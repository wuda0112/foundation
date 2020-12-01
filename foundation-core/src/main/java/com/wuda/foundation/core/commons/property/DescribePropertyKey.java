package com.wuda.foundation.core.commons.property;

import com.wuda.foundation.lang.BasicAttribute;
import com.wuda.foundation.lang.identify.Identifier;
import lombok.Data;

/**
 * 描述property key.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
public class DescribePropertyKey extends BasicAttribute {

    private Long id;
    private String key;
    private Byte propertyKeyType;
    private Identifier<Long> owner;

}
