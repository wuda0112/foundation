package com.wuda.foundation.lang.datatype;

import com.wuda.foundation.lang.DataTypeSchema;

public enum BuiltinDataTypeSchema implements DataTypeSchema {

    MySQL("MySQL");

    private String name;

    BuiltinDataTypeSchema(String description) {
        this.name = description;
    }

    public String getDescription() {
        return name;
    }

    @Override
    public String getSchema() {
        return name;
    }
}
