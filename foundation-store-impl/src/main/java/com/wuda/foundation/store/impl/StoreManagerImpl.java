package com.wuda.foundation.store.impl;

import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.store.AbstractStoreManager;
import com.wuda.foundation.store.CreateStore;
import com.wuda.foundation.store.CreateStoreGeneral;
import com.wuda.foundation.store.UpdateStoreGeneral;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreGeneralRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreRecord;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Objects;

public class StoreManagerImpl extends AbstractStoreManager {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createStoreDbOp(CreateStore createStore, Long ownerUserId, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long storeId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        StoreRecord storeRecord = new StoreRecord(ULong.valueOf(storeId),
                ULong.valueOf(ownerUserId),
                UByte.valueOf(createStore.getStoreType().getCode()),
                UByte.valueOf(createStore.getStoreState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        storeRecord.attach(configuration);
        storeRecord.insert();

        return storeId;
    }

    public long createStoreGeneralDbOp(Long storeId, CreateStoreGeneral createStoreGeneral, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long storeGeneralId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        StoreGeneralRecord storeGeneralRecord = new StoreGeneralRecord(ULong.valueOf(storeGeneralId),
                ULong.valueOf(storeId),
                createStoreGeneral.getStoreName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));

        Configuration configuration = JooqContext.getConfiguration(dataSource);
        storeGeneralRecord.attach(configuration);
        storeGeneralRecord.insert();
        return storeGeneralId;
    }

    public void updateStoreGeneralByIdDbOp(Long storeGeneralId, UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        StoreGeneralRecord storeGeneralRecord = new StoreGeneralRecord();
        storeGeneralRecord.setStoreGeneralId(ULong.valueOf(storeGeneralId));
        if (Objects.nonNull(updateStoreGeneral.getStoreName())) {
            storeGeneralRecord.setStoreName(updateStoreGeneral.getStoreName());
        }
        storeGeneralRecord.setLastModifyTime(now);
        storeGeneralRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        storeGeneralRecord.attach(configuration);
        storeGeneralRecord.update();
    }
}
