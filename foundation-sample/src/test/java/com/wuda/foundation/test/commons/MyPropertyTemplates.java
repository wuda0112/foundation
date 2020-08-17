package com.wuda.foundation.test.commons;

import com.wuda.foundation.commons.property.PropertyKeyNaming;
import com.wuda.foundation.commons.property.PropertyTemplate;
import com.wuda.foundation.commons.property.PropertyTemplates;
import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.Collections;
import java.util.List;

public class MyPropertyTemplates implements PropertyTemplates {

    public enum MyPropertyKeyNaming implements PropertyKeyNaming {

        NAME("name", BuiltinIdentifierType.TABLE_ITEM);
        private String key;
        private IdentifierType identifierType;

        MyPropertyKeyNaming(String key, IdentifierType identifierType) {
            this.key = key;
            this.identifierType = identifierType;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public IdentifierType getIdentifierType() {
            return identifierType;
        }
    }

    public enum MyPropertyTemplate implements PropertyTemplate {

        NAME(MyPropertyKeyNaming.NAME, MySQLDataType.VARCHAR, (byte) 0, (byte) 0);

        private PropertyKeyNaming propertyKeyNaming;
        private DataType dataType;
        private Byte propertyKeyType;
        private Byte propertyKeyUse;

        MyPropertyTemplate(PropertyKeyNaming propertyKeyNaming, DataType dataType, Byte propertyKeyType, Byte propertyKeyUse) {
            this.propertyKeyNaming = propertyKeyNaming;
            this.dataType = dataType;
            this.propertyKeyType = propertyKeyType;
            this.propertyKeyUse = propertyKeyUse;
        }

        @Override
        public PropertyKeyNaming getPropertyKeyNaming() {
            return propertyKeyNaming;
        }

        @Override
        public DataType getDataType() {
            return dataType;
        }

        @Override
        public Byte getPropertyKeyType() {
            return propertyKeyType;
        }

        @Override
        public Byte getPropertyKeyUse() {
            return propertyKeyUse;
        }
    }

    @Override
    public List<PropertyTemplate> templates() {
        return Collections.singletonList(MyPropertyTemplate.NAME);
    }
}
