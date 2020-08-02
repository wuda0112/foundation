package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.identify.Identifier;

import java.util.List;

/**
 * 属性管理.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PropertyManager {

    /**
     * 获取property的key.
     *
     * @param owner 该属性的拥有者
     * @param key   property的key
     * @return 如果不存在则返回<code>null</code>
     */
    DescribePropertyKey getPropertyKey(Identifier<Long> owner, String key);

    /**
     * 根据主键获取property key.
     *
     * @param id property key id
     * @return property key
     */
    DescribePropertyKey getPropertyKey(Long id);

    /**
     * 创建property key.
     *
     * @param createPropertyKey 创建key的参数
     * @param insertMode        insert mode
     * @param opUserId          操作人用户ID
     * @return 如果owner已经拥有这样的key, 则返回已经存在的property key id;如果不存在,则返回新创建的记录的id
     */
    long createPropertyKey(CreatePropertyKey createPropertyKey, InsertMode insertMode, Long opUserId);

    /**
     * 创建property key.
     *
     * @param createPropertyKeys 创建key的参数
     * @param opUserId           操作人用户ID
     */
    void directBatchInsertPropertyKey(List<CreatePropertyKey> createPropertyKeys, Long opUserId);

    /**
     * /**
     * 为property key设值value.根据property的{@link com.wuda.foundation.lang.DataType 数据类型}不同,所执行的操作也不同
     * <ul>
     * <li>对于基本类型,只要当前property有value,则使用给定的value覆盖已经存在的value;如果当前property没有value,则新增,最终的结果保证该property key只有一个value</li>
     * <li>对于集合类型,如果当前property有相同的value,则没有任何操作;如果当前property没有value,则新增.</li>
     * </ul>
     *
     * @param createPropertyValue 创建value
     * @param insertMode          insert mode
     * @param opUserId            操作人用户ID
     * @return 如果已经存在, 则返回已经存在的value的id;如果不存在,返回新增的value的id
     */
    long createPropertyValue(CreatePropertyValue createPropertyValue, InsertMode insertMode, Long opUserId);

    /**
     * 创建property value.
     *
     * @param createPropertyValues 创建value
     * @param opUserId             操作人用户ID
     */
    void directBatchInsertPropertyValue(List<CreatePropertyValue> createPropertyValues, Long opUserId);

    /**
     * 更新property value.
     *
     * @param updatePropertyValue 更新参数
     * @param opUserId            操作人用户ID
     */
    void updatePropertyValue(UpdatePropertyValue updatePropertyValue, Long opUserId);

    /**
     * 更新property value.
     *
     * @param updatePropertyValues 更新参数
     * @param opUserId             操作人用户ID
     */
    void directBatchUpdatePropertyValue(List<UpdatePropertyValue> updatePropertyValues, Long opUserId);
}
