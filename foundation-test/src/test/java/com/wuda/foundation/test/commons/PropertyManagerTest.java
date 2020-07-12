package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.lang.BuiltinIdentifierType;
import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.lang.IdentifierType;
import org.junit.Test;

public class PropertyManagerTest extends TestBase {

    @Test
    public void testCreateProperty() {
        PropertyManager propertyManager = getPropertyManager();
        Identifier<Long> owner = new Identifier<Long>() {
            @Override
            public Long getValue() {
                return 1024L;
            }

            @Override
            public IdentifierType getType() {
                return BuiltinIdentifierType.EMAIL;
            }
        };
        long propertyKeyId = propertyManager.createPropertyKey(owner, "test_key", BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO, keyGenerator, opUserId);

        long propertyValueId = propertyManager.createPropertyValue(propertyKeyId,"test_value",keyGenerator,opUserId);

    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

}
