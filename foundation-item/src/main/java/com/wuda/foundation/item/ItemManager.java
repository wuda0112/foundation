package com.wuda.foundation.item;

import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.keygen.KeyGenerator;

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
     * 给定的item新增基本信息.
     *
     * @param createItemGeneral 创建item general的参数
     * @param opUserId          操作人用户ID
     * @return 新增的item variation ID
     */
    long createItemGeneral(CreateItemGeneral createItemGeneral, Long opUserId);

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
     * @return 新增的item variation ID
     */
    void directBatchInsertItemVariation(List<CreateItemVariation> createItemVariations, Long opUserId);

    /**
     * 给定的item新增一个描述.
     *
     * @param createItemDescription 创建item description的参数
     * @param insertMode            insert mode
     * @param opUserId              操作人用户ID,是谁正在添加这个新item variation
     * @return 新增的item variation ID
     */
    long createItemDescription(CreateItemDescription createItemDescription, InsertMode insertMode, Long opUserId);

    /**
     * 给定的item新增描述.
     *
     * @param createItemDescriptions 创建item description的参数
     * @param opUserId               操作人用户ID,是谁正在添加这个新item variation
     * @return 新增的item variation ID
     */
    void directBatchInsertItemDescription(List<CreateItemDescription> createItemDescriptions, Long opUserId);

    /**
     * 为给定的item或者variation新增描述信息.
     *
     * @param itemId          item id
     * @param itemVariationId item variation id,可以为<code>null</code>,
     *                        如果为<code>null</code>,则表示新增item的描述信息,
     *                        如果不为<code>null</code>,则表示新增variation的描述信息。
     * @param description     详细的描述信息
     * @param keyGenerator    用于生成主键
     * @param opUserId        操作人用户ID
     * @return 新增的item or variation description ID
     */
    long createDescription(Long itemId,
                           Long itemVariationId,
                           String description,
                           KeyGenerator<Long> keyGenerator,
                           Long opUserId);

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
    long updateDescription(Long itemDescriptionId,
                           String description,
                           Long opUserId);

    /**
     * 新增或者修改给定的item或者variation的描述信息.
     *
     * @param itemId          item id
     * @param itemVariationId item variation id,可以为<code>null</code>,
     *                        如果为<code>null</code>,则表示新增或者修改item的描述信息,
     *                        如果不为<code>null</code>,则表示新增或者修改variation的描述信息。
     * @param description     详细的描述信息
     * @param keyGenerator    如果是新增描述信息,用于生成主键
     * @param opUserId        操作人用户ID
     * @return 新增或者修改的描述信息的ID
     */
    long createOrUpdateDescription(Long itemId,
                                   Long itemVariationId,
                                   String description,
                                   KeyGenerator<Long> keyGenerator,
                                   Long opUserId);
}
