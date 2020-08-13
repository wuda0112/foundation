package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateAfterCheckMode;
import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.SingleInsertResult;
import com.wuda.foundation.lang.datatype.DataValueIllegalException;
import com.wuda.foundation.lang.datatype.ValidateResult;
import com.wuda.foundation.lang.identify.Identifier;

import java.util.List;
import java.util.Objects;

public abstract class AbstractPropertyManager implements PropertyManager {
    @Override
    public DescribePropertyKey getPropertyKey(Identifier<Long> owner, String key) {
        ExtObjects.requireNonNull(owner, key);
        return getPropertyKeyDbOp(owner, key);
    }

    /**
     * 作为{@link #getPropertyKey}方法的一部分,参数的校验已经在{@link #getPropertyKey}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param owner 该属性的拥有者
     * @param key   property的key
     * @return 如果不存在则返回<code>null</code>
     */
    protected abstract DescribePropertyKey getPropertyKeyDbOp(Identifier<Long> owner, String key);

    @Override
    public DescribePropertyKey getPropertyKey(Long id) {
        return getPropertyKeyDbOp(id);
    }

    /**
     * 作为{@link #getPropertyKey(Long)}方法的一部分,参数的校验已经在{@link #getPropertyKey(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param id property key id
     * @return 如果不存在则返回<code>null</code>
     */
    protected abstract DescribePropertyKey getPropertyKeyDbOp(Long id);

    @Override
    public SingleInsertResult createPropertyKey(CreatePropertyKey createPropertyKey, InsertMode insertMode, Long opUserId) {
        return createPropertyKeyDbOp(createPropertyKey, insertMode, opUserId);
    }

    @Override
    public SingleInsertResult createPropertyKeyWithDefinition(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException {
        return createPropertyKeyWithDefinitionDbOp(createPropertyKeyWithDefinition, opUserId);
    }

    protected abstract SingleInsertResult createPropertyKeyWithDefinitionDbOp(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException;

    @Override
    public void directBatchInsertPropertyKey(List<CreatePropertyKey> createPropertyKeys, Long opUserId) {
        directBatchInsertPropertyKeyDbOp(createPropertyKeys, opUserId);
    }

    protected abstract void directBatchInsertPropertyKeyDbOp(List<CreatePropertyKey> createPropertyKeys, Long opUserId);

    protected abstract SingleInsertResult createPropertyKeyDbOp(CreatePropertyKey createPropertyKey, InsertMode insertMode, Long opUserId);

    @Override
    public SingleInsertResult createPropertyValue(CreatePropertyValue createPropertyValue, CreateAfterCheckMode createAfterCheckMode, Long opUserId) {
        validatePropertyValue(createPropertyValue);
        return createPropertyValueDbOp(createPropertyValue, createAfterCheckMode, opUserId);
    }

    private void validatePropertyValue(CreatePropertyValue createPropertyValue) {
        DescribePropertyKeyDefinition describePropertyKeyDefinition = getDefinitionByPropertyKeyDbOp(createPropertyValue.getPropertyKeyId());
        DataType dataType = null;
        if (describePropertyKeyDefinition != null) {
            dataType = describePropertyKeyDefinition.getDataType();
        }
        if (dataType != null) {
            DataTypeHandler dataTypeHandler = dataType.getHandler();
            ValidateResult validateResult = dataTypeHandler.validate(describePropertyKeyDefinition.toDataDefinition(), createPropertyValue.getValue());
            if (!validateResult.isSuccess()) {
                throw new DataValueIllegalException(validateResult.getMessage());
            }
        }
    }

    @Override
    public long createOrUpdatePropertyValue(CreatePropertyValue createPropertyValue, CreateAfterCheckMode createAfterCheckMode, Long opUserId) {
        validatePropertyValue(createPropertyValue);
        return createOrUpdatePropertyValueDbOp(createPropertyValue, createAfterCheckMode, opUserId);
    }

    protected abstract long createOrUpdatePropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateAfterCheckMode createAfterCheckMode, Long opUserId);

    protected abstract SingleInsertResult createPropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateAfterCheckMode createAfterCheckMode, Long opUserId);

    @Override
    public void directBatchInsertPropertyValue(List<CreatePropertyValue> createPropertyValues, Long opUserId) {
        directBatchInsertPropertyValueDbOp(createPropertyValues, opUserId);
    }

    protected abstract void directBatchInsertPropertyValueDbOp(List<CreatePropertyValue> createPropertyValues, Long opUserId);

    @Override
    public void updatePropertyValue(UpdatePropertyValue updatePropertyValue, Long opUserId) {
        updatePropertyValueDbOp(updatePropertyValue, opUserId);
    }

    protected abstract void updatePropertyValueDbOp(UpdatePropertyValue updatePropertyValue, Long opUserId);

    @Override
    public void directBatchUpdatePropertyValue(List<UpdatePropertyValue> updatePropertyValues, Long opUserId) {
        directBatchUpdatePropertyValueDbOp(updatePropertyValues, opUserId);
    }

    protected abstract void directBatchUpdatePropertyValueDbOp(List<UpdatePropertyValue> updatePropertyValues, Long opUserId);

    @Override
    public List<DescribePropertyValue> getValueByPropertyKey(Long propertyKeyId) {
        return getValueByPropertyKeyDbOp(Objects.requireNonNull(propertyKeyId));
    }

    protected abstract List<DescribePropertyValue> getValueByPropertyKeyDbOp(Long propertyKeyId);

    @Override
    public DescribePropertyKeyDefinition getDefinitionByPropertyKey(Long propertyKeyId) {
        return getDefinitionByPropertyKeyDbOp(Objects.requireNonNull(propertyKeyId));
    }

    protected abstract DescribePropertyKeyDefinition getDefinitionByPropertyKeyDbOp(Long propertyKeyId);

    @Override
    public DescribeProperty getProperty(Identifier<Long> owner, String key) {
        ExtObjects.requireNonNull(owner, key);
        return getPropertyDbOp(owner, key);
    }

    protected abstract DescribeProperty getPropertyDbOp(Identifier<Long> owner, String key);

    @Override
    public List<DescribeProperty> getProperties(Identifier<Long> owner) {
        Objects.requireNonNull(owner);
        return getPropertiesDbOp(owner);
    }

    protected abstract List<DescribeProperty> getPropertiesDbOp(Identifier<Long> owner);

    @Override
    public SingleInsertResult createPropertyKeyDefinition(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException {
        ExtObjects.requireNonNull(definition, opUserId);
        return createPropertyKeyDefinitionDbOp(definition, opUserId);
    }

    protected abstract SingleInsertResult createPropertyKeyDefinitionDbOp(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException;

}
