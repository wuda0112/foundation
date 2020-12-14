package com.wuda.foundation.core.commons;

import org.apache.commons.lang3.tuple.Triple;

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
     * 获取menu item所属的menu和category.
     *
     * @param menuItemIds menu item id集合
     * @return left: menu item id; middle: menu id; right: menu category id
     */
    List<Triple<Long, Long, Long>> getMenuAndCategory(List<Long> menuItemIds);
}
