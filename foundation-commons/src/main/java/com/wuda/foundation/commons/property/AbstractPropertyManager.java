package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;

public abstract class AbstractPropertyManager implements PropertyManager {
    @Override
    public DescribePropertyKey getPropertyKey(Identifier<Long> owner, String key) {
        ExtObjects.requireNonNull(owner, key);
        return getPropertyKeyDbOp(owner, key);
    }

    /**
     * 作为{@link #getPropertyKey}方法的一部分,参数的校验已经在{@link #getPropertyKey}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param owner 该属性的拥有者
     * @param key   property的key
     * @return 如果不存在则返回<code>null</code>
     */
    protected abstract DescribePropertyKey getPropertyKeyDbOp(Identifier<Long> owner, String key);

    @Override
    public DescribePropertyKey getPropertyKey(Long id) {
        return getPropertyKeyDbOp(id);
    }

    /**
     * 作为{@link #getPropertyKey(Long)}方法的一部分,参数的校验已经在{@link #getPropertyKey(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param id property key id
     * @return 如果不存在则返回<code>null</code>
     */
    protected abstract DescribePropertyKey getPropertyKeyDbOp(Long id);

    @Override
    public long createPropertyKey(Identifier<Long> owner, String key, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse, InsertMode insertMode, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(owner, key, propertyKeyType, propertyKeyUse, keyGenerator, opUserId);
        return createPropertyKeyDbOp(owner, key, propertyKeyType, propertyKeyUse, insertMode, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createPropertyKey}方法的一部分,参数的校验已经在{@link #createPropertyKey}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param owner           owner
     * @param key             key
     * @param propertyKeyType {@link PropertyKeyType}
     * @param propertyKeyUse  {@link PropertyKeyUse}
     * @param insertMode      数据插入模式
     * @param keyGenerator    主键生成器
     * @param opUserId        操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 如果owner已经拥有这样的key, 则返回已经存在的property key id;如果不存在,则返回新创建的记录的id
     */
    protected abstract long createPropertyKeyDbOp(Identifier<Long> owner, String key, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse, InsertMode insertMode, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public long createPropertyValue(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId) {
        ExtObjects.requireNonNull(propertyKeyId, value, keyGenerator, opUserId);
        return createPropertyValueDbOp(propertyKeyId, value, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createPropertyValue}方法的一部分,参数的校验已经在{@link #createPropertyValue}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param propertyKeyId property key id
     * @param value         property value
     * @param keyGenerator  主键生成器
     * @param opUserId      操作人用户ID,是谁正在添加这个店铺的基本信息
     * @return 如果已经存在, 则返回已经存在的value的id;如果不存在,返回新增的value的id
     */
    protected abstract long createPropertyValueDbOp(Long propertyKeyId, String value, KeyGenerator<Long> keyGenerator, Long opUserId);
}
