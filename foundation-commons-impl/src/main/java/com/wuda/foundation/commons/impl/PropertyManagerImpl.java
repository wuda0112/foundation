package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PropertyKeyDefinitionRecord;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PropertyKeyRecord;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PropertyValueRecord;
import com.wuda.foundation.commons.property.*;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeRegistry;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.PropertyKey.PROPERTY_KEY;
import static com.wuda.foundation.commons.impl.jooq.generation.tables.PropertyKeyDefinition.PROPERTY_KEY_DEFINITION;
import static com.wuda.foundation.commons.impl.jooq.generation.tables.PropertyValue.PROPERTY_VALUE;

public class PropertyManagerImpl extends AbstractPropertyManager implements JooqCommonDbOp {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected DescribePropertyKey getPropertyKeyDbOp(Identifier<Long> owner, String key) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        PropertyKeyRecord propertyKeyRecord = dslContext.selectFrom(PROPERTY_KEY)
                .where(PROPERTY_KEY.OWNER_TYPE.eq(UByte.valueOf(owner.getType().getCode())))
                .and(PROPERTY_KEY.OWNER_IDENTIFIER.eq(ULong.valueOf(owner.getValue())))
                .and(PROPERTY_KEY.KEY.eq(key))
                .fetchOne();
        return from(propertyKeyRecord);
    }

    @Override
    protected CreateResult createPropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId) {
        long propertyKeyId = createPropertyValue.getPropertyKeyId();
        PropertyValueRecord propertyValueRecord = propertyValueRecordForInsert(createPropertyValue, opUserId);
        DescribePropertyKeyDefinition describePropertyKeyDefinition = getDefinitionByPropertyKeyDbOp(propertyKeyId);
        DataType dataType = null;
        boolean isMultiValued = false;
        if (describePropertyKeyDefinition != null) {
            dataType = describePropertyKeyDefinition.getDataType();
            isMultiValued = describePropertyKeyDefinition.isMultiValued();
        }
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector;
        if (dataType == null || isMultiValued) {
            existsRecordSelector = DSL.using(configuration)
                    .select(PROPERTY_VALUE.PROPERTY_VALUE_ID)
                    .from(PROPERTY_VALUE)
                    .where(PROPERTY_VALUE.PROPERTY_KEY_ID.eq(ULong.valueOf(propertyKeyId)))
                    .and(PROPERTY_VALUE.VALUE.eq(createPropertyValue.getValue()))
                    .and(PROPERTY_VALUE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        } else {
            existsRecordSelector = DSL.using(configuration)
                    .select(PROPERTY_VALUE.PROPERTY_VALUE_ID)
                    .from(PROPERTY_VALUE)
                    .where(PROPERTY_VALUE.PROPERTY_KEY_ID.eq(ULong.valueOf(propertyKeyId)))
                    .and(PROPERTY_VALUE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        }
        return insertDispatcher(dataSource, createMode, PROPERTY_VALUE, propertyValueRecord, existsRecordSelector);
    }

    @Override
    protected void directBatchInsertPropertyValueDbOp(List<CreatePropertyValue> createPropertyValues, Long opUserId) {
        List<PropertyValueRecord> records = newPropertyValueRecords(createPropertyValues, opUserId);
        batchInsert(dataSource, PROPERTY_VALUE, records);
    }

    private List<PropertyValueRecord> getPropertyValue(Long propertyKeyId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(PROPERTY_VALUE)
                .where(PROPERTY_VALUE.PROPERTY_KEY_ID.eq(ULong.valueOf(propertyKeyId)))
                .and(PROPERTY_VALUE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetch();
    }

    private PropertyValueRecord propertyValueExists(List<PropertyValueRecord> values, String value) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        for (PropertyValueRecord propertyValueRecord : values) {
            if (value.equals(propertyValueRecord.getValue())) {
                return propertyValueRecord;
            }
        }
        return null;
    }

    @Override
    public DescribePropertyKey getPropertyKeyDbOp(Long id) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        PropertyKeyRecord propertyKeyRecord = new PropertyKeyRecord();
        propertyKeyRecord.setPropertyKeyId(ULong.valueOf(id));
        propertyKeyRecord.attach(configuration);
        propertyKeyRecord.refresh();
        return from(propertyKeyRecord);
    }

    @Override
    protected CreateResult createPropertyKeyWithDefinitionDbOp(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException {
        CreatePropertyKey createPropertyKey = createPropertyKeyWithDefinition.getPropertyKey();
        CreatePropertyKeyDefinition definition = createPropertyKeyWithDefinition.getDefinition();
        CreateResult createResult = createPropertyKeyDbOp(createPropertyKey, CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        if (createResult.getExistsRecordId() != null) {
            throw new AlreadyExistsException("owner type = " + createPropertyKey.getOwner().getType().getCode()
                    + ",owner id = " + createPropertyKey.getOwner().getValue()
                    + ",已经拥有 property key = " + createPropertyKey.getKey());
        }
        try {
            createPropertyKeyDefinitionDbOp(definition, opUserId);
        } catch (AlreadyExistsException e) {
            // 新建的key之前肯定不会有definition
        }
        return createResult;
    }

    @Override
    protected void directBatchInsertPropertyKeyDbOp(List<CreatePropertyKey> createPropertyKeys, Long opUserId) {
        List<PropertyKeyRecord> records = newPropertyKeyRecords(createPropertyKeys, opUserId);
        batchInsert(dataSource, PROPERTY_KEY, records);
    }

    @Override
    protected CreateResult createPropertyKeyDbOp(CreatePropertyKey createPropertyKey, CreateMode createMode, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PROPERTY_KEY.PROPERTY_KEY_ID)
                .from(PROPERTY_KEY)
                .where(PROPERTY_KEY.OWNER_TYPE.eq(UByte.valueOf(createPropertyKey.getOwner().getType().getCode())))
                .and(PROPERTY_KEY.OWNER_IDENTIFIER.eq(ULong.valueOf(createPropertyKey.getOwner().getValue())))
                .and(PROPERTY_KEY.KEY.eq(createPropertyKey.getKey()))
                .and(PROPERTY_KEY.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        return insertDispatcher(dataSource, createMode, PROPERTY_KEY, newPropertyKeyRecord(createPropertyKey, opUserId), existsRecordSelector);
    }

    @Override
    protected long createOrUpdatePropertyValueDbOp(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId) {
        CreateResult createResult = createPropertyValueDbOp(createPropertyValue, createMode, opUserId);
        if (createResult.getExistsRecordId() != null) {
            PropertyValueRecord propertyValueRecord = getPropertyValueById(createResult.getExistsRecordId());
            if (propertyValueRecord.getValue() != null && !propertyValueRecord.getValue().equals(createPropertyValue.getValue())) {
                UpdatePropertyValue updatePropertyValue = UpdatePropertyValue.from(createResult.getExistsRecordId(), createPropertyValue);
                updatePropertyValueDbOp(updatePropertyValue, opUserId);
            }
        }
        return createResult.getRecordId();
    }

    @Override
    protected void updatePropertyValueDbOp(UpdatePropertyValue updatePropertyValue, Long opUserId) {
        PropertyValueRecord propertyValueRecord = propertyValueRecordForUpdate(updatePropertyValue, opUserId);
        propertyValueRecord.attach(JooqContext.getConfiguration(dataSource));
        propertyValueRecord.update();
    }

    @Override
    protected void directBatchUpdatePropertyValueDbOp(List<UpdatePropertyValue> updatePropertyValues, Long opUserId) {
        for (UpdatePropertyValue updatePropertyValue : updatePropertyValues) {
            updatePropertyValue(updatePropertyValue, opUserId);
        }
    }

    @Override
    protected List<DescribePropertyValue> getValueByPropertyKeyDbOp(Long propertyKeyId) {
        List<PropertyValueRecord> propertyValueRecords = getPropertyValue(propertyKeyId);
        return from(propertyValueRecords);
    }

    @Override
    protected DescribePropertyKeyDefinition getDefinitionByPropertyKeyDbOp(Long propertyKeyId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        PropertyKeyDefinitionRecord propertyKeyDefinitionRecord = dslContext
                .selectFrom(PROPERTY_KEY_DEFINITION)
                .where(PROPERTY_KEY_DEFINITION.PROPERTY_KEY_ID.eq(ULong.valueOf(propertyKeyId)))
                .and(PROPERTY_KEY_DEFINITION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetchOne();
        return definitionFrom(propertyKeyDefinitionRecord);
    }

    @Override
    protected DescribeProperty getPropertyDbOp(Identifier<Long> owner, String key) {
        DescribePropertyKey describePropertyKey = getPropertyKeyDbOp(owner, key);
        List<DescribePropertyValue> describePropertyValues = getValueByPropertyKeyDbOp(describePropertyKey.getId());
        DescribePropertyKeyDefinition definition = getDefinitionByPropertyKeyDbOp(describePropertyKey.getId());
        return new DescribeProperty(describePropertyKey, describePropertyValues, definition);
    }

    @Override
    protected List<DescribeProperty> getPropertiesDbOp(Identifier<Long> owner) {
        ULong notDeleted = ULong.valueOf(IsDeleted.NO.getValue());
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectConditionStep selectConditionStep = dslContext.select()
                .from(PROPERTY_KEY)
                .leftJoin(PROPERTY_KEY_DEFINITION).on(PROPERTY_KEY_DEFINITION.PROPERTY_KEY_ID.eq(PROPERTY_KEY.PROPERTY_KEY_ID)).and(PROPERTY_KEY_DEFINITION.IS_DELETED.eq(notDeleted))
                .leftJoin(PROPERTY_VALUE).on(PROPERTY_VALUE.PROPERTY_KEY_ID.eq(PROPERTY_KEY.PROPERTY_KEY_ID)).and(PROPERTY_VALUE.IS_DELETED.eq(notDeleted))
                .where(PROPERTY_KEY.OWNER_TYPE.eq(UByte.valueOf(owner.getType().getCode())))
                .and(PROPERTY_KEY.OWNER_IDENTIFIER.eq(ULong.valueOf(owner.getValue())))
                .and(PROPERTY_KEY.IS_DELETED.eq(notDeleted));
        Result result = selectConditionStep.fetch();
        List<DescribeProperty> list = null;
        if (result != null && result.size() > 0) {
            Result<PropertyKeyRecord> propertyKeyRecords = result.into(PROPERTY_KEY);
            if (propertyKeyRecords != null && propertyKeyRecords.size() > 0) {
                list = new ArrayList<>();
                Result<PropertyValueRecord> propertyValueRecords = result.into(PROPERTY_VALUE);
                Map<ULong, List<PropertyValueRecord>> valueByKeyMap;
                if (propertyValueRecords != null) {
                    valueByKeyMap = propertyValueRecords.stream()
                            .filter(value -> value != null && value.getPropertyKeyId() != null) // 作为连接表,可能不存在记录
                            .collect(Collectors.groupingBy(PropertyValueRecord::getPropertyKeyId));
                } else {
                    valueByKeyMap = new HashMap<>(1);
                }

                Result<PropertyKeyDefinitionRecord> propertyKeyDefinitionRecords = result.into(PROPERTY_KEY_DEFINITION);
                Map<ULong, PropertyKeyDefinitionRecord> definitionByKeyMap = new HashMap<>(result.size());
                if (propertyKeyDefinitionRecords != null) {
                    for (PropertyKeyDefinitionRecord propertyKeyDefinitionRecord : propertyKeyDefinitionRecords) {
                        if (propertyKeyDefinitionRecord != null && propertyKeyDefinitionRecord.getPropertyKeyId() != null) {// 作为连接表,可能不存在记录
                            definitionByKeyMap.put(propertyKeyDefinitionRecord.getPropertyKeyId(), propertyKeyDefinitionRecord);
                        }
                    }
                }
                Set<Long> propertyKeyIdSet = new HashSet<>(propertyKeyRecords.size());
                for (PropertyKeyRecord propertyKeyRecord : propertyKeyRecords) {
                    if (propertyKeyIdSet.add(propertyKeyRecord.getPropertyKeyId().longValue())) { // 连接表后,主表的记录返回时可能会重复,因为连接的表中有多条记录属于主表
                        List<PropertyValueRecord> valueRecords = valueByKeyMap.get(propertyKeyRecord.getPropertyKeyId());
                        List<DescribePropertyValue> describePropertyValues = from(valueRecords);
                        PropertyKeyDefinitionRecord definitionRecord = definitionByKeyMap.get(propertyKeyRecord.getPropertyKeyId());
                        DescribePropertyKeyDefinition describePropertyKeyDefinition = definitionFrom(definitionRecord);
                        checkMultiValued(propertyKeyRecord.getPropertyKeyId().longValue(), describePropertyKeyDefinition, describePropertyValues);
                        DescribeProperty describeProperty = new DescribeProperty(from(propertyKeyRecord), describePropertyValues, describePropertyKeyDefinition);
                        list.add(describeProperty);
                    }
                }
            }
        }
        return list;
    }

    private void checkMultiValued(long propertyKeyId, DescribePropertyKeyDefinition describePropertyKeyDefinition, List<DescribePropertyValue> describePropertyValues) {
        if (describePropertyKeyDefinition != null
                && !describePropertyKeyDefinition.isMultiValued()
                && describePropertyValues != null
                && describePropertyValues.size() > 1) {
            throw new IllegalStateException("property key id = " + propertyKeyId
                    + ",isMultiValued = " + describePropertyKeyDefinition.isMultiValued()
                    + ",不是Multi-Valued,但是有多个value");
        }
    }

    @Override
    protected CreateResult createPropertyKeyDefinitionDbOp(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException {
        PropertyKeyDefinitionRecord record = propertyKeyDefinitionRecordForInsert(definition, opUserId);
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<Record1<ULong>> existsRecordSelector = DSL.using(configuration)
                .select(PROPERTY_KEY_DEFINITION.PROPERTY_DEFINITION_ID)
                .from(PROPERTY_KEY_DEFINITION)
                .where(PROPERTY_KEY_DEFINITION.PROPERTY_KEY_ID.eq(ULong.valueOf(definition.getPropertyKeyId())))
                .and(PROPERTY_KEY_DEFINITION.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        CreateResult result = insertAfterSelectCheck(dataSource, PROPERTY_KEY_DEFINITION, record, existsRecordSelector);
        result.valid();
        if (result.getExistsRecordId() != null) {
            throw new AlreadyExistsException("property key id = " + definition.getPropertyKeyId() + ",已经有了definition");
        }
        return result;
    }

    private PropertyValueRecord propertyValueRecordForUpdate(UpdatePropertyValue updatePropertyValue, Long opUserId) {
        PropertyValueRecord record = new PropertyValueRecord();
        record.setPropertyValueId(ULong.valueOf(updatePropertyValue.getId()));
        record.setValue(updatePropertyValue.getValue());
        record.setLastModifyTime(LocalDateTime.now());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        return record;
    }

    private DescribePropertyKey from(PropertyKeyRecord propertyKeyRecord) {
        DescribePropertyKey describePropertyKey = new DescribePropertyKey();
        describePropertyKey.setId(propertyKeyRecord.getPropertyKeyId().longValue());
        describePropertyKey.setKey(propertyKeyRecord.getKey());
        describePropertyKey.setOwner(new LongIdentifier(propertyKeyRecord.getOwnerIdentifier().longValue(),
                IdentifierTypeRegistry.defaultRegistry.lookup(propertyKeyRecord.getOwnerType().intValue())));
        describePropertyKey.setPropertyKeyType(propertyKeyRecord.getType().byteValue());
        return describePropertyKey;
    }

    private DescribePropertyValue from(PropertyValueRecord propertyValueRecord) {
        DescribePropertyValue describePropertyValue = new DescribePropertyValue();
        describePropertyValue.setId(propertyValueRecord.getPropertyValueId().longValue());
        describePropertyValue.setPropertyKeyId(propertyValueRecord.getPropertyKeyId().longValue());
        describePropertyValue.setValue(propertyValueRecord.getValue());
        return describePropertyValue;
    }

    private List<DescribePropertyValue> from(List<PropertyValueRecord> propertyValueRecords) {
        if (propertyValueRecords == null) {
            return null;
        }
        List<DescribePropertyValue> list = new ArrayList<>(propertyValueRecords.size());
        for (PropertyValueRecord propertyValueRecord : propertyValueRecords) {
            list.add(from(propertyValueRecord));
        }
        return list;
    }

    private PropertyValueRecord propertyValueRecordForInsert(CreatePropertyValue createPropertyValue, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PropertyValueRecord(ULong.valueOf(createPropertyValue.getId()),
                ULong.valueOf(createPropertyValue.getPropertyKeyId()),
                createPropertyValue.getValue(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private List<PropertyValueRecord> newPropertyValueRecords(List<CreatePropertyValue> createPropertyValues, Long opUserId) {
        List<PropertyValueRecord> list = new ArrayList<>(createPropertyValues.size());
        for (CreatePropertyValue createPropertyValue : createPropertyValues) {
            list.add(propertyValueRecordForInsert(createPropertyValue, opUserId));
        }
        return list;
    }

    private CreateResult insertPropertyValueRecord(CreatePropertyValue createPropertyValue, Long opUserId) {
        return insert(dataSource, PROPERTY_VALUE, propertyValueRecordForInsert(createPropertyValue, opUserId));
    }

    private PropertyKeyRecord newPropertyKeyRecord(CreatePropertyKey createPropertyKey, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PropertyKeyRecord(
                ULong.valueOf(createPropertyKey.getId()),
                createPropertyKey.getKey(),
                UByte.valueOf(createPropertyKey.getType()),
                UByte.valueOf(createPropertyKey.getOwner().getType().getCode()),
                ULong.valueOf(createPropertyKey.getOwner().getValue()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue())
        );
    }

    private List<PropertyKeyRecord> newPropertyKeyRecords(List<CreatePropertyKey> createPropertyKeys, Long opUserId) {
        List<PropertyKeyRecord> list = new ArrayList<>(createPropertyKeys.size());
        for (CreatePropertyKey createPropertyKey : createPropertyKeys) {
            list.add(newPropertyKeyRecord(createPropertyKey, opUserId));
        }
        return list;
    }

    private PropertyKeyDefinitionRecord propertyKeyDefinitionRecordForInsert(CreatePropertyKeyDefinition definition, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new PropertyKeyDefinitionRecord(ULong.valueOf(definition.getId()),
                ULong.valueOf(definition.getPropertyKeyId()),
                definition.getDataType().getFullName(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue())
        );
    }

    private List<PropertyKeyDefinitionRecord> propertyKeyDefinitionRecordsForInsert(List<CreatePropertyKeyDefinition> definitions, Long opUserId) {
        List<PropertyKeyDefinitionRecord> list = new ArrayList<>(definitions.size());
        for (CreatePropertyKeyDefinition definition : definitions) {
            list.add(propertyKeyDefinitionRecordForInsert(definition, opUserId));
        }
        return list;
    }

    private DescribePropertyKeyDefinition definitionFrom(PropertyKeyDefinitionRecord record) {
        if (record == null) {
            return null;
        }
        DescribePropertyKeyDefinition describePropertyKeyDefinition = new DescribePropertyKeyDefinition();
        describePropertyKeyDefinition.setId(record.getPropertyDefinitionId().longValue());
        describePropertyKeyDefinition.setPropertyKeyId(record.getPropertyKeyId().longValue());
        DataType dataType = DataTypeRegistry.defaultRegistry.lookup(record.getDataType());
        describePropertyKeyDefinition.setDataType(dataType);
        return describePropertyKeyDefinition;
    }

    private List<DescribePropertyKeyDefinition> definitionsFromList(List<PropertyKeyDefinitionRecord> propertyKeyDefinitionRecords) {
        if (propertyKeyDefinitionRecords == null) {
            return null;
        }
        List<DescribePropertyKeyDefinition> list = new ArrayList<>(propertyKeyDefinitionRecords.size());
        for (PropertyKeyDefinitionRecord propertyKeyDefinitionRecord : propertyKeyDefinitionRecords) {
            list.add(definitionFrom(propertyKeyDefinitionRecord));
        }
        return list;
    }

    private PropertyValueRecord getPropertyValueById(long propertyValueId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(PROPERTY_VALUE)
                .where(PROPERTY_VALUE.PROPERTY_VALUE_ID.eq(ULong.valueOf(propertyValueId)))
                .and(PROPERTY_VALUE.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())))
                .fetchOne();
    }

}
