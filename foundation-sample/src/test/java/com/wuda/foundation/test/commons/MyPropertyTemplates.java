package com.wuda.foundation.test.commons;

import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.PropertyKeyNaming;
import com.wuda.foundation.commons.property.PropertyKeyType;
import com.wuda.foundation.commons.property.PropertyKeyUse;
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

        NAME("name", BuiltinIdentifierType.MOCK);
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

        NAME(MyPropertyKeyNaming.NAME, MySQLDataType.VARCHAR, BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO);

        private PropertyKeyNaming propertyKeyNaming;
        private DataType dataType;
        private PropertyKeyType propertyKeyType;
        private PropertyKeyUse propertyKeyUse;

        MyPropertyTemplate(PropertyKeyNaming propertyKeyNaming, DataType dataType, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse) {
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
        public PropertyKeyType getPropertyKeyType() {
            return propertyKeyType;
        }

        @Override
        public PropertyKeyUse getPropertyKeyUse() {
            return propertyKeyUse;
        }
    }

    @Override
    public List<PropertyTemplate> templates() {
        return Collections.singletonList(MyPropertyTemplate.NAME);
    }
}
