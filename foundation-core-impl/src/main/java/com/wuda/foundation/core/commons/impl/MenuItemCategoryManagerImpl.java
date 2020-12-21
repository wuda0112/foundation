package com.wuda.foundation.core.commons.impl;

import com.wuda.foundation.core.commons.AbstractMenuItemCategoryManager;
import com.wuda.foundation.core.commons.CreateMenuItemCategory;
import com.wuda.foundation.core.commons.DescribeMenuItemCategory;
import com.wuda.foundation.core.commons.UpdateMenuItemCategory;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemCategoryRecord;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.RelatedDataExists;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemBelongsToCategory.MENU_ITEM_BELONGS_TO_CATEGORY;
import static com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemCategory.MENU_ITEM_CATEGORY;

public class MenuItemCategoryManagerImpl extends AbstractMenuItemCategoryManager implements JooqCommonDbOp {
    @Override
    protected int itemCountInCategoryDbOp(Long categoryId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        return dslContext.fetchCount(MENU_ITEM_BELONGS_TO_CATEGORY,
                MENU_ITEM_BELONGS_TO_CATEGORY.MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(categoryId))
                        .and(MENU_ITEM_BELONGS_TO_CATEGORY.IS_DELETED.eq(notDeleted())));
    }

    @Override
    protected int childCountDbOp(Long nodeId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        return dslContext.fetchCount(MENU_ITEM_CATEGORY,
                MENU_ITEM_CATEGORY.PARENT_MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(nodeId))
                        .and(MENU_ITEM_CATEGORY.IS_DELETED.eq(notDeleted())));
    }

    @Override
    protected List<DescribeMenuItemCategory> getChildrenDbOp(Long nodeId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<MenuItemCategoryRecord> records = dslContext.selectFrom(MENU_ITEM_CATEGORY)
                .where(MENU_ITEM_CATEGORY.PARENT_MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .and(MENU_ITEM_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromMenuItemCategoryRecords(records);
    }

    @Override
    protected DescribeMenuItemCategory getTreeNodeDbOp(Long nodeId) {
        MenuItemCategoryRecord record = getById(nodeId, MENU_ITEM_CATEGORY);
        return copyFromMenuItemCategoryRecord(record);
    }

    @Override
    protected List<DescribeMenuItemCategory> getDescendantOfRootDbOp(Long rootId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<MenuItemCategoryRecord> records = dslContext.selectFrom(MENU_ITEM_CATEGORY)
                .where(MENU_ITEM_CATEGORY.ROOT_MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(rootId)))
                .and(MENU_ITEM_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromMenuItemCategoryRecords(records);

    }

    @Override
    protected boolean checkNameExistsDbOp(Long parentId, String childName) {
        int count = JooqContext.getOrCreateDSLContext()
                .fetchCount(MENU_ITEM_CATEGORY,
                        MENU_ITEM_CATEGORY.PARENT_MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(parentId))
                                .and(MENU_ITEM_CATEGORY.NAME.eq(childName))
                                .and(MENU_ITEM_CATEGORY.IS_DELETED.eq(notDeleted())));
        return count > 0;
    }

    @Override
    protected CreateResult createTreeNodeDbOp(CreateMenuItemCategory createTreeNode, CreateMode createMode, Long opUserId) {
        MenuItemCategoryRecord record = menuItemCategoryRecordForInsert(createTreeNode, opUserId);
        return insert(JooqContext.getDataSource(), MENU_ITEM_CATEGORY, record);
    }

    @Override
    protected void updateTreeNodeDbOp(UpdateMenuItemCategory updateTreeNode, Long opUserId) {
        MenuItemCategoryRecord record = menuItemCategoryRecordForUpdate(updateTreeNode, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected void deleteTreeNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        int itemCount = itemCountInCategory(nodeId);
        if (itemCount > 0) {
            throw new RelatedDataExists("该分类下还有menu item");
        }
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        dslContext.update(MENU_ITEM_CATEGORY)
                .set(MENU_ITEM_CATEGORY.IS_DELETED, MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID)
                .set(MENU_ITEM_CATEGORY.LAST_MODIFY_USER_ID, ULong.valueOf(opUserId))
                .set(MENU_ITEM_CATEGORY.LAST_MODIFY_TIME, LocalDateTime.now())
                .where(MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .execute();
    }

    private DescribeMenuItemCategory copyFromMenuItemCategoryRecord(MenuItemCategoryRecord record) {
        if (record == null) {
            return null;
        }
        DescribeMenuItemCategory describeMenuItemCategory = new DescribeMenuItemCategory();
        describeMenuItemCategory.setMenuId(record.getMenuId().longValue());
        describeMenuItemCategory.setDepth(record.getDepth().intValue());
        describeMenuItemCategory.setDescription(record.getDescription());
        describeMenuItemCategory.setId(record.getMenuItemCategoryId().longValue());
        describeMenuItemCategory.setName(record.getName());
        describeMenuItemCategory.setParentId(record.getParentMenuItemCategoryId().longValue());
        describeMenuItemCategory.setRootId(record.getRootMenuItemCategoryId().longValue());
        return describeMenuItemCategory;
    }

    private List<DescribeMenuItemCategory> copyFromMenuItemCategoryRecords(List<MenuItemCategoryRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeMenuItemCategory> list = new ArrayList<>(records.size());
        for (MenuItemCategoryRecord record : records) {
            DescribeMenuItemCategory describeMenuItemCategory = copyFromMenuItemCategoryRecord(record);
            list.add(describeMenuItemCategory);
        }
        return list;
    }

    private MenuItemCategoryRecord menuItemCategoryRecordForInsert(CreateMenuItemCategory createMenuItemCategory, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new MenuItemCategoryRecord(ULong.valueOf(createMenuItemCategory.getId()),
                ULong.valueOf(createMenuItemCategory.getMenuId()),
                ULong.valueOf(createMenuItemCategory.getParentId()),
                ULong.valueOf(createMenuItemCategory.getRootId()),
                UByte.valueOf(createMenuItemCategory.getDepth()),
                createMenuItemCategory.getName(),
                createMenuItemCategory.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private MenuItemCategoryRecord menuItemCategoryRecordForUpdate(UpdateMenuItemCategory updateMenuItemCategory, Long opUserId) {
        MenuItemCategoryRecord record = new MenuItemCategoryRecord();
        record.setMenuItemCategoryId(ULong.valueOf(updateMenuItemCategory.getId()));
        record.setName(updateMenuItemCategory.getName());
        record.setDescription(updateMenuItemCategory.getDescription());
        return record;
    }

}
