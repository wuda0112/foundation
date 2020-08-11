package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.DataType;

/**
 * 定义静态的属性模板,就好像为MySQL Table定义固定的Column一样.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PropertyTemplate {

    /**
     * property key naming.
     *
     * @return {@link PropertyKeyNaming}
     */
    PropertyKeyNaming getPropertyKeyNaming();

    /**
     * property key的数据类型
     *
     * @return 数据类型
     */
    DataType getDataType();

    /**
     * property value是否必须,就好比是MySQL的not null的设置.
     *
     * @return <code>true</code>-如果是必须
     */
    boolean required();

    /**
     * 默认值
     *
     * @param <T> 默认值的数据类型
     * @return 默认值
     */
    <T> T defaultValue();

}
