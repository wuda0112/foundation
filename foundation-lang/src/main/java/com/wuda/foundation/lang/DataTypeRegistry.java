package com.wuda.foundation.lang;

import java.util.ArrayList;
import java.util.List;

public class DataTypeRegistry {

    private List<DataType> dataTypes = new ArrayList<>();

    public final static DataTypeRegistry defaultRegistry = new DataTypeRegistry();

    public void register(DataType dataType) {
        dataTypes.add(dataType);
    }

    public DataType getDataType(DataTypeSchema schema, String dataTypeName) {
        for (DataType dataType : dataTypes) {
            if (dataType.getSchema().equals(schema) && dataType.getName().equals(dataTypeName)) {
                return dataType;
            }
        }
        return null;
    }

    public DataType getDataType(String dataTypeFullName) {
        for (DataType dataType : dataTypes) {
            if (dataType.getFullName().equals(dataTypeFullName)) {
                return dataType;
            }
        }
        return null;
    }
}
