package com.wuda.foundation.test.commons;

import com.wuda.foundation.commons.property.*;
import com.wuda.foundation.lang.datatype.MySQLDataType;
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

    public final static PropertyTemplate NAME = new PropertyTemplate(MyPropertyKeyNaming.NAME, MySQLDataType.VARCHAR, BuiltinPropertyKeyType.ZERO, BuiltinPropertyKeyUse.ZERO);

    @Override
    public List<PropertyTemplate> templates() {
        return Collections.singletonList(NAME);
    }
}
