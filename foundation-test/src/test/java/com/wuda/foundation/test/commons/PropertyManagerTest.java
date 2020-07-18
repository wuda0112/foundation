package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.junit.Test;

public class PropertyManagerTest extends TestBase {

    @Test
    public void testCreateProperty() {
        PropertyManager propertyManager = getPropertyManager();
        Identifier<Long> owner = new LongIdentifier(1024L, BuiltinIdentifierType.MOCK);
        long propertyKeyId = propertyManager.createPropertyKey(owner, "test_key", BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO, keyGenerator, opUserId);

        long propertyValueId = propertyManager.createPropertyValue(propertyKeyId, "test_value", keyGenerator, opUserId);

    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

}
