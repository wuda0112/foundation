package com.wuda.foundation.item;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.InsertMode;

import java.util.List;

public abstract class AbstractItemManager implements ItemManager {

    @Override
    public long createItem(CreateItem createItem, Long opUserId) {
        ExtObjects.requireNonNull(createItem, opUserId);
        return createItemDbOp(createItem, opUserId);
    }

    @Override
    public void directBatchInsertItem(List<CreateItem> createItems, Long opUserId) {
        directBatchInsertItemDbOp(createItems, opUserId);
    }

    protected abstract void directBatchInsertItemDbOp(List<CreateItem> createItems, Long opUserId);

    protected abstract long createItemDbOp(CreateItem createItem, Long opUserId);

    @Override
    public long createOrUpdateItemGeneral(CreateItemGeneral createItemGeneral, InsertMode insertMode, Long opUserId) {
        ExtObjects.requireNonNull(createItemGeneral, opUserId);
        return createOrUpdateItemGeneralDbOp(createItemGeneral, insertMode, opUserId);
    }

    @Override
    public void directBatchInsertItemGeneral(List<CreateItemGeneral> createItemGenerals, Long opUserId) {
        directBatchInsertItemGeneralDbOp(createItemGenerals, opUserId);
    }

    protected abstract void directBatchInsertItemGeneralDbOp(List<CreateItemGeneral> createItemGenerals, Long opUserId);

    protected abstract long createOrUpdateItemGeneralDbOp(CreateItemGeneral createItemGeneral, InsertMode insertMode, Long opUserId);

    @Override
    public long createItemVariation(CreateItemVariation createItemVariation, Long opUserId) {
        ExtObjects.requireNonNull(createItemVariation, opUserId);
        return createItemVariationDbOp(createItemVariation, opUserId);
    }

    @Override
    public void directBatchInsertItemVariation(List<CreateItemVariation> createItemVariations, Long opUserId) {
        directBatchInsertItemVariationDbOp(createItemVariations, opUserId);
    }

    protected abstract void directBatchInsertItemVariationDbOp(List<CreateItemVariation> createItemVariations, Long opUserId);

    @Override
    public long createOrUpdateItemDescription(CreateItemDescription createItemDescription, InsertMode insertMode, Long opUserId) {
        return createOrUpdateItemDescriptionDbOp(createItemDescription, insertMode, opUserId);
    }

    @Override
    public void directBatchInsertItemDescription(List<CreateItemDescription> createItemDescriptions, Long opUserId) {
        directBatchInsertItemDescriptionDbOp(createItemDescriptions, opUserId);
    }

    protected abstract void directBatchInsertItemDescriptionDbOp(List<CreateItemDescription> createItemDescriptions, Long opUserId);

    protected abstract long createOrUpdateItemDescriptionDbOp(CreateItemDescription createItemDescription, InsertMode insertMode, Long opUserId);

    protected abstract long createItemVariationDbOp(CreateItemVariation createItemVariation, Long opUserId);


    @Override
    public long updateDescription(Long itemDescriptionId, String description, Long opUserId) {
        ExtObjects.requireNonNull(itemDescriptionId, description, opUserId);
        return updateDescriptionDbOp(itemDescriptionId, description, opUserId);
    }

    protected abstract long updateDescriptionDbOp(Long itemDescriptionId, String description, Long opUserId);
}
