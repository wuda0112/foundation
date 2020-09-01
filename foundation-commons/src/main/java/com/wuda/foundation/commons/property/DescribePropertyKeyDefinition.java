package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.datatype.DataDefinition;
import com.wuda.foundation.lang.datatype.DataType;
import lombok.Data;

@Data
public class DescribePropertyKeyDefinition {

    private Long id;
    private Long propertyKeyId;
    private DataType dataType;
    private boolean multiValued;

    /**
     * to {@link DataDefinition}.
     *
     * @return {@link DataDefinition}.
     */
    public DataDefinition toDataDefinition() {
        return new DataDefinition(dataType);
    }
}
