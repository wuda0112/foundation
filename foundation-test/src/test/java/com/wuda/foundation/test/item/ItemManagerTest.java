package com.wuda.foundation.test.item;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.item.*;
import com.wuda.foundation.item.impl.ItemManagerImpl;
import com.wuda.foundation.lang.Constant;
import org.junit.Test;

public class ItemManagerTest extends TestBase {

    @Test
    public void testCreateItem() {
        ItemManager itemManager = getItemManager();
        CreateItem createItem = new CreateItem.Builder()
                .setId(keyGenerator.next())
                .setStoreId(0L)
                .setItemType(BuiltinItemType.ZERO)
                .setItemState(BuiltinItemState.ZERO)
                .setCategoryId(0L)
                .build();
        long itemId = itemManager.createItem(createItem, opUserId);

        CreateItemGeneral createItemGeneral = new CreateItemGeneral.Builder()
                .setId(keyGenerator.next())
                .setItemId(itemId)
                .setName("item-" + itemId)
                .build();
        itemManager.createItemGeneral(createItemGeneral, opUserId);

        CreateItemVariation createItemVariation = new CreateItemVariation.Builder()
                .setId(keyGenerator.next())
                .setItemId(itemId)
                .setState(BuiltinItemState.ZERO)
                .setName("item-0-variation")
                .build();
        long itemVariationId = itemManager.createItemVariation(createItemVariation, opUserId);

        itemManager.createDescription(itemId, itemVariationId, "description", keyGenerator, opUserId);
    }

    @Test
    public void testCreateOrUpdateDescription() {
        ItemManager itemManager = getItemManager();
        itemManager.createOrUpdateDescription(1024L, Constant.NOT_EXISTS_ID, "update-" + keyGenerator.next(), keyGenerator, opUserId);
    }

    private ItemManager getItemManager() {
        ItemManagerImpl storeManager = new ItemManagerImpl();
        storeManager.setDataSource(getDataSource());
        return storeManager;
    }
}
