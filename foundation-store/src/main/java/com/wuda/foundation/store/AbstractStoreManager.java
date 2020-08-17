package com.wuda.foundation.store;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.CreateMode;

import java.util.List;

public abstract class AbstractStoreManager implements StoreManager {

    /**
     * 新增一个新店铺.
     *
     * @param createStore 创建店铺的参数
     * @return 店铺ID
     */
    public long createStore(Long ownerUserId, CreateStore createStore, Long opUserId) {
        ExtObjects.requireNonNull(createStore, opUserId);
        return createStoreDbOp(ownerUserId, createStore, opUserId);
    }

    @Override
    public void directBatchInsertStore(List<CreateStore> createStores, Long opUserId) {
        directBatchInsertStoreDbOp(createStores, opUserId);
    }

    protected abstract void directBatchInsertStoreDbOp(List<CreateStore> createStores, Long opUserId);

    protected abstract long createStoreDbOp(Long ownerUserId, CreateStore createStore, Long opUserId);

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
