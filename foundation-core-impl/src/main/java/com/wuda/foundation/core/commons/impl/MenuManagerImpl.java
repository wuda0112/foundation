package com.wuda.foundation.core.commons.impl;

import com.wuda.foundation.core.commons.*;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.PermissionGrantManager;
import com.wuda.foundation.core.security.Subject;
import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemCategoryRecord;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemRecord;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.tree.IdPidEntryTreeBuilder;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.types.ULong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItem.MENU_ITEM;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemBelongsToCategory.MENU_ITEM_BELONGS_TO_CATEGORY;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemCategory.MENU_ITEM_CATEGORY;

public class MenuManagerImpl extends AbstractMenuManager implements JooqCommonDbOp {

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

    @Override
    protected Menu getMenuDbOp(Long menuId) {
        List<DescribeMenuItemCategory> describeMenuItemCategories = getMenuItemCategoryDbOp(menuId);
        if (describeMenuItemCategories == null || describeMenuItemCategories.isEmpty()) {
            return new Menu(menuId, null);
        }
        List<DescribeMenuNode> nodes = new ArrayList<>();
        DescribeMenuNode root = null;
        for (DescribeMenuItemCategory describeMenuItemCategory : describeMenuItemCategories) {
            DescribeMenuNode describeMenuItemCategoryNode = DescribeMenuNode.newMenuItemCategoryNode(describeMenuItemCategory);
            if (FoundationContext.isRootTreeNode(describeMenuItemCategoryNode)) {
                root = describeMenuItemCategoryNode;
            }
            nodes.add(describeMenuItemCategoryNode);
            List<DescribeMenuItem> describeMenuItems = getMenuItemsByCategoryId(Collections.singletonList(describeMenuItemCategory.getId()));
            if (describeMenuItems != null && !describeMenuItems.isEmpty()) {
                for (DescribeMenuItem describeMenuItem : describeMenuItems) {
                    DescribeMenuNode describeMenuItemNode = DescribeMenuNode.newMenuItemNode(describeMenuItemCategory, describeMenuItem);
                    nodes.add(describeMenuItemNode);
                }
            }
        }
        Menu menu = new Menu(menuId, root);
        IdPidEntryTreeBuilder<Long, DescribeMenuNode> builder = new IdPidEntryTreeBuilder<>();
        builder.add(menu, nodes);
        return menu;
    }

    @Override
    protected List<DescribeMenuItem> getMenuItemsByCategoryIdDbOp(List<Long> menuItemCategoryIds) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record> result = dslContext.select(MENU_ITEM.fields())
                .from(MENU_ITEM_BELONGS_TO_CATEGORY)
                .leftJoin(MENU_ITEM).on(MENU_ITEM.MENU_ITEM_ID.eq(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_ID))
                .where(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_CATEGORY_ID.in(menuItemCategoryIds))
                .and(MENU_ITEM.IS_DELETED.eq(notDeleted()))
                .and(MENU_ITEM_BELONGS_TO_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (result == null || result.isEmpty()) {
            return null;
        }
        List<DescribeMenuItem> list = new ArrayList<>(result.size());
        for (Record record : result) {
            MenuItemRecord menuItemRecord = record.into(MenuItemRecord.class);
            DescribeMenuItem describeMenuItem = copyFromMenuItemRecord(menuItemRecord);
            list.add(describeMenuItem);
        }
        return list;
    }

    @Override
    protected List<DescribeMenuItemCategory> getMenuItemCategoryDbOp(Long menuId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<MenuItemCategoryRecord> records = dslContext.selectFrom(MENU_ITEM_CATEGORY)
                .where(MENU_ITEM_CATEGORY.MENU_ID.eq(ULong.valueOf(menuId)))
                .and(MENU_ITEM_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromMenuItemCategoryRecords(records);
    }

    @Override
    protected Long getMenuIdDbOp(Long menuItemCategoryId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Record1<ULong> record1 = dslContext.select(MENU_ITEM_CATEGORY.MENU_ID)
                .from(MENU_ITEM_CATEGORY)
                .where(MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(menuItemCategoryId)))
                .fetchOne();
        if (record1 == null) {
            return null;
        }
        return record1.component1().longValue();
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

    private DescribeMenuItemCategory copyFromMenuItemCategoryRecord(MenuItemCategoryRecord record) {
        DescribeMenuItemCategory describeMenuItemCategory = new DescribeMenuItemCategory();
        describeMenuItemCategory.setId(record.getMenuItemCategoryId().longValue());
        describeMenuItemCategory.setMenuId(record.getMenuId().longValue());
        describeMenuItemCategory.setParentId(record.getParentMenuItemCategoryId().longValue());
        describeMenuItemCategory.setRootId(record.getRootMenuItemCategoryId().longValue());
        describeMenuItemCategory.setDepth(record.getDepth().intValue());
        describeMenuItemCategory.setName(record.getName());
        describeMenuItemCategory.setDescription(record.getDescription());
        return describeMenuItemCategory;
    }

    private List<DescribeMenuItemCategory> copyFromMenuItemCategoryRecords(List<MenuItemCategoryRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeMenuItemCategory> list = new ArrayList<>();
        for (MenuItemCategoryRecord record : records) {
            DescribeMenuItemCategory describeMenuItemCategory = copyFromMenuItemCategoryRecord(record);
            list.add(describeMenuItemCategory);
        }
        return list;
    }
}
