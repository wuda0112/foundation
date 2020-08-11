package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;

import java.util.List;

/**
 * property utils.
 *
 * @author wuda
 * @since 1.0.0
 */
public class PropertyUtils {

    private PropertyUtils() {

    }

    /**
     * 从property集合中找出给定名称的property.
     *
     * @param properties  候选的集合
     * @param propertyKeyNaming 需要查找的property key
     * @return <code>null</code>-如果没有找到
     */
    public static DescribeProperty getProperty(List<DescribeProperty> properties, PropertyKeyNaming propertyKeyNaming) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }
        for (DescribeProperty describeProperty : properties) {
            DescribePropertyKey describePropertyKey = describeProperty.getPropertyKey();
            IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(describePropertyKey.getOwner().getType().getCode());
            if (identifierType.getCode() == propertyKeyNaming.getIdentifierType().getCode()
                    && describePropertyKey.getKey().equals(propertyKeyNaming.getKey())) {
                return describeProperty;
            }
        }
        return null;
    }
}
