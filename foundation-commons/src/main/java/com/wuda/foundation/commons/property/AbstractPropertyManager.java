package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.datatype.DataDefinition;
import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
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

    protected abstract DescribePropertyKey getPropertyKeyDbOp(Identifier<Long> owner, String key);

    @Override
    public DescribePropertyKey getPropertyKey(Long id) {
        return getPropertyKeyDbOp(id);
    }

    protected abstract DescribePropertyKey getPropertyKeyDbOp(Long id);

    @Override
    public CreateResult createPropertyKey(CreatePropertyKey createPropertyKey, CreateMode createMode, Long opUserId) {
        return createPropertyKeyDbOp(createPropertyKey, createMode, opUserId);
    }

    @Override
    public CreateResult createPropertyKeyWithDefinition(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException {
        return createPropertyKeyWithDefinitionDbOp(createPropertyKeyWithDefinition, opUserId);
    }

    protected abstract CreateResult createPropertyKeyWithDefinitionDbOp(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException;

    @Override
    public void directBatchInsertPropertyKey(List<CreatePropertyKey> createPropertyKeys, Long opUserId) {
        directBatchInsertPropertyKeyDbOp(createPropertyKeys, opUserId);
    }

    protected abstract void directBatchInsertPropertyKeyDbOp(List<CreatePropertyKey> createPropertyKeys, Long opUserId);

    protected abstract CreateResult createPropertyKeyDbOp(CreatePropertyKey createPropertyKey, CreateMode createMode, Long opUserId);

    @Override
    public CreateResult createPropertyValue(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId) {
        validatePropertyValue(createPropertyValue);
        return createPropertyValueDbOp(createPropertyValue, createMode, opUserId);
    }

    private void validatePropertyValue(CreatePropertyValue createPropertyValue) {
        DescribePropertyKeyDefinition describePropertyKeyDefinition = getDefinitionByPropertyKeyDbOp(createPropertyValue.getPropertyKeyId());
        DataDefinition dataDefinition;
        if (describePropertyKeyDefinition != null) {
            dataDefinition = describePropertyKeyDefinition.toDataDefinition();
            validatePropertyValue(createPropertyValue, dataDefinition);
        }
    }

    private void validatePropertyValue(CreatePropertyValue createPropertyValue, DataDefinition dataDefinition) {
        DataType dataType = dataDefinition.getDataType();
        if (dataType != null) {
            DataTypeHandler dataTypeHandler = dataType.getHandler();
            ValidateResult validateResult = dataTypeHandler.validate(dataDefinition, createPropertyValue.getValue());
            if (!validateResult.isSuccess()) {
                throw new DataValueIllegalException(validateResult.getMessage());
            }
        }
    }

    @Override
    public long createOrUpdatePropertyValue(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId) {
        validatePropertyValue(createPropertyValue);
        return createOrUpdatePropertyValueDbOp(createPropertyValue, createMode, opUserId);
    }

    protected abstract long createOrUpdatePropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId);

    protected abstract CreateResult createPropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId);

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
    public CreateResult createPropertyKeyDefinition(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException {
        ExtObjects.requireNonNull(definition, opUserId);
        return createPropertyKeyDefinitionDbOp(definition, opUserId);
    }

    protected abstract CreateResult createPropertyKeyDefinitionDbOp(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException;

}
