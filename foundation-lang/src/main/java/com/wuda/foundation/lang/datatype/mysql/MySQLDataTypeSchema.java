package com.wuda.foundation.lang.datatype.mysql;

import com.wuda.foundation.lang.datatype.DataTypeSchema;

/**
 * mysql schema.
 *
 * @author wuda
 * @since 1.0.0
 */
public final class MySQLDataTypeSchema implements DataTypeSchema {

    private String name;

    public static MySQLDataTypeSchema instance = new MySQLDataTypeSchema("MySQL");

    private MySQLDataTypeSchema(String name) {
        this.name = name;
    }

    @Override
    public String getSchema() {
        return name;
    }
}
