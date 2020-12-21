package com.wuda.foundation.core.commons;

/**
 * menu item category manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface MenuItemCategoryManager extends TreeManager<CreateMenuItemCategory, UpdateMenuItemCategory, DescribeMenuItemCategory> {

    /**
     * 统计分类下的item数量.
     *
     * @param categoryId category id
     * @return count
     */
    int itemCountInCategory(Long categoryId);

}
