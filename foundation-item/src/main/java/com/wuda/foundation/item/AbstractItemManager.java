package com.wuda.foundation.item;

import com.wuda.foundation.commons.TreeManager;
import com.wuda.foundation.lang.*;

import java.util.List;

public abstract class AbstractItemManager implements ItemManager {

    @Override
    public long createItemCore(CreateItemCore createItemCore, Long opUserId) {
        ExtObjects.requireNonNull(createItemCore, opUserId);
        return createItemCoreDbOp(createItemCore, opUserId);
    }

    @Override
    public void directBatchInsertItemCore(List<CreateItemCore> createItemCores, Long opUserId) {
        directBatchInsertItemCoreDbOp(createItemCores, opUserId);
    }

    protected abstract void directBatchInsertItemCoreDbOp(List<CreateItemCore> createItemCores, Long opUserId);

    protected abstract long createItemCoreDbOp(CreateItemCore createItemCore, Long opUserId);

    @Override
    public long createOrUpdateItemGeneral(CreateItemGeneral createItemGeneral, CreateMode createMode, Long opUserId) {
        ExtObjects.requireNonNull(createItemGeneral, opUserId);
        return createOrUpdateItemGeneralDbOp(createItemGeneral, createMode, opUserId);
    }

    @Override
    public void directBatchInsertItemGeneral(List<CreateItemGeneral> createItemGenerals, Long opUserId) {
        directBatchInsertItemGeneralDbOp(createItemGenerals, opUserId);
    }

    protected abstract void directBatchInsertItemGeneralDbOp(List<CreateItemGeneral> createItemGenerals, Long opUserId);

    protected abstract long createOrUpdateItemGeneralDbOp(CreateItemGeneral createItemGeneral, CreateMode createMode, Long opUserId);

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
    public long createOrUpdateItemDescription(CreateItemDescription createItemDescription, CreateMode createMode, Long opUserId) {
        return createOrUpdateItemDescriptionDbOp(createItemDescription, createMode, opUserId);
    }

    @Override
    public void directBatchInsertItemDescription(List<CreateItemDescription> createItemDescriptions, Long opUserId) {
        directBatchInsertItemDescriptionDbOp(createItemDescriptions, opUserId);
    }

    protected abstract void directBatchInsertItemDescriptionDbOp(List<CreateItemDescription> createItemDescriptions, Long opUserId);

    protected abstract long createOrUpdateItemDescriptionDbOp(CreateItemDescription createItemDescription, CreateMode createMode, Long opUserId);

    protected abstract long createItemVariationDbOp(CreateItemVariation createItemVariation, Long opUserId);


    @Override
    public long updateDescription(Long itemDescriptionId, String description, Long opUserId) {
        ExtObjects.requireNonNull(itemDescriptionId, description, opUserId);
        return updateDescriptionDbOp(itemDescriptionId, description, opUserId);
    }

    protected abstract long updateDescriptionDbOp(Long itemDescriptionId, String description, Long opUserId);

    @Override
    public CreateResult createCategory(TreeManager treeManager, CreateItemCategory createItemCategory, Long opUserId) throws AlreadyExistsException {
        CreateResult createResult = treeManager.createNode(createItemCategory.toCreateTreeNode(), CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("category name = " + createItemCategory.getName() + "在同一级别中已经存在");
        }
        return createCategoryDbOp(createItemCategory, opUserId);
    }

    protected abstract CreateResult createCategoryDbOp(CreateItemCategory createItemCategory, Long opUserId);

    @Override
    public void updateCategory(TreeManager treeManager, UpdateItemCategory updateItemCategory, Long opUserId) throws AlreadyExistsException {
        treeManager.updateNode(updateItemCategory, opUserId);
        updateCategoryDbOp(updateItemCategory, opUserId);
    }

    protected abstract void updateCategoryDbOp(UpdateItemCategory updateItemCategory, Long opUserId);

    @Override
    public void deleteCategory(TreeManager treeManager, Long categoryId, Long opUserId) throws RelatedDataExists {
        int itemCount = itemCountInCategory(categoryId);
        if (itemCount > 0) {
            throw new RelatedDataExists("分类下还有item");
        }
        treeManager.deleteNode(categoryId, opUserId);
        deleteCategoryDbOp(categoryId, opUserId);
    }

    protected abstract void deleteCategoryDbOp(Long categoryId, Long opUserId) throws RelatedDataExists;

    @Override
    public int itemCountInCategory(Long categoryId){
        return itemCountInCategoryDbOp(categoryId);
    }

    protected abstract int itemCountInCategoryDbOp(Long categoryId);
}
