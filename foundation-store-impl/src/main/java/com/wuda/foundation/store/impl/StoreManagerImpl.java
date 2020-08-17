package com.wuda.foundation.store.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.store.AbstractStoreManager;
import com.wuda.foundation.store.BindStoreUser;
import com.wuda.foundation.store.CreateStore;
import com.wuda.foundation.store.CreateStoreGeneral;
import com.wuda.foundation.store.UpdateStoreGeneral;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreGeneralRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreUserRelationshipRecord;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
            records.add(storeRecordForInsert(createStore, opUserId));
        }
        batchInsert(dataSource, STORE, records);
    }

    @Override
    public long createStoreDbOp(Long ownerUserId, CreateStore createStore, Long opUserId) {
        long storeId = insert(dataSource, STORE, storeRecordForInsert(createStore, opUserId)).getRecordId();
        BindStoreUser bindStoreUser = new BindStoreUser.Builder()
                .setId(FoundationContext.getLongKeyGenerator().next())
                .setStoreId(storeId)
                .setUserId(ownerUserId)
                .isStoreOwner(true)
                .build();
        insert(dataSource, STORE_USER_RELATIONSHIP, storeUserRelationshipRecordForInsert(bindStoreUser, opUserId));
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
            records.add(storeGeneralRecordForInsert(createStoreGeneral, opUserId));
        }
        batchInsert(dataSource, STORE_GENERAL, records);
    }

    @Override
    public long createOrUpdateStoreGeneralDbOp(CreateStoreGeneral createStoreGeneral, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(STORE_GENERAL.STORE_GENERAL_ID)
                .from(STORE_GENERAL)
                .where(STORE_GENERAL.STORE_ID.eq(ULong.valueOf(createStoreGeneral.getStoreId())))
                .and(STORE_GENERAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Consumer<Long> updateAction = (storeGeneralId) -> {
            UpdateStoreGeneral updateStoreGeneral = UpdateStoreGeneral.from(storeGeneralId, createStoreGeneral);
            updateStoreGeneralByIdDbOp(updateStoreGeneral, opUserId);
        };
        return insertOrUpdate(createMode, dataSource, STORE_GENERAL, storeGeneralRecordForInsert(createStoreGeneral, opUserId), existsRecordSelector, updateAction);
    }

    @Override
    public void updateStoreGeneralByIdDbOp(UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        StoreGeneralRecord storeGeneralRecord = new StoreGeneralRecord();
        storeGeneralRecord.setStoreGeneralId(ULong.valueOf(updateStoreGeneral.getId()));
        if (Objects.nonNull(updateStoreGeneral.getStoreName())) {
            storeGeneralRecord.setStoreName(updateStoreGeneral.getStoreName());
        }
        storeGeneralRecord.setLastModifyTime(now);
        storeGeneralRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        storeGeneralRecord.attach(configuration);
        storeGeneralRecord.update();
    }

    private StoreGeneralRecord storeGeneralRecordForInsert(CreateStoreGeneral createStoreGeneral, Long opUserId) {
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

    private StoreRecord storeRecordForInsert(CreateStore createStore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreRecord(ULong.valueOf(createStore.getId()),
                UByte.valueOf(createStore.getStoreType()),
                UByte.valueOf(createStore.getStoreState()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}
