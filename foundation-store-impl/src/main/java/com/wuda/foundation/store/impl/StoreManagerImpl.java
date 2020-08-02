package com.wuda.foundation.store.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.store.*;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreGeneralRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreUserRelationshipRecord;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wuda.foundation.store.impl.jooq.generation.tables.Store.STORE;
import static com.wuda.foundation.store.impl.jooq.generation.tables.StoreGeneral.STORE_GENERAL;
import static com.wuda.foundation.store.impl.jooq.generation.tables.StoreUserRelationship.STORE_USER_RELATIONSHIP;

public class StoreManagerImpl extends AbstractStoreManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void directBatchInsertStoreDbOp(List<CreateStore> createStores, Long opUserId) {
        List<StoreRecord> records = new ArrayList<>(createStores.size());
        for (CreateStore createStore : createStores) {
            records.add(newStoreRecord(createStore, opUserId));
        }
        batchInsert(dataSource, STORE, records);
    }

    @Override
    public long createStoreDbOp(CreateStore createStore, Long ownerUserId, KeyGenerator<Long> keyGenerator, Long opUserId) {
        Field[] storeFields = generateField(createStore, opUserId);
        long storeId = insert(dataSource, STORE, storeFields).getRecordId();

        BindStoreUser bindStoreUser = new BindStoreUser.Builder()
                .setId(keyGenerator.next())
                .setStoreId(storeId)
                .setUserId(opUserId)
                .isStoreOwner(true)
                .build();
        Field[] storeUserRelationshipFields = generateField(bindStoreUser, opUserId);

        insert(dataSource, STORE_USER_RELATIONSHIP, storeUserRelationshipFields);
        return storeId;
    }

    @Override
    protected void directBatchBindStoreUserDbOp(List<BindStoreUser> bindStoreUserList, Long opUserId) {
        batchInsert(dataSource, STORE_USER_RELATIONSHIP, storeUserRelationshipRecordsForInsert(bindStoreUserList, opUserId));
    }

    @Override
    protected void directBatchInsertStoreGeneralDbOp(List<CreateStoreGeneral> createStoreGenerals, Long opUserId) {
        List<StoreGeneralRecord> records = new ArrayList<>(createStoreGenerals.size());
        for (CreateStoreGeneral createStoreGeneral : createStoreGenerals) {
            records.add(newStoreGeneralRecord(createStoreGeneral, opUserId));
        }
        batchInsert(dataSource, STORE_GENERAL, records);
    }

    @Override
    public long createStoreGeneralDbOp(CreateStoreGeneral createStoreGeneral, Long opUserId) {
        Field[] fields = newStoreGeneralRecord(createStoreGeneral, opUserId).fields();
        return insert(dataSource, STORE_GENERAL, fields).getRecordId();
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

    private StoreGeneralRecord newStoreGeneralRecord(CreateStoreGeneral createStoreGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreGeneralRecord(ULong.valueOf(createStoreGeneral.getId()),
                ULong.valueOf(createStoreGeneral.getStoreId()),
                createStoreGeneral.getStoreName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private StoreUserRelationshipRecord storeUserRelationshipRecordForInsert(BindStoreUser bindStoreUser, Long opUserId) {
        return new StoreUserRelationshipRecord(ULong.valueOf(bindStoreUser.getId()),
                ULong.valueOf(bindStoreUser.getStoreId()),
                ULong.valueOf(bindStoreUser.getUserId()),
                bindStoreUser.getIsStoreOwner(),
                LocalDateTime.now(),
                ULong.valueOf(opUserId),
                ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<StoreUserRelationshipRecord> storeUserRelationshipRecordsForInsert(List<BindStoreUser> bindStoreUserList, Long opUserId) {
        List<StoreUserRelationshipRecord> list = new ArrayList<>(bindStoreUserList.size());
        for (BindStoreUser bindStoreUser : bindStoreUserList) {
            list.add(storeUserRelationshipRecordForInsert(bindStoreUser, opUserId));
        }
        return list;
    }

    private StoreRecord newStoreRecord(CreateStore createStore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreRecord(ULong.valueOf(createStore.getId()),
                UByte.valueOf(createStore.getStoreType().getCode()),
                UByte.valueOf(createStore.getStoreState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private Field[] generateField(CreateStore createStore, Long opUserId) {
        StoreRecord storeRecord = newStoreRecord(createStore, opUserId);
        return storeRecord.fields();
    }

    private Field[] generateField(BindStoreUser bindStoreUser, Long opUserId) {
        StoreUserRelationshipRecord storeUserRelationshipRecord = storeUserRelationshipRecordForInsert(bindStoreUser, opUserId);
        return storeUserRelationshipRecord.fields();
    }
}
