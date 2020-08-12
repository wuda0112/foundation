package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.*;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateAfterCheckMode;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.SingleInsertResult;
import com.wuda.foundation.lang.datatype.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PropertyManagerTest extends TestBase {

    LongIdentifier owner = new LongIdentifier(1024L, BuiltinIdentifierType.MOCK);

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
        long propertyKeyId_1 = propertyManager.createPropertyKey(createPropertyKey_1, InsertMode.DIRECT, opUserId).getRecordId();
        long propertyKeyId_2 = propertyManager.createPropertyKey(createPropertyKey_2, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId).getRecordId();
        Assert.assertNotEquals(propertyKeyId_1, propertyKeyId_2);
        long propertyKeyId_3 = propertyManager.createPropertyKey(createPropertyKey_3, InsertMode.INSERT_AFTER_SELECT_CHECK, opUserId).getRecordId();
        Assert.assertEquals(propertyKeyId_2, propertyKeyId_3);
        SingleInsertResult singleInsertResult = propertyManager.createPropertyValue(createPropertyValue, CreateAfterCheckMode.INSERT_AFTER_SELECT_CHECK, opUserId);
    }

    @Test
    public void testCreatePropertyKeyDefinition() {
        PropertyManager propertyManager = getPropertyManager();
        long propertyKeyId = keyGenerator.next();
        String key = "duplicate-key";
        CreatePropertyKey createPropertyKey = new CreatePropertyKey.Builder()
                .setId(propertyKeyId)
                .setOwner(owner)
                .setType(BuiltinPropertyKeyType.ZERO)
                .setUse(BuiltinPropertyKeyUse.ZERO)
                .setKey(key)
                .build();
        CreatePropertyKeyDefinition createPropertyKeyDefinition = new CreatePropertyKeyDefinition.Builder()
                .setId(keyGenerator.next())
                .setPropertyKeyId(propertyKeyId)
                .setDataType(MySQLDataType.VARCHAR)
                .build();
        CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition = new CreatePropertyKeyWithDefinition.Builder()
                .setCreatePropertyKey(createPropertyKey)
                .setCreatePropertyDefinition(createPropertyKeyDefinition)
                .build();
        CreatePropertyValue createPropertyValue = new CreatePropertyValue.Builder()
                .setId(keyGenerator.next())
                .setPropertyKeyId(propertyKeyId)
                .setValue("value-" + keyGenerator.next())
                .build();
        try {
            propertyManager.createPropertyKeyWithDefinition(createPropertyKeyWithDefinition, opUserId);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        propertyManager.createPropertyValue(createPropertyValue, CreateAfterCheckMode.INSERT_AFTER_SELECT_CHECK, opUserId);

        try {
            propertyManager.createPropertyKeyDefinition(createPropertyKeyDefinition, opUserId);
        } catch (AlreadyExistsException e) {
            System.out.println("测试创建重复的key definition");
        }

        CreatePropertyKey duplicatePropertyKey = new CreatePropertyKey.Builder()
                .setId(propertyKeyId)
                .setOwner(owner)
                .setType(BuiltinPropertyKeyType.ZERO)
                .setUse(BuiltinPropertyKeyUse.ZERO)
                .setKey(key)
                .build();
        CreatePropertyKeyWithDefinition duplicateKeyWithDefinition = new CreatePropertyKeyWithDefinition.Builder()
                .setCreatePropertyKey(duplicatePropertyKey)
                .setCreatePropertyDefinition(createPropertyKeyDefinition)
                .build();
        try {
            propertyManager.createPropertyKeyWithDefinition(duplicateKeyWithDefinition, opUserId);
        } catch (AlreadyExistsException e) {
            System.out.println("测试创建重复的key");
        }
    }

    @Test
    public void testGetProperties() {
        PropertyManager propertyManager = getPropertyManager();
        MyPropertyTemplates myPropertyTemplates = new MyPropertyTemplates();
        List<DescribeProperty> properties = propertyManager.getProperties(owner);
        properties = PropertyUtils.padding(owner, properties, myPropertyTemplates.templates());
        System.out.println(properties);
    }

    private PropertyManager getPropertyManager() {
        MySQLDataType.VARCHAR.register();
        BuiltinPropertyKeyType.ZERO.register();
        BuiltinPropertyKeyUse.ZERO.register();
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

}
