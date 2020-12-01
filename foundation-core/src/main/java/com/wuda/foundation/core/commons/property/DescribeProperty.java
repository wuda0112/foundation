package com.wuda.foundation.core.commons.property;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DescribeProperty {

    private DescribePropertyKey propertyKey;
    private List<DescribePropertyValue> propertyValues;
    private DescribePropertyKeyDefinition propertyDefinition;
}
