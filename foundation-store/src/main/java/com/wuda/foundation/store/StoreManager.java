package com.wuda.foundation.store;

/**
 * 店铺管理.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface StoreManager {

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
    long addStore(StoreType storeType,
                  StoreState storeState,
                  Long ownerUserId,
                  String storeName,
                  Long opUserId);
}
