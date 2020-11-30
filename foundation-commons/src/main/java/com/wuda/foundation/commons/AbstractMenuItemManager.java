package com.wuda.foundation.commons;

import java.util.List;

public abstract class AbstractMenuItemManager implements MenuItemManager{

    public List<DescribeMenuItem> getMenuItemsFromRole(List<Long> roleIds){
        return getMenuItemsFromRoleDbOp(roleIds);
    }

    protected abstract List<DescribeMenuItem> getMenuItemsFromRoleDbOp(List<Long> roleIds);
}
