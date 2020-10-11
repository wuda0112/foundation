package com.wuda.foundation.store;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.CreateMode;

import java.util.List;

public abstract class AbstractStoreManager implements StoreManager {

    @Override
    public long createStoreCore(Long ownerUserId, CreateStoreCore createStoreCore, Long opUserId) {
        ExtObjects.requireNonNull(createStoreCore, opUserId);
        return createStoreCoreDbOp(ownerUserId, createStoreCore, opUserId);
    }

    @Override
    public void directBatchInsertStoreCore(List<CreateStoreCore> createStoreCores, Long opUserId) {
        directBatchInsertStoreDbOp(createStoreCores, opUserId);
    }

    protected abstract void directBatchInsertStoreDbOp(List<CreateStoreCore> createStoreCores, Long opUserId);

    protected abstract long createStoreCoreDbOp(Long ownerUserId, CreateStoreCore createStoreCore, Long opUserId);

    @Override
    public long createOrUpdateStoreGeneral(CreateStoreGeneral createStoreGeneral, CreateMode createMode, Long opUserId) {
        ExtObjects.requireNonNull(createStoreGeneral, opUserId);
        return createOrUpdateStoreGeneralDbOp(createStoreGeneral, createMode, opUserId);
    }

    @Override
    public void directBatchInsertStoreGeneral(List<CreateStoreGeneral> createStoreGenerals, Long opUserId) {
        directBatchInsertStoreGeneralDbOp(createStoreGenerals, opUserId);
    }

    @Override
    public void directBatchBindStoreUser(List<BindStoreUser> bindStoreUserList, Long opUserId) {
        directBatchBindStoreUserDbOp(bindStoreUserList, opUserId);
    }

    protected abstract void directBatchBindStoreUserDbOp(List<BindStoreUser> bindStoreUserList, Long opUserId);

    protected abstract void directBatchInsertStoreGeneralDbOp(List<CreateStoreGeneral> createStoreGenerals, Long opUserId);

    protected abstract long createOrUpdateStoreGeneralDbOp(CreateStoreGeneral createStoreGeneral, CreateMode createMode, Long opUserId);

    @Override
    public void updateStoreGeneralById(UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        ExtObjects.requireNonNull(updateStoreGeneral, opUserId);
        updateStoreGeneralByIdDbOp(updateStoreGeneral, opUserId);
    }

    protected abstract void updateStoreGeneralByIdDbOp(UpdateStoreGeneral updateStoreGeneral, Long opUserId);
}
