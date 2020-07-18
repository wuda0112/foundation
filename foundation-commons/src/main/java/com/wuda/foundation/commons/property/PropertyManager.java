package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;

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
     * 如果给定的owner已经拥有这样的key,则不执行任何操作,
     * 即使已经存在的property key的{@link PropertyKeyType}或者{@link PropertyKeyUse}和当前给定的不一致.
     * 如果不存在,则新建.
     *
     * @param owner           owner
     * @param key             key
     * @param propertyKeyType {@link PropertyKeyType}
     * @param propertyKeyUse  {@link PropertyKeyUse}
     * @param keyGenerator    主键生成器
     * @param opUserId        操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 如果owner已经拥有这样的key, 则返回已经存在的property key id;如果不存在,则返回新创建的记录的id
     */
    long createPropertyKey(Identifier<Long> owner, String key, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse, KeyGenerator<Long> keyGenerator, Long opUserId);

    /**
     * 如果给定的property key已经有给定的value,则不执行任何操作;如果没有,就需要分情况处理:
     * 根据property的{@link com.wuda.foundation.lang.DataType 数据类型}不同,所执行的操作也不同
     * <ul>
     * <li>对于基本类型,如果当前property有value,则使用给定的value覆盖已经存在的value;如果当前property没有value,则新增,最终的结果保证该property key只有一个value</li>
     * <li>对于集合类型,如果当前property有相同的value,则没有任何操作;如果当前property没有value,则新增.</li>
     * </ul>
     *
     * @param propertyKeyId property key id
     * @param value         property value
     * @param keyGenerator  主键生成器
     * @param opUserId      操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 如果已经存在, 则返回已经存在的value的id;如果不存在,返回新增的value的id
     */
    long createPropertyValue(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId);
}
