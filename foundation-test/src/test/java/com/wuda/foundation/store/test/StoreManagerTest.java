package com.wuda.foundation.store.test;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.store.BuiltinStoreState;
import com.wuda.foundation.store.BuiltinStoreType;
import com.wuda.foundation.store.StoreManager;
import com.wuda.foundation.store.impl.StoreManagerImpl;
import org.junit.Test;

public class StoreManagerTest extends TestBase {

    @Test
    public void testAddStore() {
        StoreManager storeManager = getStoreManager();
        storeManager.addStore(BuiltinStoreType.ZERO, BuiltinStoreState.ZERO, 0L, "卖卖小店", 0L);
    }

    private StoreManager getStoreManager() {
        StoreManagerImpl storeManager = new StoreManagerImpl();
        storeManager.setDataSource(getDataSource());
        storeManager.setKeyGenerator(keyGeneratorSnowflake);
        return storeManager;
    }
}
