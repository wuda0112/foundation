package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractEmailManager;
import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.EmailRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.Email.EMAIL;
import static org.jooq.impl.DSL.param;

public class EmailManagerImpl extends AbstractEmailManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Long createEmailDbOp(CreateEmail createEmail, InsertMode insertMode, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<EmailRecord> existsRecordSelector = DSL.using(configuration)
                .selectFrom(EMAIL)
                .where(EMAIL.ADDRESS.eq(createEmail.getAddress()))
                .and(EMAIL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = generateFields(createEmail, opUserId);
        return insertDispatcher(dataSource, insertMode, EMAIL, fields, existsRecordSelector, EMAIL.EMAIL_ID);
    }

    @Override
    protected void createEmailDbOp(List<CreateEmail> emails, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<EmailRecord> insertSetStep = dslContext.insertInto(EMAIL);
        int last = emails.size() - 1;
        for (int i = 0; i < last; i++) {
            CreateEmail createEmail = emails.get(i);
            insertSetStep.values(generateFields(createEmail, opUserId));
        }
        insertSetStep.values(emails.get(last), opUserId).execute();
    }

    private Field[] generateFields(CreateEmail createEmail, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(EMAIL.EMAIL_ID.getName(), ULong.valueOf(createEmail.getId())),
                param(EMAIL.ADDRESS.getName(), createEmail.getAddress()),
                param(EMAIL.STATE.getName(), UByte.valueOf(createEmail.getState().getCode())),
                param(EMAIL.CREATE_TIME.getName(), now),
                param(EMAIL.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(EMAIL.LAST_MODIFY_TIME.getName(), now),
                param(EMAIL.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(EMAIL.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }
}
