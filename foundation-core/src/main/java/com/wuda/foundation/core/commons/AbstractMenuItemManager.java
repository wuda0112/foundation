package com.wuda.foundation.core.commons;

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
}
