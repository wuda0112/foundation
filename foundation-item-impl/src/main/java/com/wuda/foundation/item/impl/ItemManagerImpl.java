package com.wuda.foundation.item.impl;

import com.wuda.foundation.item.AbstractItemManager;
import com.wuda.foundation.item.CreateItem;
import com.wuda.foundation.item.CreateItemGeneral;
import com.wuda.foundation.item.CreateItemVariation;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemDescriptionRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemGeneralRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemRecord;
import com.wuda.foundation.item.impl.jooq.generation.tables.records.ItemVariationRecord;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import org.jooq.Configuration;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class ItemManagerImpl extends AbstractItemManager {

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
    protected long updateDescriptionDbOp(Long itemId, Long itemVariationId, String description, Long opUserId) {
        return 0;
    }

    @Override
    protected long createOrUpdateDescriptionDbOp(Long itemId, Long itemVariationId, String description, KeyGenerator<Long> keyGenerator, Long opUserId) {
        return 0;
    }
}
