package com.wuda.foundation.lang.datatype;

/**
 * 每种数据类型对应一个handler.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface DataTypeHandler<T> {

    /**
     * 检查数据是否有效.比如该Handler处理{@link Long}类型的数据,但是如果传递过来值无法转换成{@link Long}类型,则该数据无效.
     *
     * @param dataDefinition 数据定义,在该定义下,给定的value是否有效
     * @param value          data value
     * @return <code>true</code>-如果该value满足data type的定义
     */
    ValidateResult validate(DataDefinition dataDefinition, String value);

    /**
     * 将原始的字符串类型的数据转换成{@link DataType}对应的数据.
     *
     * @param value 原始的字符串数据
     * @return {@link DataType}对应的数据.
     */
    T parseValue(String value);
}
