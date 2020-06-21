package com.wuda.foundation.store;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.keygen.KeyGenerator;

public abstract class AbstractStoreManager implements StoreManager {

    /**
     * 新增一个新店铺.
     *
     * @param createStore 创建店铺的参数
     * @param ownerUserId 该店铺所属用户
     * @param opUserId    操作人用户ID,是谁正在添加这个新店铺
     * @return 店铺ID
     */
    public long createStore(CreateStore createStore, Long ownerUserId, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(createStore, opUserId, keyGenerator, opUserId);
        return createStoreDbOp(createStore, ownerUserId, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createStore}方法的一部分,参数的校验已经在{@link #createStore}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createStore  创建店铺的参数
     * @param ownerUserId  该店铺所属用户
     * @param keyGenerator 用于生成主键
     * @param opUserId     操作人用户ID,是谁正在添加这个新店铺
     * @return 店铺ID
     */
    protected abstract long createStoreDbOp(CreateStore createStore, Long ownerUserId, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long createStoreGeneral(Long storeId, CreateStoreGeneral createStoreGeneral, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(storeId, createStoreGeneral, keyGenerator, opUserId);
        return createStoreGeneralDbOp(storeId, createStoreGeneral, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createStoreGeneral}方法的一部分,参数的校验已经在{@link #createStoreGeneral}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param storeId            店铺ID
     * @param createStoreGeneral 创建基本信息的参数
     * @param keyGenerator       主键生成器
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 基本信息的ID
     */
    protected abstract long createStoreGeneralDbOp(Long storeId, CreateStoreGeneral createStoreGeneral, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public void updateStoreGeneralById(Long storeGeneralId, UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        ExtObjects.requireNonNull(storeGeneralId, updateStoreGeneral,opUserId);
        updateStoreGeneralByIdDbOp(storeGeneralId, updateStoreGeneral, opUserId);
    }

    /**
     * 作为{@link #updateStoreGeneralById}方法的一部分,参数的校验已经在{@link #updateStoreGeneralById}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param storeGeneralId     基本信息的ID
     * @param updateStoreGeneral 更新基本信息的参数
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     */
    protected abstract void updateStoreGeneralByIdDbOp(Long storeGeneralId, UpdateStoreGeneral updateStoreGeneral, Long opUserId);
}
