package com.wuda.foundation.test.commons;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.commons.impl.PropertyManagerImpl;
import com.wuda.foundation.commons.property.CreatePropertyKey;
import com.wuda.foundation.commons.property.CreatePropertyKeyDefinition;
import com.wuda.foundation.commons.property.CreatePropertyKeyWithDefinition;
import com.wuda.foundation.commons.property.CreatePropertyValue;
import com.wuda.foundation.commons.property.DescribeProperty;
import com.wuda.foundation.commons.property.DescribePropertyKey;
import com.wuda.foundation.commons.property.DescribePropertyKeyDefinition;
import com.wuda.foundation.commons.property.DescribePropertyValue;
import com.wuda.foundation.commons.property.PropertyManager;
import com.wuda.foundation.commons.property.PropertyUtils;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.datatype.DataDefinition;
import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class PropertyManagerTest extends TestBase {

    LongIdentifier owner = new LongIdentifier(1024L, BuiltinIdentifierType.TABLE_ITEM);

    @Test
    public void test() {

        long propertyKeyId = keyGenerator.next();
        String key = getKey(propertyKeyId);
        long key_1 = createPropertyKey(owner, propertyKeyId, key);
        long key_2 = createPropertyKey(owner, propertyKeyId, key);
        // 测试创建相同的key
        Assert.assertEquals(key_1, key_2);

        try {
            createDefinition(key_1, MySQLDataType.INT);
        } catch (AlreadyExistsException e) {
            System.out.println("这里不应该发生");
            e.printStackTrace();
        }

        try {
            // 为同一个key创建多个definition
            createDefinition(key_1, MySQLDataType.VARCHAR);
        } catch (AlreadyExistsException e) {
            System.out.println("为同一个key创建多个definition，应该报错.");
            e.printStackTrace();
        }

        try {
            // 创建错误的值,因为数据类型不满足
            createPropertyValue(key_1, "value-" + keyGenerator.next());
        } catch (Exception e) {
            System.out.println("property value数据类型不一致，应该报错.");
            e.printStackTrace();
        }
        try {
            // 创建错误的值,因为数据类型不满足
            createPropertyValue(key_1, keyGenerator.next() + "");
        } catch (Exception e) {
            System.out.println("property value转换成int后值发生变化，应该报错.");
            e.printStackTrace();
        }

        Random random = new Random();
        createPropertyValue(key_1, random.nextInt()+"");

        try {
            // 测试创建相同的key
            createPropertyKeyWithDefinition(keyGenerator.next(), key, MySQLDataType.INT);
        } catch (AlreadyExistsException e) {
            System.out.println("为同一个owner重复创建多个相同的key，应该报错.");
            e.printStackTrace();
        }

        try {
            long propertyKeyId_1 = keyGenerator.next();
            createPropertyKeyWithDefinition(propertyKeyId_1, getKey(propertyKeyId_1), MySQLDataType.VARCHAR);
        } catch (AlreadyExistsException e) {
            System.out.println("这里不应该发生");
            e.printStackTrace();
        }

        getPropertyValue(owner, key);

    }

    public long createPropertyKey(LongIdentifier owner, long id, String key) {
        PropertyManager propertyManager = getPropertyManager();
        CreatePropertyKey createPropertyKey = getCreatePropertyKey(owner, id, key);
        return propertyManager.createPropertyKey(createPropertyKey, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId).getRecordId();
    }

    public long createPropertyValue(long propertyKeyId, String value) {
        PropertyManager propertyManager = getPropertyManager();
        CreatePropertyValue createPropertyValue = new CreatePropertyValue.Builder()
                .setId(keyGenerator.next())
                .setPropertyKeyId(propertyKeyId)
                .setValue(value)
                .build();
        CreateResult createResult = propertyManager.createPropertyValue(createPropertyValue, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        long value_2 = propertyManager.createOrUpdatePropertyValue(createPropertyValue, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        Assert.assertEquals(createResult.getRecordId().longValue(), value_2);
        return value_2;
    }

    public long createPropertyKeyWithDefinition(long propertyKeyId, String key, DataType dataType) throws AlreadyExistsException {
        PropertyManager propertyManager = getPropertyManager();
        CreatePropertyKey createPropertyKey = getCreatePropertyKey(owner, propertyKeyId, key);
        CreatePropertyKeyDefinition createPropertyKeyDefinition = getCreatePropertyKeyDefinition(propertyKeyId, dataType);
        CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition = new CreatePropertyKeyWithDefinition.Builder()
                .setCreatePropertyKey(createPropertyKey)
                .setCreatePropertyDefinition(createPropertyKeyDefinition)
                .build();
        return propertyManager.createPropertyKeyWithDefinition(createPropertyKeyWithDefinition, opUserId).getRecordId();
    }

    private long createDefinition(long propertyKeyId, DataType dataType) throws AlreadyExistsException {
        PropertyManager propertyManager = getPropertyManager();
        CreatePropertyKeyDefinition createPropertyKeyDefinition = getCreatePropertyKeyDefinition(propertyKeyId, dataType);
        return propertyManager.createPropertyKeyDefinition(createPropertyKeyDefinition, opUserId).getRecordId();
    }

    @Test
    public void testGetProperties() {
        PropertyManager propertyManager = getPropertyManager();
        MyPropertyTemplates myPropertyTemplates = new MyPropertyTemplates();
        List<DescribeProperty> properties = propertyManager.getProperties(owner);
        properties = PropertyUtils.padding(owner, properties, myPropertyTemplates.templates());
        printProperties(properties);
        DescribeProperty nameProperty = PropertyUtils.getProperty(properties, MyPropertyTemplates.MyPropertyKeyNaming.NAME);
        Assert.assertNotNull(nameProperty);
    }

    private void printProperties(List<DescribeProperty> properties) {
        if (properties == null || properties.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (DescribeProperty describeProperty : properties) {
            builder.append("\n");
            DescribePropertyKey describePropertyKey = describeProperty.getPropertyKey();
            DescribePropertyKeyDefinition describePropertyKeyDefinition = describeProperty.getPropertyDefinition();
            List<DescribePropertyValue> describePropertyValues = describeProperty.getPropertyValues();
            Identifier<Long> owner = describePropertyKey.getOwner();
            builder.append("owner type = ").append(owner.getType().getCode()).append(",owner identifier =").append(owner.getValue());
            builder.append("\tkey = ").append(describePropertyKey.getKey());
            if (describePropertyKeyDefinition != null) {
                builder.append(",data type = ").append(describePropertyKeyDefinition.getDataType());
            }
            if (describePropertyValues != null && !describePropertyValues.isEmpty()) {
                builder.append(",values = ");
                for (DescribePropertyValue describePropertyValue : describePropertyValues) {
                    builder.append(describePropertyValue.getValue()).append("  ");
                }
            }
        }
        System.out.println(builder.toString());
    }

    private PropertyManager getPropertyManager() {
        PropertyManagerImpl propertyManager = new PropertyManagerImpl();
        propertyManager.setDataSource(getDataSource());
        return propertyManager;
    }

    private CreatePropertyKey getCreatePropertyKey(LongIdentifier owner, long id, String key) {
        return new CreatePropertyKey.Builder()
                .setId(id)
                .setOwner(owner)
                .setType(byte0)
                .setUse(byte0)
                .setKey(key)
                .build();
    }

    private CreatePropertyKeyDefinition getCreatePropertyKeyDefinition(long propertyKeyId, DataType dataType) {
        return new CreatePropertyKeyDefinition.Builder()
                .setId(keyGenerator.next())
                .setPropertyKeyId(propertyKeyId)
                .setDataType(dataType)
                .build();
    }

    private String getKey(long propertyKeyId) {
        return "key-" + propertyKeyId;
    }

    private void getPropertyValue(Identifier<Long> owner, String key) {
        PropertyManager propertyManager = getPropertyManager();
        DescribePropertyKey describePropertyKey = propertyManager.getPropertyKey(owner, key);
        if (describePropertyKey != null) {
            DescribePropertyKeyDefinition definition = propertyManager.getDefinitionByPropertyKey(describePropertyKey.getId());
            DataDefinition dataDefinition = definition.toDataDefinition();
            DataTypeHandler dataTypeHandler = dataDefinition.getDataType().getHandler();
            List<DescribePropertyValue> values = propertyManager.getValueByPropertyKey(describePropertyKey.getId());
            if (values != null && !values.isEmpty()) {
                for (DescribePropertyValue value : values) {
                    System.out.print("key = " + key + ",data type = " + definition.getDataType() + "\t");
                    System.out.println(",value = " + dataTypeHandler.parseValue(value.getValue()));
                }
            }
        }
    }

}
