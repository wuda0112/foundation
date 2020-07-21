package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.junit.Assert;
import org.junit.Test;

public class PropertyManagerTest extends TestBase {

    @Test
    public void testCreateProperty() {
        PropertyManager propertyManager = getPropertyManager();
        Identifier<Long> owner = new LongIdentifier(1024L, BuiltinIdentifierType.MOCK);
        long propertyKeyId_1 = propertyManager.createPropertyKey(owner, "test_key_5", BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO, InsertMode.DIRECT, keyGenerator, opUserId);
        long propertyKeyId_2 = propertyManager.createPropertyKey(owner, "test_key_5", BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO, InsertMode.INSERT_AFTER_SELECT_CHECK, keyGenerator, opUserId);
        Assert.assertEquals(propertyKeyId_1, propertyKeyId_2);
        long propertyKeyId_3 = propertyManager.createPropertyKey(owner, "test_key_5", BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO, InsertMode.INSERT_WHERE_NOT_EXISTS, keyGenerator, opUserId);
        Assert.assertEquals(propertyKeyId_2, propertyKeyId_3);
        long propertyValueId = propertyManager.createPropertyValue(propertyKeyId_1, "test_value", keyGenerator, opUserId);

    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

}
