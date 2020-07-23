package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractPhoneManager;
import com.wuda.foundation.commons.CreatePhone;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PhoneRecord;
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

import static com.wuda.foundation.commons.impl.jooq.generation.tables.Phone.PHONE;
import static org.jooq.impl.DSL.param;

public class PhoneManagerImpl extends AbstractPhoneManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Long createPhoneDbOp(CreatePhone createPhone, InsertMode insertMode, Long opUserId) {

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<PhoneRecord> existsRecordSelector = DSL.using(configuration)
                .selectFrom(PHONE)
                .where(PHONE.NUMBER.eq(createPhone.getNumber()))
                .and(PHONE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = generateFields(createPhone, opUserId);
        return insertDispatcher(dataSource, insertMode, PHONE, fields, existsRecordSelector, PHONE.PHONE_ID);
    }

    @Override
    protected void createPhoneDbOp(List<CreatePhone> phones, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertSetStep<PhoneRecord> insertSetStep = dslContext.insertInto(PHONE);
        int last = phones.size() - 1;
        for (int i = 0; i < last; i++) {
            CreatePhone createPhone = phones.get(i);
            insertSetStep.values(generateFields(createPhone, opUserId));
        }
        insertSetStep.values(phones.get(last), opUserId).execute();
    }

    private Field[] generateFields(CreatePhone createPhone, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new Field[]{
                param(PHONE.PHONE_ID.getName(), ULong.valueOf(createPhone.getId())),
                param(PHONE.NUMBER.getName(), createPhone.getNumber()),
                param(PHONE.TYPE.getName(), UByte.valueOf(createPhone.getType().getCode())),
                param(PHONE.STATE.getName(), UByte.valueOf(createPhone.getState().getCode())),
                param(PHONE.CREATE_TIME.getName(), now),
                param(PHONE.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(PHONE.LAST_MODIFY_TIME.getName(), now),
                param(PHONE.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(PHONE.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
    }
}
