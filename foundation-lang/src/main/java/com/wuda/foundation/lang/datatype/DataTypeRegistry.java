package com.wuda.foundation.lang.datatype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link DataType}的注册中心.默认使用{@link #defaultRegistry}即可.
 *
 * @author wuda
 * @since 1.0.0
 */
public class DataTypeRegistry {

    private Map<DataTypeSchema, List<DataType>> bySchemaMap = new ConcurrentHashMap<>();

    public final static DataTypeRegistry defaultRegistry = new DataTypeRegistry();

    /**
     * 注册{@link DataType}.
     *
     * @param dataType data type
     */
    public void register(DataType dataType) {
        List<DataType> dataTypeList = bySchemaMap.get(dataType.getSchema());
        if (dataTypeList != null) {
            for (DataType dt : dataTypeList) {
                if (dt.getName().equalsIgnoreCase(dataType.getName())) {
                    throw new IllegalStateException("data type schema = " + dataType.getSchema().getSchema() + ",data type name = " + dataType.getName() + ",Duplicate!");
                }
            }
        } else {
            dataTypeList = new ArrayList<>();
            bySchemaMap.put(dataType.getSchema(), dataTypeList);
        }
        dataTypeList.add(dataType);
    }

    /**
     * 在注册中心中查找.如果没有找到则抛出{@link IllegalStateException}异常.
     *
     * @param schema       data type schema
     * @param dataTypeName {@link DataType#getName()}
     * @return 保证不会为<code>null</code>
     */
    public DataType lookup(DataTypeSchema schema, String dataTypeName) {
        DataType dataType = null;
        List<DataType> dataTypeList = bySchemaMap.get(schema);
        if (dataTypeList != null && !dataTypeList.isEmpty()) {
            for (DataType dt : dataTypeList) {
                if (dt.getSchema().equals(schema) && dt.getName().equals(dataTypeName)) {
                    dataType = dt;
                    break;
                }
            }
        }
        if (dataType == null) {
            throw new IllegalStateException("data type schema = " + schema.getSchema() + ",data type name = " + dataTypeName + ",没有找到,很可能是没有注册!");
        }
        return null;
    }

    /**
     * 在注册中心中查找.如果没有找到则抛出{@link IllegalStateException}异常.
     *
     * @param dataTypeFullName {@link DataType#getFullName()} ()}
     * @return 保证不会为<code>null</code>
     */
    public DataType lookup(String dataTypeFullName) {
        Set<Map.Entry<DataTypeSchema, List<DataType>>> entrySet = bySchemaMap.entrySet();
        DataType dataType = null;
        if (!entrySet.isEmpty()) {
            for (Map.Entry<DataTypeSchema, List<DataType>> entry : entrySet) {
                List<DataType> dataTypeList = entry.getValue();
                for (DataType dt : dataTypeList) {
                    if (dt.getFullName().equals(dataTypeFullName)) {
                        dataType = dt;
                        break;
                    }
                }
            }
        }
        if (dataType == null) {
            throw new IllegalStateException("data type full name = " + dataTypeFullName + ",没有找到,很可能是没有注册!");
        }
        return dataType;
    }
}
