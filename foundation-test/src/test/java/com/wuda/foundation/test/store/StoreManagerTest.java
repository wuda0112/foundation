package com.wuda.foundation.test.store;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.store.*;
import com.wuda.foundation.store.impl.StoreManagerImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class StoreManagerTest extends TestBase {

    @Test
    public void testAddStore() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.ALL);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.ALL);
        }

        StoreManager storeManager = getStoreManager();

        CreateStore createStore2 = new CreateStore.Builder()
                .setId(keyGenerator.next())
                .setStoreType(BuiltinStoreType.ZERO)
                .setStoreState(BuiltinStoreState.ZERO)
                .build();

        CreateStore createStore3 = new CreateStore.Builder()
                .setId(keyGenerator.next())
                .setStoreType(BuiltinStoreType.ZERO)
                .setStoreState(BuiltinStoreState.ZERO)
                .build();
        storeManager.directBatchInsertStore(Arrays.asList(createStore2, createStore3), opUserId);


        CreateStore createStore = new CreateStore.Builder()
                .setId(keyGenerator.next())
                .setStoreType(BuiltinStoreType.ZERO)
                .setStoreState(BuiltinStoreState.ZERO)
                .build();
        long storeId = storeManager.createStore(createStore, opUserId, keyGenerator, opUserId);

        CreateStoreGeneral createStoreGeneral = new CreateStoreGeneral.Builder()
                .setId(keyGenerator.next())
                .setStoreId(storeId)
                .setStoreName("卖卖小店")
                .build();
        long storeGeneralId = storeManager.createStoreGeneral(createStoreGeneral, opUserId);

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
