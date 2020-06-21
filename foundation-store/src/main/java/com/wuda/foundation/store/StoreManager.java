package com.wuda.foundation.store;

import com.wuda.foundation.lang.keygen.KeyGenerator;

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
     * @param createStore  创建店铺的参数
     * @param ownerUserId  该店铺所属用户
     * @param keyGenerator 用于生成主键
     * @param opUserId     操作人用户ID,是谁正在添加这个新店铺
     * @return 店铺ID
     */
    long createStore(CreateStore createStore, Long ownerUserId, KeyGenerator<Long> keyGenerator, Long opUserId);

    /**
     * 为店铺新增基本信息.
     *
     * @param storeId            店铺ID
     * @param createStoreGeneral 创建基本信息的参数
     * @param keyGenerator       主键生成器
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 基本信息的ID
     */
    long createStoreGeneral(Long storeId, CreateStoreGeneral createStoreGeneral, KeyGenerator<Long> keyGenerator, Long opUserId);

    /**
     * 更新店铺新增基本信息.
     *
     * @param storeGeneralId     基本信息的ID
     * @param updateStoreGeneral 更新基本信息的参数
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     */
    void updateStoreGeneralById(Long storeGeneralId, UpdateStoreGeneral updateStoreGeneral, Long opUserId);
}
