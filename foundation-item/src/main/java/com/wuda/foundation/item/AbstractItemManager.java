package com.wuda.foundation.item;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.keygen.KeyGenerator;

public abstract class AbstractItemManager implements ItemManager {

    @Override
    public long createItem(Long storeId, CreateItem createItem, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(storeId, createItem, opUserId);
        return createItemDbOp(storeId, createItem, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createItem}方法的一部分,参数的校验已经在{@link #createItem}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param storeId      该item所属店铺
     * @param createItem   创建item的参数
     * @param keyGenerator 用于生成主键
     * @param opUserId     操作人用户ID,是谁正在添加这个新item
     * @return 新增的item ID
     */
    protected abstract long createItemDbOp(Long storeId, CreateItem createItem, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long createItemGeneral(Long itemId, CreateItemGeneral createItemGeneral, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(itemId, createItemGeneral, opUserId);
        return createItemGeneralDbOp(itemId, createItemGeneral, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createItemGeneral}方法的一部分,参数的校验已经在{@link #createItemGeneral}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param itemId            item id
     * @param createItemGeneral 创建item general的参数
     * @param opUserId          操作人用户ID,是谁正在添加这个新item variation
     * @param keyGenerator      用于生成主键
     * @return 新增的item variation ID
     */
    protected abstract long createItemGeneralDbOp(Long itemId, CreateItemGeneral createItemGeneral, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long createItemVariation(Long itemId, CreateItemVariation createItemVariation, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(itemId, createItemVariation, opUserId);
        return createItemVariationDbOp(itemId, createItemVariation, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createItemVariation}方法的一部分,参数的校验已经在{@link #createItemVariation}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param itemId              item id
     * @param createItemVariation 创建item variation的参数
     * @param keyGenerator        用于生成主键
     * @param opUserId            操作人用户ID,是谁正在添加这个新item variation
     * @return 新增的item variation ID
     */
    protected abstract long createItemVariationDbOp(Long itemId, CreateItemVariation createItemVariation, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long createDescription(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(itemId, itemVariationId, description, opUserId);
        return createDescriptionDbOp(itemId, itemVariationId, description, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createDescription}方法的一部分,参数的校验已经在{@link #createDescription}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
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
    protected abstract long createDescriptionDbOp(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long updateDescription(Long itemDescriptionId, String description, Long opUserId) {
        ExtObjects.requireNonNull(itemDescriptionId, description, opUserId);
        return updateDescriptionDbOp(itemDescriptionId, description, opUserId);
    }

    /**
     * 作为{@link #createDescription}方法的一部分,参数的校验已经在{@link #createDescription}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param itemDescriptionId item  description id
     *                          如果为<code>null</code>,则表示修改item的描述信息,
     *                          如果不为<code>null</code>,则表示修改variation描述信息。
     * @param description       详细的描述信息
     * @param opUserId          操作人用户ID
     * @return 被修改的描述信息的ID
     */
    protected abstract long updateDescriptionDbOp(Long itemDescriptionId, String description, Long opUserId);

    @Override
    public long createOrUpdateDescription(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(itemId, itemVariationId, description, opUserId);
        return createOrUpdateDescriptionDbOp(itemId, itemVariationId, description, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createDescription}方法的一部分,参数的校验已经在{@link #createDescription}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param itemId          item id
     * @param itemVariationId item variation id,可以为<code>null</code>,
     *                        如果为<code>null</code>,则表示新增item的描述信息,
     *                        如果不为<code>null</code>,则表示新增variation的描述信息。
     * @param description     详细的描述信息
     * @param opUserId        操作人用户ID
     * @param keyGenerator    如果是新增描述信息,用于生成主键
     * @return 新增的item or variation description ID
     */
    protected abstract long createOrUpdateDescriptionDbOp(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId);
}
