package com.wuda.foundation.lang;

/**
 * 数据类型的定义.可以使用已有系统的数据类型,比如：MySQL的VARCHAR类型;
 * 比如Java的Integer类型,也可以自定义数据类型.
 *
 * @author wuda
 * @see DataTypeSchema
 */
public interface DataType {

    /**
     * 获取该数据类型所属的schema.
     *
     * @return schema
     */
    DataTypeSchema getSchema();

    /**
     * 获取data type name.
     *
     * @return data type name
     */
    String getName();

    /**
     * 完整的名称,包含schema和data type name.
     * 我们定义数据类型的完整名称由三部分组成,
     * <ol>
     * <li>第一部分表示该数据类型所处的体系</li>
     * <li>第二部分是固定的英文冒号</li>
     * <li>第三部分表示该体系下具体的数据类型的名称</li>
     * </ol>
     * 因此一个data type的完整名称的格式是：scheme:data type name
     * 比如：BuiltinDataTypeSchema:VARCHAR
     *
     * @return full name
     */
    String getFullName();

    /**
     * 是否集合类型.
     *
     * @return <code>true</code>-如果是
     */
    boolean isCollection();
}
