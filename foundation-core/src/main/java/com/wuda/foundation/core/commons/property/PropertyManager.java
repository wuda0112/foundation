package com.wuda.foundation.core.commons.property;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.datatype.DataType;
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
     * @return property key，如果不存在则返回<code>null</code>
     */
    DescribePropertyKey getPropertyKey(Long id);

    /**
     * 创建property key.
     *
     * @param createPropertyKey 创建key的参数
     * @param createMode        create mode
     * @param opUserId          操作人用户ID
     * @return 结果中的ID表示的是key的ID.如果owner已经拥有这样的key, 则返回已经存在的property key id;如果不存在,则返回新创建的记录的id.
     * 如果{@link CreateMode}是{@link CreateMode#DIRECT}并且成功返回了,则一定是返回新创建的记录的ID
     */
    CreateResult createPropertyKey(CreatePropertyKey createPropertyKey, CreateMode createMode, Long opUserId);

    /**
     * 创建property key,同时一起创建definition.就好像是创建MySQL的列时,也会创建列的定义.
     * 如果owner有了给定的名称的key,会抛出异常.
     *
     * @param opUserId 操作人用户ID
     * @return 结果中的ID表示的是key的ID.如果owner已经拥有这样的key, 则返回已经存在的property key id;如果不存在,则返回新创建的记录的id
     * @throws AlreadyExistsException 如果owner已经有了给定名称的key.
     */
    CreateResult createPropertyKeyWithDefinition(CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition, Long opUserId) throws AlreadyExistsException;

    /**
     * 创建property key.
     *
     * @param createPropertyKeys 创建key的参数
     * @param opUserId           操作人用户ID
     */
    void directBatchInsertPropertyKey(List<CreatePropertyKey> createPropertyKeys, Long opUserId);

    /**
     * 为property key设值value.根据property的{@link DataType 数据类型}不同,所执行的操作也不同
     * <ul>
     * <li>对于基本类型
     * <ul>
     * <li>如果当前property key没有value,则新增</li>
     * <li>如果当前property key有value,则没有任何操作</li>
     * </ul>最终的结果保证该property key只有一个value</li>
     * <li>
     * 对于集合类型
     * <ul>
     * <li>如果当前property key没有相同的value,则新增</li>
     * <li>如果当前property key有相同的value,则没有任何操作</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param createPropertyValue 创建value
     * @param createMode          mode
     * @param opUserId            操作人用户ID
     * @return 结果中的ID表示value的ID.如果已经存在, 则返回已经存在的value的id;如果不存在,返回新增的value的id
     */
    CreateResult createPropertyValue(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId);

    /**
     * 为property key设值value.根据property的{@link DataType 数据类型}不同,所执行的操作也不同
     * <ul>
     * <li>对于基本类型
     * <ul>
     * <li>如果当前property key没有value,则新增</li>
     * <li>如果当前property key有相同的value,则没有任何操作</li>
     * <li>如果当前property key有value,但是和给定的value不同,则使用当前的value更新已经存在的value</li>
     * </ul>最终的结果保证该property key只有一个value
     * </li>
     * <li>对于集合类型
     * <ul>
     * <li>如果当前property key没有相同的value,则新增</li>
     * <li>如果当前property key有相同的value,则没有任何操作</li>
     * </ul></li>
     * </ul>
     *
     * @param createPropertyValue 创建value
     * @param createMode          mode
     * @param opUserId            操作人用户ID
     * @return 如果已经存在, 则返回已经存在的value的id;如果不存在,返回新增的value的id
     */
    long createOrUpdatePropertyValue(CreatePropertyValue createPropertyValue, CreateMode createMode, Long opUserId);

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

    /**
     * 获取property key的value.
     *
     * @param propertyKeyId property key id
     * @return <code>null</code>-如果不存在
     */
    List<DescribePropertyValue> getValueByPropertyKey(Long propertyKeyId);

    /**
     * 获取property key的definition.
     *
     * @param propertyKeyId property key id
     * @return <code>null</code>-如果不存在
     */
    DescribePropertyKeyDefinition getDefinitionByPropertyKey(Long propertyKeyId);

    /**
     * 获取property.
     *
     * @param owner 该属性的拥有者
     * @param key   property的key
     * @return 如果不存在则返回<code>null</code>
     */
    DescribeProperty getProperty(Identifier<Long> owner, String key);

    /**
     * 获取property.
     *
     * @param owner 该属性的拥有者
     * @return 如果不存在则返回<code>null</code>
     */
    List<DescribeProperty> getProperties(Identifier<Long> owner);

    /**
     * 为property key设值definition.如果该Property key已经有definition,则抛出异常.
     * 如果这个key已经有了value,随意更改definition会出现问题,因此,不能随意更改property key的definition,
     * 就好像在MySQL中不能随意改变Column的数据类型一样.
     *
     * @param definition 创建definition
     * @param opUserId   操作人用户ID
     * @return 结果中的ID表示新创建的definition的ID
     * @throws AlreadyExistsException 如果该property key已经有了定义
     */
    CreateResult createPropertyKeyDefinition(CreatePropertyKeyDefinition definition, Long opUserId) throws AlreadyExistsException;
}
