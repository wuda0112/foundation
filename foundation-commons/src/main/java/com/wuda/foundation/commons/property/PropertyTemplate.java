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
    private PropertyKeyType propertyKeyType;
    private PropertyKeyUse propertyKeyUse;

    /**
     * 构建模板.
     *
     * @param propertyKeyNaming 属性名
     * @param dataType          数据类型
     * @param propertyKeyType   property key type
     * @param propertyKeyUse    property key use
     */
    public PropertyTemplate(PropertyKeyNaming propertyKeyNaming, DataType dataType, PropertyKeyType propertyKeyType, PropertyKeyUse propertyKeyUse) {
        this.propertyKeyNaming = propertyKeyNaming;
        this.dataType = dataType;
        this.propertyKeyType = propertyKeyType;
        this.propertyKeyUse = propertyKeyUse;
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

    /**
     * property key type
     *
     * @return property key type
     */
    public PropertyKeyType getPropertyKeyType() {
        return propertyKeyType;
    }

    /**
     * property key use
     *
     * @return property key use
     */
    public PropertyKeyUse getPropertyKeyUse() {
        return propertyKeyUse;
    }

}
