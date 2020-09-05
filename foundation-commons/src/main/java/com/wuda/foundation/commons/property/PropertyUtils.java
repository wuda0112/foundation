package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.ArrayList;
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
     * @param owner      属性的owner
     * @param properties 属性集
     * @param templates  模板集
     * @return 新的属性集
     */
    public static List<DescribeProperty> padding(LongIdentifier owner, List<DescribeProperty> properties, List<? extends PropertyTemplate> templates) {
        if (templates == null || templates.isEmpty()) {
            return properties;
        }
        checkPropertiesBelongToSameOwner(owner, properties);
        checkTemplatesBelongToSameOwnerType(owner, templates);
        List<DescribeProperty> list;
        if (properties != null) {
            list = new ArrayList<>(properties);
        } else {
            list = new ArrayList<>(templates.size());
        }
        for (PropertyTemplate propertyTemplate : templates) {
            DescribeProperty describeProperty = getProperty(properties, propertyTemplate.getPropertyKeyNaming());
            if (describeProperty == null) {
                describeProperty = generateDescribeProperty(owner, propertyTemplate);
                list.add(describeProperty);
            }
        }
        return list;
    }

    private static void checkTemplatesBelongToSameOwnerType(LongIdentifier owner, List<? extends PropertyTemplate> templates) {
        if (templates == null || templates.isEmpty()) {
            return;
        }
        for (PropertyTemplate propertyTemplate : templates) {
            IdentifierType identifierType = propertyTemplate.getPropertyKeyNaming().getIdentifierType();
            if (identifierType.getCode() != owner.getType().getCode()) {
                throw new IllegalStateException("template property key = " + propertyTemplate.getPropertyKeyNaming().getKey() + ",不属于s类型的owner");
            }
        }
    }

    private static void checkPropertiesBelongToSameOwner(LongIdentifier owner, List<DescribeProperty> properties) {
        if (properties == null || properties.isEmpty()) {
            return;
        }
        for (DescribeProperty describeProperty : properties) {
            Identifier<Long> identifier = describeProperty.getPropertyKey().getOwner();
            if (!identifier.getValue().equals(owner.getValue())
                    || identifier.getType().getCode() != owner.getType().getCode()) {
                throw new IllegalStateException("property key = " + describeProperty.getPropertyKey().getKey() + ",不属于当前owner");
            }
        }
    }

    private static DescribeProperty generateDescribeProperty(LongIdentifier owner, PropertyTemplate propertyTemplate) {
        DescribePropertyKey propertyKey = new DescribePropertyKey();
        PropertyKeyNaming propertyKeyNaming = propertyTemplate.getPropertyKeyNaming();
        propertyKey.setKey(propertyKeyNaming.getKey());
        propertyKey.setPropertyKeyType(propertyTemplate.getPropertyKeyType());
        propertyKey.setOwner(owner);

        DescribePropertyKeyDefinition definition = new DescribePropertyKeyDefinition();
        definition.setDataType(propertyTemplate.getDataType());

        return new DescribeProperty(propertyKey, null, definition);
    }
}
