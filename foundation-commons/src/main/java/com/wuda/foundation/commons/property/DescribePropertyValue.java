package com.wuda.foundation.commons.property;

import lombok.Data;

@Data
public class DescribePropertyValue {
    private Long id;
    private Long propertyKeyId;
    private String value;
}
