package com.wuda.foundation.core.item;

import com.wuda.foundation.core.commons.TreeManager;

/**
 * item category manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface ItemCategoryManager extends TreeManager<CreateItemCategory, UpdateItemCategory, DescribeItemCategory> {

    /**
     * 统计分类下的item数量.
     *
     * @param categoryId category id
     * @return count
     */
    int itemCountInCategory(Long categoryId);
}
