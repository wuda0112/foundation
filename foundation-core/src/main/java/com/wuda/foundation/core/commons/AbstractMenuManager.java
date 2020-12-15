package com.wuda.foundation.core.commons;

import java.util.List;

public abstract class AbstractMenuManager implements MenuManager {

    @Override
    public List<DescribeMenuItem> getMenuItemsFromRole(List<Long> roleIds) {
        return getMenuItemsFromRoleDbOp(roleIds);
    }

    protected abstract List<DescribeMenuItem> getMenuItemsFromRoleDbOp(List<Long> roleIds);

    @Override
    public List<DescribeMenuItem> getMenuItemsById(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return getMenuItemsByIdDbOp(ids);
    }

    protected abstract List<DescribeMenuItem> getMenuItemsByIdDbOp(List<Long> ids);

    @Override
    public Menu getMenu(Long menuId) {
        return getMenuDbOp(menuId);
    }

    protected abstract Menu getMenuDbOp(Long menuId);

    @Override
    public List<DescribeMenuItem> getMenuItemsByCategoryId(List<Long> menuItemCategoryIds) {
        return getMenuItemsByCategoryIdDbOp(menuItemCategoryIds);
    }

    protected abstract List<DescribeMenuItem> getMenuItemsByCategoryIdDbOp(List<Long> menuItemCategoryIds);

    @Override
    public List<DescribeMenuItemCategory> getMenuItemCategory(Long menuId) {
        return getMenuItemCategoryDbOp(menuId);
    }

    protected abstract List<DescribeMenuItemCategory> getMenuItemCategoryDbOp(Long menuId);

    @Override
    public Long getMenuId(Long menuItemCategoryId) {
        return getMenuIdDbOp(menuItemCategoryId);
    }

    protected abstract Long getMenuIdDbOp(Long menuItemCategoryId);

}
