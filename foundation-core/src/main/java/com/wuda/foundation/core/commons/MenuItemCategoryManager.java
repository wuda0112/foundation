package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.tree.Tree;

/**
 * menu item category manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface MenuItemCategoryManager extends TreeManager<CreateMenuItemCategory, UpdateMenuItemCategory, DescribeMenuItemCategory> {

    /**
     * 获取给定菜单的所有分类,以树形结构返回.
     *
     * @param menuId menu id
     * @return 分类树
     */
    Tree<Long, DescribeMenuItemCategory> getTree(Long menuId);

}
