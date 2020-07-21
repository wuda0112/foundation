package com.wuda.foundation.commons.impl;

import com.wuda.foundation.commons.impl.jooq.generation.tables.PropertyValue;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PropertyKeyRecord;
import com.wuda.foundation.commons.impl.jooq.generation.tables.records.PropertyValueRecord;
import com.wuda.foundation.commons.property.AbstractPropertyManager;
import com.wuda.foundation.commons.property.DescribePropertyKey;
import com.wuda.foundation.commons.property.PropertyKeyType;
import com.wuda.foundation.commons.property.PropertyKeyUse;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.DataType;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.datatype.MySQLDataTypes;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static com.wuda.foundation.commons.impl.jooq.generation.tables.PropertyKey.PROPERTY_KEY;
import static org.jooq.impl.DSL.param;
import static org.jooq.impl.DSL.select;

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
    protected long createPropertyKeyDbOp(Identifier<Long> owner, String key, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse, InsertMode insertMode, KeyGenerator<Long> keyGenerator, Long opUserId) {
        Long propertyKeyId = keyGenerator.next();
        LocalDateTime now = LocalDateTime.now();
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        SelectConditionStep<PropertyKeyRecord> existsRecordSelector = DSL.using(configuration)
                .selectFrom(PROPERTY_KEY)
                .where(PROPERTY_KEY.OWNER_TYPE.eq(UByte.valueOf(owner.getType().getCode())))
                .and(PROPERTY_KEY.OWNER_IDENTIFIER.eq(ULong.valueOf(owner.getValue())))
                .and(PROPERTY_KEY.KEY.eq(key))
                .and(PROPERTY_KEY.IS_DELETED.eq(ULong.valueOf(IsDeleted.NO.getValue())));
        Field[] fields = new Field[]{
                param(PROPERTY_KEY.PROPERTY_KEY_ID.getName(), ULong.valueOf(propertyKeyId)),
                param(PROPERTY_KEY.KEY.getName(), key),
                param(PROPERTY_KEY.TYPE.getName(), UByte.valueOf(propertyKeyType.getCode())),
                param(PROPERTY_KEY.OWNER_TYPE.getName(), UByte.valueOf(owner.getType().getCode())),
                param(PROPERTY_KEY.OWNER_IDENTIFIER.getName(), ULong.valueOf(owner.getValue())),
                param(PROPERTY_KEY.USE.getName(), UByte.valueOf(propertyKeyUse.getCode())),
                param(PROPERTY_KEY.CREATE_TIME.getName(), now),
                param(PROPERTY_KEY.CREATE_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(PROPERTY_KEY.LAST_MODIFY_TIME.getName(), now),
                param(PROPERTY_KEY.LAST_MODIFY_USER_ID.getName(), ULong.valueOf(opUserId)),
                param(PROPERTY_KEY.IS_DELETED.getName(), ULong.valueOf(IsDeleted.NO.getValue()))
        };
        Long id;
        switch (insertMode) {
            case DIRECT:
                id = insert(dataSource, PROPERTY_KEY, fields, PROPERTY_KEY.PROPERTY_KEY_ID);
                break;
            case INSERT_AFTER_SELECT_CHECK:
                id = insertAfterSelectCheck(dataSource, PROPERTY_KEY, fields, existsRecordSelector, PROPERTY_KEY.PROPERTY_KEY_ID);
                break;
            case INSERT_WHERE_NOT_EXISTS:
                id = insertIfNotExists(dataSource, PROPERTY_KEY, fields, existsRecordSelector, PROPERTY_KEY.PROPERTY_KEY_ID);
                break;
            default:
                id = insertAfterSelectCheck(dataSource, PROPERTY_KEY, fields, existsRecordSelector, PROPERTY_KEY.PROPERTY_KEY_ID);
                break;
        }
        return id;
    }

    @Override
    protected long createPropertyValueDbOp(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId) {
        DescribePropertyKey describePropertyKey = getPropertyKeyDbOp(propertyKeyId);
        DataType dataType = describePropertyKey.getDataType();
        List<PropertyValueRecord> values = getPropertyValue(propertyKeyId);
        long propertyValueId;
        if (dataType.isCollection()) {
            PropertyValueRecord exists = propertyValueExists(values, value);
            if (exists != null) {
                propertyValueId = exists.getPropertyValueId().longValue();
            } else {
                propertyValueId = insertPropertyValueRecord(propertyKeyId, value, keyGenerator, opUserId);
            }
        } else {
            if (values != null && !values.isEmpty()) {
                assert values.size() == 1 : "非集合类型的Property,value最多只能有一个";
                PropertyValueRecord propertyValueRecord = values.get(0);
                propertyValueId = propertyValueRecord.getPropertyValueId().longValue();
            } else {
                propertyValueId = insertPropertyValueRecord(propertyKeyId, value, keyGenerator, opUserId);
            }
        }
        return propertyValueId;
    }

    private List<PropertyValueRecord> getPropertyValue(Long propertyKeyId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        return dslContext.selectFrom(PropertyValue.PROPERTY_VALUE)
                .where(PropertyValue.PROPERTY_VALUE.PROPERTY_KEY_ID.eq(ULong.valueOf(propertyKeyId)))
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

    private DescribePropertyKey from(PropertyKeyRecord propertyKeyRecord) {
        DescribePropertyKey describePropertyKey = new DescribePropertyKey();
        describePropertyKey.setId(propertyKeyRecord.getPropertyKeyId().longValue());
        describePropertyKey.setKey(propertyKeyRecord.getKey());
        describePropertyKey.setDataType(MySQLDataTypes.VARCHAR);
//        describePropertyKey.setOwner(owner);
//        describePropertyKey.setPropertyKeyType(PropertyKeyType);
        return describePropertyKey;
    }

    private PropertyValueRecord generatePropertyValueRecord(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        long id = keyGenerator.next();
        PropertyValueRecord propertyValueRecord = new PropertyValueRecord();
        propertyValueRecord.setPropertyValueId(ULong.valueOf(id));
        propertyValueRecord.setPropertyKeyId(ULong.valueOf(propertyKeyId));
        propertyValueRecord.setValue(value);
        propertyValueRecord.setCreateTime(now);
        propertyValueRecord.setCreateUserId(ULong.valueOf(opUserId));
        propertyValueRecord.setLastModifyTime(now);
        propertyValueRecord.setLastModifyUserId(ULong.valueOf(opUserId));
        return propertyValueRecord;
    }

    private long insertPropertyValueRecord(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        PropertyValueRecord propertyValueRecord = generatePropertyValueRecord(propertyKeyId, value, keyGenerator, opUserId);
        propertyValueRecord.attach(configuration);
        propertyValueRecord.insert();
        return propertyValueRecord.getPropertyValueId().longValue();
    }


}
