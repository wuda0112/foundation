package com.wuda.foundation.store;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.keygen.KeyGenerator;

import java.util.List;

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

    @Override
    public void directBatchInsertStore(List<CreateStore> createStores, Long opUserId) {
        directBatchInsertStoreDbOp(createStores, opUserId);
    }

    protected abstract void directBatchInsertStoreDbOp(List<CreateStore> createStores, Long opUserId);

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
    public long createStoreGeneral(CreateStoreGeneral createStoreGeneral, Long opUserId) {
        ExtObjects.requireNonNull(createStoreGeneral,  opUserId);
        return createStoreGeneralDbOp(createStoreGeneral, opUserId);
    }

    @Override
    public void directBatchInsertStoreGeneral(List<CreateStoreGeneral> createStoreGenerals, Long opUserId) {
        directBatchInsertStoreGeneralDbOp(createStoreGenerals,  opUserId);
    }

    @Override
    public void directBatchBindStoreUser(List<BindStoreUser> bindStoreUserList, Long opUserId) {
        directBatchBindStoreUserDbOp(bindStoreUserList, opUserId);
    }

    protected abstract void directBatchBindStoreUserDbOp(List<BindStoreUser> bindStoreUserList, Long opUserId);

    protected abstract void directBatchInsertStoreGeneralDbOp(List<CreateStoreGeneral> createStoreGenerals, Long opUserId);

    /**
     * 作为{@link #createStoreGeneral}方法的一部分,参数的校验已经在{@link #createStoreGeneral}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createStoreGeneral 创建基本信息的参数
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 基本信息的ID
     */
    protected abstract long createStoreGeneralDbOp(CreateStoreGeneral createStoreGeneral, Long opUserId);

    @Override
    public void updateStoreGeneralById(Long storeGeneralId, UpdateStoreGeneral updateStoreGeneral, Long opUserId) {
        ExtObjects.requireNonNull(storeGeneralId, updateStoreGeneral, opUserId);
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
