package com.wuda.foundation.lang.identify;

/**
 * 唯一标记一个对象,比如username;比如数据库的primary key等等.
 *
 * @param <T> 该identifier的值的数据类型
 * @author wuda
 */
public interface Identifier<T> {

    /**
     * 该identifier的值.
     *
     * @return value
     */
    T getValue();

    /**
     * 该identifier的类型.
     *
     * @return {@link IdentifierType}
     */
    IdentifierType getType();
}
