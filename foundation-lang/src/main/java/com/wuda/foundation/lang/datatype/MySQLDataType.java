package com.wuda.foundation.lang.datatype;

import com.wuda.foundation.lang.DataType;
import com.wuda.foundation.lang.DataTypeSchema;

/**
 * 使用MySQL的数据类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum MySQLDataType implements DataType {

    VARCHAR(BuiltinDataTypeSchema.MySQL, "VARCHAR", false);

    private DataTypeSchema schema;
    private String name;
    private boolean isCollection;

    MySQLDataType(DataTypeSchema schema, String name, boolean isCollection) {
        this.schema = schema;
        this.name = name;
        this.isCollection = isCollection;
    }

    static {
        MySQLDataType[] dataTypes = MySQLDataType.values();
        for (MySQLDataType dataType : dataTypes) {
            dataType.register();
        }
    }

    @Override
    public DataTypeSchema getSchema() {
        return schema;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return schema.getSchema() + ":" + name;
    }

    @Override
    public boolean isCollection() {
        return isCollection;
    }
}
