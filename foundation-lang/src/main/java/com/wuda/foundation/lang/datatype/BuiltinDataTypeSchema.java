package com.wuda.foundation.lang.datatype;

import com.wuda.foundation.lang.DataTypeSchema;

/**
 * 内置的数据类型schema.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinDataTypeSchema implements DataTypeSchema {

    MySQL("MySQL");

    private String name;

    BuiltinDataTypeSchema(String name) {
        this.name = name;
    }
    
    @Override
    public String getSchema() {
        return name;
    }
}
