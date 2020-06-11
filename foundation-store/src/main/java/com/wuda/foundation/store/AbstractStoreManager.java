package com.wuda.foundation.store;

import com.wuda.foundation.lang.ExtObjects;

public abstract class AbstractStoreManager implements StoreManager {

    @Override
    public long addStore(StoreType storeType, StoreState storeState, Long ownerUserId, String storeName, Long opUserId) {
        ExtObjects.requireNonNull(storeType, storeState, ownerUserId, storeName, opUserId);
        return addStoreDbOp(storeType, storeState, ownerUserId, storeName, opUserId);
    }

    /**
     * 新增一个新店铺.
     *
     * @param storeType   店铺类型
     * @param storeState  店铺状态
     * @param ownerUserId 该店铺所属用户
     * @param storeName   店铺名称
     * @param opUserId    操作人用户ID,是谁正在添加这个新店铺
     * @return 店铺ID
     */
    public abstract long addStoreDbOp(StoreType storeType, StoreState storeState, Long ownerUserId, String storeName, Long opUserId);
}
