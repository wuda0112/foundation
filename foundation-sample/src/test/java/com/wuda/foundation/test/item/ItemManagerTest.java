package com.wuda.foundation.test.item;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.item.BuiltinItemState;
import com.wuda.foundation.item.BuiltinItemType;
import com.wuda.foundation.item.CreateItem;
import com.wuda.foundation.item.CreateItemDescription;
import com.wuda.foundation.item.CreateItemGeneral;
import com.wuda.foundation.item.CreateItemVariation;
import com.wuda.foundation.item.ItemManager;
import com.wuda.foundation.item.impl.ItemManagerImpl;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.InsertMode;
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
        itemManager.createOrUpdateItemGeneral(createItemGeneral, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);

        CreateItemVariation createItemVariation = new CreateItemVariation.Builder()
                .setId(keyGenerator.next())
                .setItemId(itemId)
                .setState(BuiltinItemState.ZERO)
                .setName("item-0-variation")
                .build();
        long itemVariationId = itemManager.createItemVariation(createItemVariation, opUserId);

        CreateItemDescription createItemDescription = new CreateItemDescription.Builder()
                .setId(keyGenerator.next())
                .setItemId(itemId)
                .setItemVariationId(itemVariationId)
                .setContent("description")
                .build();

        itemManager.createOrUpdateItemDescription(createItemDescription, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);
    }

    @Test
    public void testCreateOrUpdateDescription() {
        ItemManager itemManager = getItemManager();
        CreateItemDescription createItemDescription = new CreateItemDescription.Builder()
                .setId(keyGenerator.next())
                .setItemId(1024L)
                .setItemVariationId(Constant.NOT_EXISTS_ID)
                .setContent("description")
                .build();

        itemManager.createOrUpdateItemDescription(createItemDescription, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);
    }

    private ItemManager getItemManager() {
        ItemManagerImpl storeManager = new ItemManagerImpl();
        storeManager.setDataSource(getDataSource());
        return storeManager;
    }
}
