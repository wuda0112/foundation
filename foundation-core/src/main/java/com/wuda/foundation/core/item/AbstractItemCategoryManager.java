package com.wuda.foundation.core.item;

import com.wuda.foundation.core.commons.AbstractTreeManager;

public abstract class AbstractItemCategoryManager extends AbstractTreeManager<CreateItemCategory, UpdateItemCategory,DescribeItemCategory> implements ItemCategoryManager {

    @Override
    public int itemCountInCategory(Long categoryId) {
        return itemCountInCategoryDbOp(categoryId);
    }

    protected abstract int itemCountInCategoryDbOp(Long categoryId);

}
