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
     * @param properties        候选的集合
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

    /**
     * 对于给定的属性集,如果没有模板要求的属性,则使用该模板自动填充一个默认的属性出来.
     * 举例,比如属性模板有A,B,C三个property key,而属性集中只有B一个property key,则使用模板,
     * 自动填充A,C两个属性,返回的属性集从一个变成了三个.
     *
     * @param properties 属性集
     * @param templates  模板集
     * @return 新的属性集
     */
    public static List<DescribeProperty> padding(List<DescribeProperty> properties, List<PropertyTemplate> templates) {
        return null;
    }
}
