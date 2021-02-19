package com.wuda.foundation.core.commons.impl;

import com.wuda.foundation.core.commons.*;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.PermissionGrantManager;
import com.wuda.foundation.core.security.Subject;
import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuCoreRecord;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemBelongsToCategoryRecord;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemCategoryRecord;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemCoreRecord;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.tree.IdPidEntryTreeBuilder;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuCore.MENU_CORE;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemBelongsToCategory.MENU_ITEM_BELONGS_TO_CATEGORY;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemCategory.MENU_ITEM_CATEGORY;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemCore.MENU_ITEM_CORE;

public class MenuManagerImpl extends AbstractMenuManager implements JooqCommonDbOp {

    private PermissionGrantManager permissionGrantManager;

    public void setPermissionGrantManager(PermissionGrantManager permissionGrantManager) {
        this.permissionGrantManager = permissionGrantManager;
    }

    @Override
    protected CreateResult createMenuItemCoreDbOp(CreateMenuItemCore createMenuItemCore, CreateMode createMode) {
        MenuItemCoreRecord record = menuItemCoreRecordForInsert(createMenuItemCore);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = menuItemCoreUniqueCondition(createMenuItemCore.getMenuItemId(), createMenuItemCore.getName());
        return insertDispatcher(JooqContext.getDataSource(), createMode, MENU_ITEM_CORE, record, existsRecordSelector);
    }

    @Override
    protected void updateMenuItemCoreDbOp(UpdateMenuItemCore updateMenuItemCore) {
        MenuItemCoreRecord record = menuItemCoreRecordForUpdate(updateMenuItemCore);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected void addItemToCategoryDbOp(Long menuItemId, Long menuItemCategoryId, Long opUserId) {
        MenuItemBelongsToCategoryRecord record = menuItemBelongsToCategoryRecordForInsert(menuItemId, menuItemCategoryId, opUserId);
        insert(JooqContext.getDataSource(), MENU_ITEM_BELONGS_TO_CATEGORY, record);
    }

    @Override
    protected void removeItemFromCategoryDbOp(Long menuItemId, Long menuItemCategoryId, Long opUserId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(MENU_ITEM_BELONGS_TO_CATEGORY)
                .set(MENU_ITEM_BELONGS_TO_CATEGORY.IS_DELETED, MENU_ITEM_BELONGS_TO_CATEGORY.ID)
                .where(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(menuItemCategoryId)))
                .and(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_ID.eq(ULong.valueOf(menuItemCategoryId)))
                .execute();
    }

    @Override
    protected CreateResult createMenuCoreDbOp(CreateMenuCore createMenuCore, CreateMode createMode) {
        MenuCoreRecord record = menuCoreRecordForInsert(createMenuCore);
        return insert(JooqContext.getDataSource(), MENU_CORE, record);
    }

