package com.wuda.foundation.store.impl;

import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
import com.wuda.foundation.store.AbstractStoreManager;
import com.wuda.foundation.store.StoreState;
import com.wuda.foundation.store.StoreType;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreGeneralRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreRecord;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class StoreManagerImpl extends AbstractStoreManager {

    private DataSource dataSource;
    private KeyGeneratorSnowflake keyGeneratorSnowflake;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setKeyGenerator(KeyGeneratorSnowflake keyGeneratorSnowflake) {
        this.keyGeneratorSnowflake = keyGeneratorSnowflake;
    }

    @Override
    public long addStoreDbOp(StoreType storeType, StoreState storeState, Long ownerUserId, String storeName, Long opUserId) {
        long storeId = keyGeneratorSnowflake.next();
        LocalDateTime now = LocalDateTime.now();
        StoreRecord storeRecord = new StoreRecord(ULong.valueOf(storeId),
                ULong.valueOf(ownerUserId),
                UByte.valueOf(storeType.getCode()),
                UByte.valueOf(storeState.getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));

        long storeGeneralId = keyGeneratorSnowflake.next();
        StoreGeneralRecord storeGeneralRecord = new StoreGeneralRecord(ULong.valueOf(storeGeneralId),
                ULong.valueOf(storeId),
                storeName,
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        storeRecord.attach(configuration);
        storeGeneralRecord.attach(configuration);
        storeRecord.insert();
        storeGeneralRecord.insert();
        return storeId;
    }
}
