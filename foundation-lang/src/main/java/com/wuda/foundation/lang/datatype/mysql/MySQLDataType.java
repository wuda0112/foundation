package com.wuda.foundation.lang.datatype.mysql;

import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
import com.wuda.foundation.lang.datatype.DataTypeSchema;

/**
 * 使用MySQL的数据类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum MySQLDataType implements DataType {

    VARCHAR(MySQLDataTypeSchema.instance, "VARCHAR", new MySQLVarcharDataTypeHandler(), false),
    INT(MySQLDataTypeSchema.instance, "INT", new MySQLIntDataTypeHandler(), false);

    private DataTypeSchema schema;
    private String name;
    private DataTypeHandler handler;
    private boolean isCollection;

    MySQLDataType(DataTypeSchema schema, String name, DataTypeHandler handler, boolean isCollection) {
        this.schema = schema;
        this.name = name;
        this.handler = handler;
        this.isCollection = isCollection;
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

    @Override
    public DataTypeHandler getHandler() {
        return handler;
    }
}
