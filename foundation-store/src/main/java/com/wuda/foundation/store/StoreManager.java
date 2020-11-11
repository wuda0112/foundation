package com.wuda.foundation.store;

import com.wuda.foundation.lang.CreateMode;

import java.util.List;

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
     * @param ownerUserId     店铺所属用户Id
     * @param createStoreCore 创建店铺核心信息的参数
     * @param opUserId        操作人用户ID,是谁正在添加这个新店铺
     * @return 店铺核心信息ID
     */
    long createStoreCore(Long ownerUserId, CreateStoreCore createStoreCore, Long opUserId);

    /**
     * 新增一个店铺核心信息.
     *
     * @param createStoreCores 创建店铺核心信息的参数
     * @param opUserId         操作人用户ID,是谁正在添加这个新店铺
     */
    void directBatchInsertStoreCore(List<CreateStoreCore> createStoreCores, Long opUserId);

    /**
     * 为店铺新增基本信息.
     *
     * @param createStoreGeneral 创建基本信息的参数
     * @param createMode         create mode
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 基本信息的ID
     */
    long createOrUpdateStoreGeneral(CreateStoreGeneral createStoreGeneral, CreateMode createMode, Long opUserId);

    /**
     * 为店铺新增基本信息.
     *
     * @param createStoreGenerals 创建基本信息的参数
     * @param opUserId            操作人用户ID
     */
    void directBatchInsertStoreGeneral(List<CreateStoreGeneral> createStoreGenerals, Long opUserId);

    /**
     * 更新店铺新增基本信息.
     *
     * @param updateStoreGeneral 更新基本信息的参数
     * @param opUserId           操作人用户ID,是谁正在添加这个店铺的基本信息
     */
    void updateStoreGeneralById(UpdateStoreGeneral updateStoreGeneral, Long opUserId);
}
