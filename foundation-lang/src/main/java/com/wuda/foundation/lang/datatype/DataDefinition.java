package com.wuda.foundation.lang.datatype;

/**
 * 对数据的定义,包括数据类型等等.
 *
 * @author wuda
 * @since 1.0.0
 */
public class DataDefinition {

    private DataType dataType;

    /**
     * 构建definition.
     *
     * @param dataType 数据类型
     */
    public DataDefinition(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取数据类型
     *
     * @return 数据类型
     */
    public DataType getDataType() {
        return dataType;
    }
}
