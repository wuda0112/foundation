package com.wuda.foundation.core.commons;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public abstract class AbstractMenuItemManager implements MenuItemManager {

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
    public List<Triple<Long, Long, Long>> getMenuAndCategory(List<Long> menuItemIds) {
        return getMenuAndCategoryDbOp(menuItemIds);
    }

    protected abstract List<Triple<Long, Long, Long>> getMenuAndCategoryDbOp(List<Long> menuItemIds);
}
