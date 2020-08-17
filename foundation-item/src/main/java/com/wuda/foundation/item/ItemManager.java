package com.wuda.foundation.item;

import com.wuda.foundation.lang.CreateMode;

import java.util.List;

/**
 * item manager.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface ItemManager {

    /**
     * 新增一个item.
     *
     * @param createItem 创建item的参数
     * @param opUserId   操作人用户ID
     * @return 新增的item ID
     */
    long createItem(CreateItem createItem, Long opUserId);

    /**
     * 新增item.
     *
     * @param createItems 创建item的参数
     * @param opUserId    操作人用户ID,是谁正在添加这个新item
     */
    void directBatchInsertItem(List<CreateItem> createItems, Long opUserId);

    /**
     * 为item新增基本信息,如果已经存在基本信息,则更新.
     *
     * @param createItemGeneral 创建item general的参数
     * @param createMode        create mode
     * @param opUserId          操作人用户ID
     * @return 已经存在或者新增的item general ID
     */
    long createOrUpdateItemGeneral(CreateItemGeneral createItemGeneral, CreateMode createMode, Long opUserId);

    /**
     * 给定的item新增基本信息.
     *
     * @param createItemGenerals 创建item general的参数
     * @param opUserId           操作人用户ID
     */
    void directBatchInsertItemGeneral(List<CreateItemGeneral> createItemGenerals, Long opUserId);

    /**
     * 给定的item新增一个规格.
     *
     * @param createItemVariation 创建item variation的参数
     * @param opUserId            操作人用户ID
     * @return 新增的item variation ID
     */
    long createItemVariation(CreateItemVariation createItemVariation, Long opUserId);

    /**
     * 给定的item新增规格.
     *
     * @param createItemVariations 创建item variation的参数
     * @param opUserId             操作人用户ID
     */
    void directBatchInsertItemVariation(List<CreateItemVariation> createItemVariations, Long opUserId);

    /**
     * 给定的item新增描述,如果已经有描述,则更新.
     *
     * @param createItemDescription 创建item description的参数
     * @param createMode            create mode
     * @param opUserId              操作人用户ID
     * @return 新增或者已有的item description ID
     */
    long createOrUpdateItemDescription(CreateItemDescription createItemDescription, CreateMode createMode, Long opUserId);

    /**
     * 给定的item新增描述.
     *
     * @param createItemDescriptions 创建item description的参数
     * @param opUserId               操作人用户ID,是谁正在添加这个新item variation
     */
    void directBatchInsertItemDescription(List<CreateItemDescription> createItemDescriptions, Long opUserId);

    /**
     * 修改给定的item或者variation的描述信息.
     *
     * @param itemDescriptionId item description id
     *                          如果为<code>null</code>,则表示修改item的描述信息,
     *                          如果不为<code>null</code>,则表示修改variation描述信息。
     * @param description       详细的描述信息
     * @param opUserId          操作人用户ID
     * @return 被修改的描述信息的ID
     */
    long updateDescription(Long itemDescriptionId, String description, Long opUserId);
}
