package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

import java.util.List;

/**
 * menu item manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface MenuManager {

    /**
     * 创建menu item core.
     *
     * @param createMenuItemCore 参数
     * @param createMode         create mode
     * @return 创建结果
     */
    CreateResult createMenuItemCore(CreateMenuItemCore createMenuItemCore, CreateMode createMode);

    /**
     * 更新.
     *
     * @param updateMenuItemCore 参数
     */
    void updateMenuItemCore(UpdateMenuItemCore updateMenuItemCore);

    /**
     * add item to category.
     *
     * @param menuItemId         menu item id
     * @param menuItemCategoryId menu item category id
     * @param opUserId           操作人用户ID
     */
    void addItemToCategory(Long menuItemId, Long menuItemCategoryId, Long opUserId);

    /**
     * remove item from category.
     *
     * @param menuItemId         menu item id
     * @param menuItemCategoryId menu item category id
     * @param opUserId           操作人用户ID
     */
    void removeItemFromCategory(Long menuItemId, Long menuItemCategoryId, Long opUserId);

    /**
     * 创建menu core.
     *
     * @param createMenuCore 参数
     * @param createMode     create mode
     * @return 创建结果
     */
    CreateResult createMenuCore(CreateMenuCore createMenuCore, CreateMode createMode);

    /**
     * 获取通过角色获得的所有menu item.
     *
     * @param roleIds permission role id集合
     * @return menu item的集合
     */
    List<DescribeMenuItemCore> getMenuItemsFromRole(List<Long> roleIds);

    /**
     * 根据ID获取.
     *
     * @param ids menu item id
     * @return {@link DescribeMenuItemCore}
     */
    List<DescribeMenuItemCore> getMenuItemsById(List<Long> ids);

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
    List<DescribeMenuItemCore> getMenuItemsByCategoryId(List<Long> menuItemCategoryIds);

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
