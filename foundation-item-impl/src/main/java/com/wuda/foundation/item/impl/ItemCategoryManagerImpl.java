package com.wuda.foundation.item.impl;

import com.wuda.foundation.item.AbstractItemCategoryManager;
import com.wuda.foundation.item.CreateItemCategory;
import com.wuda.foundation.item.DescribeItemCategory;
import com.wuda.foundation.item.UpdateItemCategory;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemCategoryRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.RelatedDataExists;
import com.wuda.foundation.lang.identify.BuiltinIdentifierTypes;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemCategory.ITEM_CATEGORY;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemGroupRelation.ITEM_GROUP_RELATION;

public class ItemCategoryManagerImpl extends AbstractItemCategoryManager implements JooqCommonDbOp {

    @Override
    protected int itemCountInCategoryDbOp(Long categoryId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource());
        return dslContext.fetchCount(ITEM_GROUP_RELATION,
                ITEM_GROUP_RELATION.GROUP_IDENTIFIER.eq(ULong.valueOf(categoryId))
                        .and(ITEM_GROUP_RELATION.GROUP_TYPE.eq(UShort.valueOf(BuiltinIdentifierTypes.ITEM_CATEGORY.getCode())))
                        .and(ITEM_GROUP_RELATION.IS_DELETED.eq(notDeleted())));
    }

    @Override
    public int childCount(Long categoryId) {
        return JooqContext.getOrCreateDSLContext(JooqContext.getDataSource())
                .fetchCount(ITEM_CATEGORY,
                        ITEM_CATEGORY.PARENT_ITEM_CATEGORY_ID.eq(ULong.valueOf(categoryId))
                                .and(ITEM_CATEGORY.IS_DELETED.eq(notDeleted())));
    }

    @Override
    public List<DescribeItemCategory> getChildren(Long nodeId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<ItemCategoryRecord> records = dslContext.selectFrom(ITEM_CATEGORY)
                .where(ITEM_CATEGORY.PARENT_ITEM_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .and(ITEM_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromItemCategoryRecords(records);
    }

    @Override
    protected CreateResult createTreeNodeDbOp(CreateItemCategory createItemCategory, CreateMode createMode, Long opUserId) {
        return insert(JooqContext.getDataSource(), ITEM_CATEGORY, itemCategoryRecordForInsert(createItemCategory, opUserId));
    }

    @Override
    protected void updateTreeNodeDbOp(UpdateItemCategory updateItemCategory, Long opUserId) {
        ItemCategoryRecord record = itemCategoryRecordForUpdate(updateItemCategory, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected void deleteTreeNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        int itemCount = itemCountInCategory(nodeId);
        if (itemCount > 0) {
            throw new RelatedDataExists("该分类下还有item");
        }
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource());
        dslContext.update(ITEM_CATEGORY)
                .set(ITEM_CATEGORY.IS_DELETED, ITEM_CATEGORY.ITEM_CATEGORY_ID)
                .set(ITEM_CATEGORY.LAST_MODIFY_USER_ID, ULong.valueOf(opUserId))
                .set(ITEM_CATEGORY.LAST_MODIFY_TIME, LocalDateTime.now())
                .where(ITEM_CATEGORY.ITEM_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .execute();
    }

    @Override
    public DescribeItemCategory getTreeNode(Long nodeId) {
        ItemCategoryRecord record = getById(nodeId, ITEM_CATEGORY);
        return copyFromItemCategoryRecord(record);
    }

    @Override
    public List<DescribeItemCategory> getDescendantOfRoot(Long rootId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<ItemCategoryRecord> records = dslContext.selectFrom(ITEM_CATEGORY)
                .where(ITEM_CATEGORY.ROOT_ITEM_CATEGORY_ID.eq(ULong.valueOf(rootId)))
                .and(ITEM_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromItemCategoryRecords(records);
    }

    @Override
    public boolean checkNameExists(Long parentId, String childName) {
        int count = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource())
                .fetchCount(ITEM_CATEGORY,
                        ITEM_CATEGORY.PARENT_ITEM_CATEGORY_ID.eq(ULong.valueOf(parentId))
                                .and(ITEM_CATEGORY.NAME.eq(childName))
                                .and(ITEM_CATEGORY.IS_DELETED.eq(notDeleted())));
        return count > 0;
    }

    private ItemCategoryRecord itemCategoryRecordForInsert(CreateItemCategory createItemCategory, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemCategoryRecord(ULong.valueOf(createItemCategory.getId()),
                ULong.valueOf(createItemCategory.getParentId()),
                ULong.valueOf(createItemCategory.getRootId()),
                ULong.valueOf(createItemCategory.getStoreId()),
                UByte.valueOf(createItemCategory.getDepth()),
                createItemCategory.getName(),
                createItemCategory.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private ItemCategoryRecord itemCategoryRecordForUpdate(UpdateItemCategory updateItemCategory, Long opUserId) {
        ItemCategoryRecord record = new ItemCategoryRecord();
        record.setItemCategoryId(ULong.valueOf(updateItemCategory.getId()));
        record.setName(updateItemCategory.getName());
        record.setDescription(updateItemCategory.getDescription());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        return record;
    }

    private DescribeItemCategory copyFromItemCategoryRecord(ItemCategoryRecord record) {
        DescribeItemCategory describeItemCategory = new DescribeItemCategory();
        describeItemCategory.setId(record.getItemCategoryId().longValue());
        describeItemCategory.setParentId(record.getParentItemCategoryId().longValue());
        describeItemCategory.setRootId(record.getRootItemCategoryId().longValue());
        describeItemCategory.setDepth(record.getDepth().intValue());
        describeItemCategory.setName(record.getName());
        describeItemCategory.setDescription(record.getDescription());
        describeItemCategory.setStoreId(record.getStoreId().longValue());
        return describeItemCategory;
    }

    private List<DescribeItemCategory> copyFromItemCategoryRecords(Result<ItemCategoryRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeItemCategory> list = new ArrayList<>();
        for (ItemCategoryRecord record : records) {
            list.add(copyFromItemCategoryRecord(record));
        }
        return list;
    }
}
