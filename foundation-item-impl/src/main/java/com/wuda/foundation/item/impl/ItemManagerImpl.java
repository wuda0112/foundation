package com.wuda.foundation.item.impl;

import com.wuda.foundation.item.*;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemDescriptionRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemGeneralRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemVariationRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.wuda.foundation.item.impl.jooq.generation.tables.Item.ITEM;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemDescription.ITEM_DESCRIPTION;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemGeneral.ITEM_GENERAL;
import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemVariation.ITEM_VARIATION;

public class ItemManagerImpl extends AbstractItemManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void directBatchInsertItemDbOp(List<CreateItem> createItems, Long opUserId) {
        batchInsert(dataSource, ITEM, itemRecordsForInsert(createItems, opUserId));
    }

    @Override
    protected long createItemDbOp(CreateItem createItem, Long opUserId) {
        Field[] fields = itemRecordForInsert(createItem, opUserId).fields();
        return insert(dataSource, ITEM, fields).getRecordId();
    }

    @Override
    protected void directBatchInsertItemGeneralDbOp(List<CreateItemGeneral> createItemGenerals, Long opUserId) {
        batchInsert(dataSource, ITEM_GENERAL, itemGeneralRecordsForInsert(createItemGenerals, opUserId));
    }

    @Override
    protected long createItemGeneralDbOp(CreateItemGeneral createItemGeneral, Long opUserId) {
        Field[] fields = itemGeneralRecordForInsert(createItemGeneral, opUserId).fields();
        return insert(dataSource, ITEM_GENERAL, fields).getRecordId();
    }

    @Override
    protected void directBatchInsertItemVariationDbOp(List<CreateItemVariation> createItemVariations, Long opUserId) {
        batchInsert(dataSource, ITEM_VARIATION, itemVariationRecordsForInsert(createItemVariations, opUserId));
    }

    @Override
    protected void directBatchInsertItemDescriptionDbOp(List<CreateItemDescription> createItemDescriptions, Long opUserId) {
        batchInsert(dataSource, ITEM_DESCRIPTION, itemDescriptionRecordsForInsert(createItemDescriptions, opUserId));
    }

    @Override
    protected long createItemDescriptionDbOp(CreateItemDescription createItemDescription, InsertMode insertMode, Long opUserId) {
        Field[] fields = itemDescriptionRecordForInsert(createItemDescription, opUserId).fields();
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID)
                .from(ITEM_DESCRIPTION)
                .where(ITEM_DESCRIPTION.ITEM_ID.eq(ULong.valueOf(createItemDescription.getItemId())))
                .and(ITEM_DESCRIPTION.ITEM_VARIATION_ID.eq(ULong.valueOf(createItemDescription.getItemVariationId())));
        return insertDispatcher(dataSource, insertMode, ITEM_DESCRIPTION, fields, existsRecordSelector).getRecordId();
    }

    @Override
    protected long createItemVariationDbOp(CreateItemVariation createItemVariation, Long opUserId) {
        Field[] fields = itemVariationRecordForInsert(createItemVariation, opUserId).fields();
        return insert(dataSource, ITEM_VARIATION, fields).getRecordId();
    }

    @Override
    protected long createDescriptionDbOp(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long itemDescriptionId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        ItemDescriptionRecord itemDescriptionRecord = new ItemDescriptionRecord(ULong.valueOf(itemDescriptionId),
                ULong.valueOf(itemId),
                ULong.valueOf(itemVariationId),
                description,
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        itemDescriptionRecord.attach(configuration);
        itemDescriptionRecord.insert();
        return itemDescriptionId;
    }

    @Override
    protected long updateDescriptionDbOp(Long itemDescriptionId, String description, Long opUserId) {
        ItemDescriptionRecord itemDescriptionRecord = new ItemDescriptionRecord();
        itemDescriptionRecord.setItemDescriptionId(ULong.valueOf(itemDescriptionId));
        itemDescriptionRecord.setContent(description);
        itemDescriptionRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        itemDescriptionRecord.setLastModifyTime(LocalDateTime.now());
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        itemDescriptionRecord.attach(configuration);
        return itemDescriptionRecord.update();
    }

    @Override
    protected long createOrUpdateDescriptionDbOp(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID)
                .from(ITEM_DESCRIPTION)
                .where(ITEM_DESCRIPTION.ITEM_ID.eq(ULong.valueOf(itemId)))
                .and(ITEM_DESCRIPTION.ITEM_VARIATION_ID.eq(ULong.valueOf(itemVariationId)));
        CreateItemDescription createItemDescription = new CreateItemDescription.Builder()
                .setId(keyGenerator.next())
                .setItemId(itemId)
                .setItemVariationId(itemVariationId)
                .setContent(description)
                .build();
        Field[] fields = itemDescriptionRecordForInsert(createItemDescription, opUserId).fields();

        Consumer<Long> existsRecordUpdateAction = itemDescriptionRecordId -> {
            updateDescriptionDbOp(itemDescriptionRecordId, description, opUserId);
        };
        return insertOrUpdate(InsertMode.INSERT_AFTER_SELECT_CHECK, dataSource, ITEM_DESCRIPTION, fields, existsRecordSelector, existsRecordUpdateAction);
    }

    private ItemRecord itemRecordForInsert(CreateItem createItem, long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new ItemRecord(ULong.valueOf(createItem.getId()),
                ULong.valueOf(createItem.getStoreId()),
                UByte.valueOf(createItem.getItemType().getCode()),
                UByte.valueOf(createItem.getItemState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<ItemRecord> itemRecordsForInsert(List<CreateItem> createItems, Long opUserId) {
        List<ItemRecord> list = new ArrayList<>(createItems.size());
        for (CreateItem createItem : createItems) {
            list.add(itemRecordForInsert(createItem, opUserId));
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
