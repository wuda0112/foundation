package com.wuda.foundation.item.impl;

import com.wuda.foundation.item.*;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.FoundationContext;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemCore.ITEM_CORE;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemDescription.ITEM_DESCRIPTION;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemGeneral.ITEM_GENERAL;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemVariation.ITEM_VARIATION;

public class ItemManagerImpl extends AbstractItemManager implements JooqCommonDbOp {

    @Override
    protected void directBatchInsertItemCoreDbOp(List<CreateItemCore> createItemCores, Long opUserId) {
        batchInsert(JooqContext.getDataSource(), ITEM_CORE, itemCoreRecordsForInsert(createItemCores, opUserId));
    }

    @Override
    protected long createItemCoreDbOp(CreateItemCore createItemCore, Long opUserId) {
        long itemCoreId = insert(JooqContext.getDataSource(), ITEM_CORE, itemCoreRecordForInsert(createItemCore, opUserId)).getRecordId();
        createItemCategoryRelation(createItemCore.getCategoryId(), createItemCore.getItemId(), opUserId);
        createItemStoreRelation(createItemCore.getStoreId(), createItemCore.getItemId(), opUserId);
        return itemCoreId;
    }

    private void createItemCategoryRelation(Long categoryId, Long itemId, Long opUserId) {
        ItemGroupRelationRecord itemGroupRelationRecord = itemGroupRelationRecordForInsert(categoryId, itemId, opUserId);
        attach(JooqContext.getDataSource(), itemGroupRelationRecord);
        itemGroupRelationRecord.insert();
    }

    private void createItemStoreRelation(Long storeId, Long itemId, Long opUserId) {
        ItemGroupRelationRecord itemGroupRelationRecord = itemGroupRelationRecordForInsert(storeId, itemId, opUserId);
        attach(JooqContext.getDataSource(), itemGroupRelationRecord);
        itemGroupRelationRecord.insert();
    }

