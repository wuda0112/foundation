package com.wuda.foundation.lang.datatype.mysql;

import com.wuda.foundation.lang.datatype.DataTypeSchema;

/**
 * mysql schema.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum MySQLDataTypeSchema implements DataTypeSchema {

    MySQL("MySQL");

    private String name;

    MySQLDataTypeSchema(String name) {
        this.name = name;
    }

    @Override
    public String getSchema() {
        return name;
    }
}
