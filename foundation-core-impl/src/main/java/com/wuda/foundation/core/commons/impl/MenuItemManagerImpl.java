package com.wuda.foundation.core.commons.impl;

import com.wuda.foundation.core.commons.AbstractMenuItemManager;
import com.wuda.foundation.core.commons.DescribeMenuItem;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.PermissionGrantManager;
import com.wuda.foundation.core.security.Subject;
import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemRecord;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import org.jooq.DSLContext;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItem.MENU_ITEM;

public class MenuItemManagerImpl extends AbstractMenuItemManager implements JooqCommonDbOp {

    private PermissionGrantManager permissionGrantManager;

    public void setPermissionGrantManager(PermissionGrantManager permissionGrantManager) {
        this.permissionGrantManager = permissionGrantManager;
    }

    @Override
    protected List<DescribeMenuItem> getMenuItemsFromRoleDbOp(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }
        List<Subject> roles = new ArrayList<>(roleIds.size());
        for (Long roleId : roleIds) {
            Subject role = new Subject(roleId, BuiltinIdentifierType.PERMISSION_ROLE);
            roles.add(role);
        }
        List<DescribePermissionAssignment> permissions = permissionGrantManager.getPermissions(roles, BuiltinIdentifierType.MENU_ITEM);
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }
        List<Long> menuItemIds = permissions.stream().map(DescribePermissionAssignment::getTarget).map(Target::getValue).collect(Collectors.toList());
        return getMenuItemsById(menuItemIds);
    }

    @Override
    protected List<DescribeMenuItem> getMenuItemsByIdDbOp(List<Long> ids) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<MenuItemRecord> menuItemRecords = dslContext.selectFrom(MENU_ITEM)
                .where(MENU_ITEM.MENU_ITEM_ID.in(ids))
                .and(MENU_ITEM.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromMenuItemRecords(menuItemRecords);
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