    private ItemGroupRelationRecord itemGroupRelationRecordForInsert(Long groupId, Long itemId, Long opUserId) {
        long id = FoundationContext.getLongKeyGenerator().next();
        LocalDateTime now = LocalDateTime.now();
        return new ItemGroupRelationRecord(ULong.valueOf(id),
                ULong.valueOf(itemId),
                ULong.valueOf(groupId),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    @Override
    protected void directBatchInsertItemGeneralDbOp(List<CreateItemGeneral> createItemGenerals, Long opUserId) {
        batchInsert(JooqContext.getDataSource(), ITEM_GENERAL, itemGeneralRecordsForInsert(createItemGenerals, opUserId));
    }

    @Override
    protected long createOrUpdateItemGeneralDbOp(CreateItemGeneral createItemGeneral, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(ITEM_GENERAL.ITEM_GENERAL_ID)
                .from(ITEM_GENERAL)
                .where(ITEM_GENERAL.ITEM_ID.eq(ULong.valueOf(createItemGeneral.getItemId())))
                .and(ITEM_GENERAL.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Consumer<Long> updateAction = (itemGeneralId) -> {
            ItemGeneralRecord itemGeneralRecord = itemGeneralRecordForUpdate(itemGeneralId, createItemGeneral, opUserId);
            itemGeneralRecord.attach(configuration);
            itemGeneralRecord.update();
        };
        return insertOrUpdate(createMode, JooqContext.getDataSource(), ITEM_GENERAL, itemGeneralRecordForInsert(createItemGeneral, opUserId), existsRecordSelector, updateAction);
    }

    @Override
    protected void directBatchInsertItemVariationDbOp(List<CreateItemVariation> createItemVariations, Long opUserId) {
        batchInsert(JooqContext.getDataSource(), ITEM_VARIATION, itemVariationRecordsForInsert(createItemVariations, opUserId));
    }

    @Override
    protected void directBatchInsertItemDescriptionDbOp(List<CreateItemDescription> createItemDescriptions, Long opUserId) {
        batchInsert(JooqContext.getDataSource(), ITEM_DESCRIPTION, itemDescriptionRecordsForInsert(createItemDescriptions, opUserId));
    }

    @Override
    protected long createOrUpdateItemDescriptionDbOp(CreateItemDescription createItemDescription, CreateMode createMode, Long opUserId) {
        ItemDescriptionRecord record = itemDescriptionRecordForInsert(createItemDescription, opUserId);
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID)
                .from(ITEM_DESCRIPTION)
                .where(ITEM_DESCRIPTION.ITEM_ID.eq(ULong.valueOf(createItemDescription.getItemId())))
                .and(ITEM_DESCRIPTION.ITEM_VARIATION_ID.eq(ULong.valueOf(createItemDescription.getItemVariationId())))
                .and(ITEM_DESCRIPTION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Consumer<Long> existsRecordUpdateAction = itemDescriptionRecordId -> {
            updateDescriptionDbOp(itemDescriptionRecordId, createItemDescription.getContent(), opUserId);
        };
        return insertOrUpdate(CreateMode.CREATE_AFTER_SELECT_CHECK, JooqContext.getDataSource(), ITEM_DESCRIPTION, record, existsRecordSelector, existsRecordUpdateAction);
    }

    @Override
    protected long createItemVariationDbOp(CreateItemVariation createItemVariation, Long opUserId) {
        return insert(JooqContext.getDataSource(), ITEM_VARIATION, itemVariationRecordForInsert(createItemVariation, opUserId)).getRecordId();
    }

    @Override
    protected long updateDescriptionDbOp(Long itemDescriptionId, String description, Long opUserId) {
        ItemDescriptionRecord itemDescriptionRecord = new ItemDescriptionRecord();
        itemDescriptionRecord.setItemDescriptionId(ULong.valueOf(itemDescriptionId));
        itemDescriptionRecord.setContent(description);
        itemDescriptionRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        itemDescriptionRecord.setLastModifyTime(LocalDateTime.now());
        Configuration configuration = JooqContext.getConfiguration(JooqContext.getDataSource());
        itemDescriptionRecord.attach(configuration);
        return itemDescriptionRecord.update();
    }


    private ItemCoreRecord itemCoreRecordForInsert(CreateItemCore createItemCore, long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemCoreRecord(ULong.valueOf(createItemCore.getId()),
                ULong.valueOf(createItemCore.getItemId()),
                UByte.valueOf(createItemCore.getItemType().getCode()),
                UByte.valueOf(createItemCore.getItemState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<ItemCoreRecord> itemCoreRecordsForInsert(List<CreateItemCore> createItemCores, Long opUserId) {
        List<ItemCoreRecord> list = new ArrayList<>(createItemCores.size());
        for (CreateItemCore createItemCore : createItemCores) {
            list.add(itemCoreRecordForInsert(createItemCore, opUserId));
        }
        return list;
    }

    private ItemGeneralRecord itemGeneralRecordForInsert(CreateItemGeneral createItemGeneral, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemGeneralRecord(ULong.valueOf(createItemGeneral.getId()),
                ULong.valueOf(createItemGeneral.getItemId()),
                createItemGeneral.getName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private ItemGeneralRecord itemGeneralRecordForUpdate(long id, CreateItemGeneral createItemGeneral, Long opUserId) {
        ItemGeneralRecord itemGeneralRecord = new ItemGeneralRecord();
        itemGeneralRecord.setItemGeneralId(ULong.valueOf(id));
        itemGeneralRecord.setName(createItemGeneral.getName());
        itemGeneralRecord.setLastModifyTime(LocalDateTime.now());
        itemGeneralRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        return itemGeneralRecord;
    }

    private List<ItemGeneralRecord> itemGeneralRecordsForInsert(List<CreateItemGeneral> createItemGenerals, Long opUserId) {
        List<ItemGeneralRecord> list = new ArrayList<>(createItemGenerals.size());
        for (CreateItemGeneral createItemGeneral : createItemGenerals) {
            list.add(itemGeneralRecordForInsert(createItemGeneral, opUserId));
        }
        return list;
    }

    private ItemVariationRecord itemVariationRecordForInsert(CreateItemVariation createItemVariation, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemVariationRecord(ULong.valueOf(createItemVariation.getId()),
                ULong.valueOf(createItemVariation.getItemId()),
                createItemVariation.getName(),
                UByte.valueOf(createItemVariation.getState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<ItemVariationRecord> itemVariationRecordsForInsert(List<CreateItemVariation> createItemVariations, Long opUserId) {
        List<ItemVariationRecord> list = new ArrayList<>(createItemVariations.size());
        for (CreateItemVariation createItemVariation : createItemVariations) {
            list.add(itemVariationRecordForInsert(createItemVariation, opUserId));
        }
        return list;
    }

    private ItemDescriptionRecord itemDescriptionRecordForInsert(CreateItemDescription createItemDescription, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemDescriptionRecord(ULong.valueOf(createItemDescription.getId()),
                ULong.valueOf(createItemDescription.getItemId()),
                ULong.valueOf(createItemDescription.getItemVariationId()),
                createItemDescription.getContent(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<ItemDescriptionRecord> itemDescriptionRecordsForInsert(List<CreateItemDescription> createItemDescriptions, Long opUserId) {
        List<ItemDescriptionRecord> list = new ArrayList<>(createItemDescriptions.size());
        for (CreateItemDescription createItemDescription : createItemDescriptions) {
            list.add(itemDescriptionRecordForInsert(createItemDescription, opUserId));
        }
        return list;
    }
}
