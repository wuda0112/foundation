package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.AbstractEmailManager;
import com.wuda.foundation.lang.EmailState;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.EmailRecord;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class EmailManagerImpl extends AbstractEmailManager {

    private DataSource dataSource;
    private KeyGeneratorSnowflake keyGeneratorSnowflake;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setKeyGeneratorSnowflake(KeyGeneratorSnowflake keyGeneratorSnowflake) {
        this.keyGeneratorSnowflake = keyGeneratorSnowflake;
    }

    @Override
    protected long addEmailDbOp(String emailAddress, EmailState emailState, Long opUserId) {
        long emailId = keyGeneratorSnowflake.next();
        LocalDateTime now = LocalDateTime.now();
        EmailRecord emailRecord = new EmailRecord(ULong.valueOf(emailId),
                emailAddress,
                UByte.valueOf(emailState.getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        emailRecord.attach(configuration);
        emailRecord.insert();
        return emailId;
    }
}