    @Override
    protected List<DescribeMenuItemCore> getMenuItemsFromRoleDbOp(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return null;
        }
        List<Subject> roles = new ArrayList<>(roleIds.size());
        for (Long roleId : roleIds) {
            Subject role = new Subject(roleId, BuiltinIdentifierType.TABLE_PERMISSION_ROLE);
            roles.add(role);
        }
        List<DescribePermissionAssignment> permissions = permissionGrantManager.getPermissions(roles, BuiltinIdentifierType.TABLE_MENU_ITEM);
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }
        List<Long> menuItemIds = permissions.stream().map(DescribePermissionAssignment::getTarget).map(Target::getValue).collect(Collectors.toList());
        return getMenuItemsById(menuItemIds);
    }

    @Override
    protected List<DescribeMenuItemCore> getMenuItemsByIdDbOp(List<Long> menuItemIds) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<MenuItemCoreRecord> menuItemCoreRecords = dslContext.selectFrom(MENU_ITEM_CORE)
                .where(MENU_ITEM_CORE.MENU_ITEM_ID.in(menuItemIds))
                .and(MENU_ITEM_CORE.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromMenuItemRecords(menuItemCoreRecords);
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
            List<DescribeMenuItemCore> describeMenuItemCores = getMenuItemsByCategoryId(Collections.singletonList(describeMenuItemCategory.getId()));
            if (describeMenuItemCores != null && !describeMenuItemCores.isEmpty()) {
                for (DescribeMenuItemCore describeMenuItemCore : describeMenuItemCores) {
                    DescribeMenuNode describeMenuItemNode = DescribeMenuNode.newMenuItemNode(describeMenuItemCategory, describeMenuItemCore);
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
    protected List<DescribeMenuItemCore> getMenuItemsByCategoryIdDbOp(List<Long> menuItemCategoryIds) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<Record> result = dslContext.select(MENU_ITEM_CORE.fields())
                .from(MENU_ITEM_BELONGS_TO_CATEGORY)
                .leftJoin(MENU_ITEM_CORE).on(MENU_ITEM_CORE.MENU_ITEM_ID.eq(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_ID))
                .where(MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_CATEGORY_ID.in(menuItemCategoryIds))
                .and(MENU_ITEM_CORE.IS_DELETED.eq(notDeleted()))
                .and(MENU_ITEM_BELONGS_TO_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        if (result == null || result.isEmpty()) {
            return null;
        }
        List<DescribeMenuItemCore> list = new ArrayList<>(result.size());
        for (Record record : result) {
            MenuItemCoreRecord menuItemRecord = record.into(MenuItemCoreRecord.class);
            DescribeMenuItemCore describeMenuItemCore = copyFromMenuItemRecord(menuItemRecord);
            list.add(describeMenuItemCore);
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

    private DescribeMenuItemCore copyFromMenuItemRecord(MenuItemCoreRecord record) {
        DescribeMenuItemCore describeMenuItemCore = new DescribeMenuItemCore();
        describeMenuItemCore.setId(record.getMenuItemCoreId().longValue());
        describeMenuItemCore.setMenuItemId(record.getMenuItemId().longValue());
        describeMenuItemCore.setName(record.getName());
        describeMenuItemCore.setDescription(record.getDescription());
        return describeMenuItemCore;
    }

    private List<DescribeMenuItemCore> copyFromMenuItemRecords(List<MenuItemCoreRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeMenuItemCore> list = new ArrayList<>();
        for (MenuItemCoreRecord record : records) {
            DescribeMenuItemCore describeMenuItemCore = copyFromMenuItemRecord(record);
            list.add(describeMenuItemCore);
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

    private MenuItemCoreRecord menuItemCoreRecordForInsert(CreateMenuItemCore createMenuItemCore) {
        LocalDateTime now = LocalDateTime.now();
        Long opUserId = createMenuItemCore.getOpUserId();
        return new MenuItemCoreRecord(ULong.valueOf(createMenuItemCore.getId()),
                ULong.valueOf(createMenuItemCore.getMenuItemId()),
                createMenuItemCore.getName(),
                createMenuItemCore.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private MenuItemCoreRecord menuItemCoreRecordForUpdate(UpdateMenuItemCore updateMenuItemCore) {
        MenuItemCoreRecord record = new MenuItemCoreRecord();
        record.setMenuItemCoreId(ULong.valueOf(updateMenuItemCore.getId()));
        record.setName(updateMenuItemCore.getName());
        record.setDescription(updateMenuItemCore.getDescription());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(updateMenuItemCore.getOpUserId()));
        return record;
    }


    private MenuCoreRecord menuCoreRecordForInsert(CreateMenuCore createMenuCore) {
        LocalDateTime now = LocalDateTime.now();
        Long opUserId = createMenuCore.getOpUserId();
        return new MenuCoreRecord(ULong.valueOf(createMenuCore.getId()),
                ULong.valueOf(createMenuCore.getMenuId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private MenuItemBelongsToCategoryRecord menuItemBelongsToCategoryRecordForInsert(Long menuItemId, Long menuItemCategoryId, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new MenuItemBelongsToCategoryRecord(ULong.valueOf(FoundationContext.getLongKeyGenerator().next()),
                ULong.valueOf(menuItemId),
                ULong.valueOf(menuItemCategoryId),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private SelectConditionStep<Record1<ULong>> menuItemCoreUniqueCondition(Long menuItemId, String name) {
        Configuration configuration = JooqContext.getConfiguration();
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(MENU_ITEM_CORE.MENU_ITEM_CORE_ID)
                .from(MENU_ITEM_CORE)
                .where(MENU_ITEM_CORE.MENU_ITEM_ID.eq(ULong.valueOf(menuItemId)))
                .and(MENU_ITEM_CORE.NAME.eq(name))
                .and(MENU_ITEM_CORE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return existsRecordSelector;
    }

}
