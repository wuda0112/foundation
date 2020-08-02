package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractEmailManager;
import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.EmailRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.Email.EMAIL;

public class EmailManagerImpl extends AbstractEmailManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Long createEmailDbOp(CreateEmail createEmail, InsertMode insertMode, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(EMAIL.EMAIL_ID)
                .from(EMAIL)
                .where(EMAIL.ADDRESS.eq(createEmail.getAddress()))
                .and(EMAIL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = emailRecordForInsert(createEmail, opUserId).fields();
        return insertDispatcher(dataSource, insertMode, EMAIL, fields, existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchInsertEmailDbOp(List<CreateEmail> emails, Long opUserId) {
        batchInsert(dataSource, EMAIL, emailRecordsForInsert(emails, opUserId));
    }

    private EmailRecord emailRecordForInsert(CreateEmail createEmail, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new EmailRecord(ULong.valueOf(createEmail.getId()),
                createEmail.getAddress(),
                UByte.valueOf(createEmail.getState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<EmailRecord> emailRecordsForInsert(List<CreateEmail> createEmails, Long opUserId) {
        List<EmailRecord> list = new ArrayList<>(createEmails.size());
        for (CreateEmail createEmail : createEmails) {
            list.add(emailRecordForInsert(createEmail, opUserId));
        }
        return list;
    }
}
