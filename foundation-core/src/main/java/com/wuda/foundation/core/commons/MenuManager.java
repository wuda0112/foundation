package com.wuda.foundation.core.commons;

import java.util.List;

/**
 * menu item manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface MenuManager {

    /**
     * 获取通过角色获得的所有menu item.
     *
     * @param roleIds permission role id集合
     * @return menu item的集合
     */
    List<DescribeMenuItem> getMenuItemsFromRole(List<Long> roleIds);

    /**
     * 根据ID获取.
     *
     * @param ids menu item id
     * @return {@link DescribeMenuItem}
     */
    List<DescribeMenuItem> getMenuItemsById(List<Long> ids);

    /**
     * 获取菜单.
     *
     * @param menuId menu id
     * @return 菜单
     */
    Menu getMenu(Long menuId);

    /**
     * 获取分类下的menu item.
     *
     * @param menuItemCategoryIds 分类ID
     * @return menu item
     */
    List<DescribeMenuItem> getMenuItemsByCategoryId(List<Long> menuItemCategoryIds);

    /**
     * 获取菜单下的分类.
     *
     * @param menuId menu id
     * @return 分类
     */
    List<DescribeMenuItemCategory> getMenuItemCategory(Long menuId);

    /**
     * 获取分类所属的菜单
     *
     * @param menuItemCategoryId menu item category id
     * @return 所属菜单
     */
    Long getMenuId(Long menuItemCategoryId);
}
