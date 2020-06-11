package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.*;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PhoneRecord;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.PhoneState;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class PhoneManagerImpl extends AbstractPhoneManager {

    private DataSource dataSource;
    private KeyGeneratorSnowflake keyGeneratorSnowflake;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setKeyGeneratorSnowflake(KeyGeneratorSnowflake keyGeneratorSnowflake) {
        this.keyGeneratorSnowflake = keyGeneratorSnowflake;
    }

    @Override
    protected long addPhoneDbOp(String phoneNumber, PhoneState phoneState, PhoneType phoneType, Long opUserId) {
        long phoneId = keyGeneratorSnowflake.next();
        LocalDateTime now = LocalDateTime.now();
        PhoneRecord phoneRecord = new PhoneRecord(ULong.valueOf(phoneId),
                phoneNumber,
                UByte.valueOf(phoneType.getCode()),
                UByte.valueOf(phoneState.getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        phoneRecord.attach(configuration);
        phoneRecord.insert();
        return phoneId;
    }
}
