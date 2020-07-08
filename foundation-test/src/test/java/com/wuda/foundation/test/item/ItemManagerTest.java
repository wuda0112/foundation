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
                .setItemType(BuiltinItemType.ZERO)
                .setItemState(BuiltinItemState.ZERO)
                .setCategoryId(0L)
                .build();
        long itemId = itemManager.createItem(0L, createItem, keyGenerator, opUserId);

        CreateItemGeneral createItemGeneral = new CreateItemGeneral.Builder()
                .setName("item-0")
                .build();
        itemManager.createItemGeneral(itemId, createItemGeneral, keyGenerator, opUserId);

        CreateItemVariation createItemVariation = new CreateItemVariation.Builder()
                .setState(BuiltinItemState.ZERO)
                .setName("item-0-variation")
                .build();
        long itemVariationId = itemManager.createItemVariation(itemId, createItemVariation, keyGenerator, opUserId);

        itemManager.createDescription(itemId, itemVariationId, "description", keyGenerator, opUserId);
    }

    @Test
    public void testCreateOrUpdateDescription(){
        ItemManager itemManager = getItemManager();
        itemManager.createOrUpdateDescription(1024L, Constant.NOT_EXISTS_ID,"update-4",keyGenerator,opUserId);
    }

    private ItemManager getItemManager() {
        ItemManagerImpl storeManager = new ItemManagerImpl();
        storeManager.setDataSource(getDataSource());
        return storeManager;
    }
}
