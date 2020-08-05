package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.DataType;
import lombok.Data;

@Data
public class DescribePropertyDefinition {

    private Long id;
    private Long propertyKeyId;
    private DataType dataType;
}
