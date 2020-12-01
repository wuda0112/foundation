package com.wuda.foundation.core.commons.impl;

import com.wuda.foundation.core.commons.AbstractMenuItemManager;
import com.wuda.foundation.core.commons.DescribeMenuItem;
import com.wuda.foundation.core.commons.impl.jooq.generation.tables.records.MenuItemRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;

import java.util.ArrayList;
import java.util.List;

public class MenuItemManagerImpl extends AbstractMenuItemManager implements JooqCommonDbOp {

    @Override
    protected List<DescribeMenuItem> getMenuItemsFromRoleDbOp(List<Long> roleIds) {
        return null;
    }

    private DescribeMenuItem copyFromMenuItemRecord(MenuItemRecord record) {
        DescribeMenuItem describeMenuItem = new DescribeMenuItem();
        describeMenuItem.setId(record.getMenuItemId().longValue());
        describeMenuItem.setName(record.getName());
        describeMenuItem.setDescription(record.getDescription());
        return describeMenuItem;
    }

    private List<DescribeMenuItem> copyFromMenuItemRecords(List<MenuItemRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeMenuItem> list = new ArrayList<>();
        for (MenuItemRecord record : records) {
            DescribeMenuItem describeMenuItem = copyFromMenuItemRecord(record);
            list.add(describeMenuItem);
        }
        return list;
    }
}
