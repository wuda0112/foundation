package com.wuda.foundation.item.impl;

import com.wuda.foundation.item.AbstractItemManager;
import com.wuda.foundation.item.CreateItem;
import com.wuda.foundation.item.CreateItemGeneral;
import com.wuda.foundation.item.CreateItemVariation;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemDescriptionRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemGeneralRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemVariationRecord;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import org.jooq.Configuration;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import static com.wuda.foundation.item.impl.jooq.generation.tables.ItemDescription.ITEM_DESCRIPTION;
import static org.jooq.impl.DSL.param;
import static org.jooq.impl.DSL.select;

public class ItemManagerImpl extends AbstractItemManager implements JooqCommonDbOp {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected long createItemDbOp(Long storeId, CreateItem createItem, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long itemId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        ItemRecord itemRecord = new ItemRecord(ULong.valueOf(itemId),
                ULong.valueOf(storeId),
                UByte.valueOf(createItem.getItemType().getCode()),
                UByte.valueOf(createItem.getItemState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        itemRecord.attach(configuration);
        itemRecord.insert();

        return itemId;
    }

    @Override
    protected long createItemGeneralDbOp(Long itemId, CreateItemGeneral createItemGeneral, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long itemGeneralId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        ItemGeneralRecord itemGeneralRecord = new ItemGeneralRecord(ULong.valueOf(itemGeneralId),
                ULong.valueOf(itemId),
                createItemGeneral.getName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        itemGeneralRecord.attach(configuration);
        itemGeneralRecord.insert();
        return itemGeneralId;
    }

    @Override
    protected long createItemVariationDbOp(Long itemId, CreateItemVariation createItemVariation, KeyGenerator<Long> keyGenerator, Long opUserId) {
        long itemVariationId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        ItemVariationRecord itemVariationRecord = new ItemVariationRecord(ULong.valueOf(itemVariationId),
                ULong.valueOf(itemId),
                createItemVariation.getName(),
                UByte.valueOf(createItemVariation.getState().getCode()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        itemVariationRecord.attach(configuration);
        itemVariationRecord.insert();
        return itemVariationId;
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
        Long itemDescriptionId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<ItemDescriptionRecord> forUpdateRecordSelector = DSL.using(configuration)
                .selectFrom(ITEM_DESCRIPTION)
                .where(ITEM_DESCRIPTION.ITEM_ID.eq(ULong.valueOf(itemId)))
                .and(ITEM_DESCRIPTION.ITEM_VARIATION_ID.eq(ULong.valueOf(itemVariationId)));
        SelectField[] selectFields = new SelectField[]{param(ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID.getName(), ULong.valueOf(itemDescriptionId)),
                param(ITEM_DESCRIPTION.ITEM_ID.getName(), ULong.valueOf(itemId)),
                param(ITEM_DESCRIPTION.ITEM_VARIATION_ID.getName(), ULong.valueOf(itemVariationId)),
                param(ITEM_DESCRIPTION.CONTENT.getName(), description),
                param(ITEM_DESCRIPTION.CREATE_TIME.getName(), now),
                param(ITEM_DESCRIPTION.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(ITEM_DESCRIPTION.LAST_MODIFY_TIME.getName(), now),
                param(ITEM_DESCRIPTION.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(ITEM_DESCRIPTION.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))};
        SelectSelectStep insertIntoSelectFields = select(selectFields);

        Consumer<ItemDescriptionRecord> existsRecordUpdateAction = itemDescriptionRecord -> {
            updateDescriptionDbOp(itemDescriptionRecord.getItemDescriptionId().longValue(), description, opUserId);
        };
        return insertOrUpdate(dataSource, ITEM_DESCRIPTION, forUpdateRecordSelector, insertIntoSelectFields, existsRecordUpdateAction, ITEM_DESCRIPTION.ITEM_DESCRIPTION_ID);
    }
}
