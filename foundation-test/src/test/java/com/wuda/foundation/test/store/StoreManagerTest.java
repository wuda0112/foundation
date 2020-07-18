package com.wuda.foundation.test.store;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.store.*;
import com.wuda.foundation.store.impl.StoreManagerImpl;
import org.junit.Test;

public class StoreManagerTest extends TestBase {

    @Test
    public void testAddStore() {
        StoreManager storeManager = getStoreManager();
        CreateStore createStore = new CreateStore.Builder()
                .setStoreType(BuiltinStoreType.ZERO)
                .setStoreState(BuiltinStoreState.ZERO)
                .build();
        long storeId = storeManager.createStore(createStore, opUserId, keyGenerator, opUserId);

        CreateStoreGeneral createStoreGeneral = new CreateStoreGeneral.Builder()
                .setStoreId(storeId)
                .setStoreName("卖卖小店")
                .build();
        long storeGeneralId = storeManager.createStoreGeneral(createStoreGeneral, keyGenerator, opUserId);

        UpdateStoreGeneral updateStoreGeneral = new UpdateStoreGeneral.Builder()
                .setStoreName("卖卖百年老店")
                .build();
        storeManager.updateStoreGeneralById(storeGeneralId, updateStoreGeneral, opUserId);
    }

    private StoreManager getStoreManager() {
        StoreManagerImpl storeManager = new StoreManagerImpl();
        storeManager.setDataSource(getDataSource());
        return storeManager;
    }
}
