package com.wuda.foundation.store.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.BuiltinIdentifierTypes;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.store.AbstractStoreManager;
import com.wuda.foundation.store.CreateStoreCore;
import com.wuda.foundation.store.CreateStoreGeneral;
import com.wuda.foundation.store.UpdateStoreGeneral;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreCoreRecord;
import com.wuda.foundation.store.impl.jooq.generation.tables.records.StoreGeneralRecord;
import com.wuda.foundation.user.UserBelongsToGroupManager;
import com.wuda.foundation.user.CreateUserBelongsToGroupCoreRequest;
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

import static com.wuda.foundation.store.impl.jooq.generation.tables.StoreCore.STORE_CORE;
import static com.wuda.foundation.store.impl.jooq.generation.tables.StoreGeneral.STORE_GENERAL;

public class StoreManagerImpl extends AbstractStoreManager implements JooqCommonDbOp {

    private DataSource dataSource;
    private UserBelongsToGroupManager userBelongsToGroupManager;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void directBatchInsertStoreDbOp(List<CreateStoreCore> createStoreCores, Long opUserId) {
        List<StoreCoreRecord> records = new ArrayList<>(createStoreCores.size());
        for (CreateStoreCore createStoreCore : createStoreCores) {
            records.add(storeRecordForInsert(createStoreCore, opUserId));
        }
        batchInsert(dataSource, STORE_CORE, records);
    }

    @Override
    public long createStoreCoreDbOp(Long ownerUserId, CreateStoreCore createStoreCore, Long opUserId) {
        long storeCoreId = insert(dataSource, STORE_CORE, storeRecordForInsert(createStoreCore, opUserId)).getRecordId();
        CreateUserBelongsToGroupCoreRequest createUserBelongsToGroupCoreRequest = new CreateUserBelongsToGroupCoreRequest.Builder()
                .setUserId(ownerUserId)
                .setGroup(new LongIdentifier(createStoreCore.getStoreId(), BuiltinIdentifierTypes.TABLE_STORE))
                .build();
        userBelongsToGroupManager.createUserBelongsToGroupCore(createUserBelongsToGroupCoreRequest,CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        return storeCoreId;
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

    private StoreCoreRecord storeRecordForInsert(CreateStoreCore createStoreCore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new StoreCoreRecord(ULong.valueOf(createStoreCore.getId()),
                ULong.valueOf(createStoreCore.getStoreId()),
                UByte.valueOf(createStoreCore.getStoreType().getCode()),
                UByte.valueOf(createStoreCore.getStoreState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }
}
