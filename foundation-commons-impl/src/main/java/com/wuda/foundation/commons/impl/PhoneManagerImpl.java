package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractPhoneManager;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PhoneRecord;
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

import static com.wuda.foundation.commons.impl.jooq.generation.tables.Phone.PHONE;

public class PhoneManagerImpl extends AbstractPhoneManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Long createPhoneDbOp(CreatePhone createPhone, InsertMode insertMode, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PHONE.PHONE_ID)
                .from(PHONE)
                .where(PHONE.NUMBER.eq(createPhone.getNumber()))
                .and(PHONE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, insertMode, PHONE, phoneRecordForInsert(createPhone, opUserId), existsRecordSelector).getRecordId();
    }

    @Override
    protected void directBatchInsertPhoneDbOp(List<CreatePhone> phones, Long opUserId) {
        batchInsert(dataSource, PHONE, phoneRecordsForInsert(phones, opUserId));
    }

    private PhoneRecord phoneRecordForInsert(CreatePhone createPhone, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PhoneRecord(ULong.valueOf(createPhone.getId()),
                createPhone.getNumber(),
                UByte.valueOf(createPhone.getType().getCode()),
                UByte.valueOf(createPhone.getState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<PhoneRecord> phoneRecordsForInsert(List<CreatePhone> createPhones, Long opUserId) {
        List<PhoneRecord> list = new ArrayList<>(createPhones.size());
        for (CreatePhone createPhone : createPhones) {
            list.add(phoneRecordForInsert(createPhone, opUserId));
        }
        return list;
    }
}
