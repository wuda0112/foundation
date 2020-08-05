package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.CreatePropertyKey;
import com.wuda.foundation.commons.property.CreatePropertyValue;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.junit.Assert;
import org.junit.Test;

public class PropertyManagerTest extends TestBase {

    Identifier<Long> owner = new LongIdentifier(1024L, BuiltinIdentifierType.MOCK);

    @Test
    public void testCreateProperty() {
        PropertyManager propertyManager = getPropertyManager();
        long keySuffix = keyGenerator.next();
        CreatePropertyKey createPropertyKey_1 = new CreatePropertyKey.Builder()
                .setId(keyGenerator.next())
                .setOwner(owner)
                .setType(BuiltinPropertyKeyType.ZERO)
                .setUse(BuiltinPropertyKeyUse.ZERO)
                .setKey("key-" + keyGenerator.next())
                .build();
        CreatePropertyKey createPropertyKey_2 = new CreatePropertyKey.Builder()
                .setId(keyGenerator.next())
                .setOwner(owner)
                .setType(BuiltinPropertyKeyType.ZERO)
                .setUse(BuiltinPropertyKeyUse.ZERO)
                .setKey("key-" + keySuffix)
                .build();
        CreatePropertyKey createPropertyKey_3 = new CreatePropertyKey.Builder()
                .setId(keyGenerator.next())
                .setOwner(owner)
                .setType(BuiltinPropertyKeyType.ZERO)
                .setUse(BuiltinPropertyKeyUse.ZERO)
                .setKey("key-" + keySuffix)
                .build();
        CreatePropertyValue createPropertyValue = new CreatePropertyValue.Builder()
                .setId(keyGenerator.next())
                .setPropertyKeyId(createPropertyKey_1.getId())
                .setValue("value-" + keyGenerator.next())
                .build();
        long propertyKeyId_1 = propertyManager.createPropertyKey(createPropertyKey_1, InsertMode.DIRECT, opUserId);
        long propertyKeyId_2 = propertyManager.createPropertyKey(createPropertyKey_2, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);
        Assert.assertNotEquals(propertyKeyId_1, propertyKeyId_2);
        long propertyKeyId_3 = propertyManager.createPropertyKey(createPropertyKey_3, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);
        Assert.assertEquals(propertyKeyId_2, propertyKeyId_3);
        long propertyValueId = propertyManager.createPropertyValue(createPropertyValue, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId);
    }

    @Test
    public void testGetProperties() {
        BuiltinPropertyKeyType.ZERO.register();
        BuiltinPropertyKeyUse.ZERO.register();
        PropertyManager propertyManager = getPropertyManager();
        propertyManager.getProperties(owner);
    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

}
