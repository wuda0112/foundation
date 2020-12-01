package com.wuda.foundation.core.commons.property;

import com.wuda.foundation.lang.datatype.DataType;

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
     * property key type
     *
     * @return property key type
     */
    Byte getPropertyKeyType();

    /**
     * property key use
     *
     * @return property key use
     */
    Byte getPropertyKeyUse();

}
