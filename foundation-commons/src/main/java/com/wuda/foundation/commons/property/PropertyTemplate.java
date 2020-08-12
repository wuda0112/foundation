package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.DataType;

/**
 * 定义静态的属性模板,就好像为MySQL Table定义固定的Column一样.
 *
 * @author wuda
 * @since 1.0.0
 */
public class PropertyTemplate {


    private PropertyKeyNaming propertyKeyNaming;
    private DataType dataType;

    /**
     * 构建模板.
     *
     * @param propertyKeyNaming 属性名
     * @param dataType          数据类型
     */
    public PropertyTemplate(PropertyKeyNaming propertyKeyNaming, DataType dataType) {
        this.propertyKeyNaming = propertyKeyNaming;
        this.dataType = dataType;
    }

    /**
     * property key naming.
     *
     * @return {@link PropertyKeyNaming}
     */
    public PropertyKeyNaming getPropertyKeyNaming() {
        return propertyKeyNaming;
    }

    /**
     * property key的数据类型
     *
     * @return 数据类型
     */
    public DataType getDataType() {
        return dataType;
    }

}
