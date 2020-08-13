package com.wuda.foundation.lang.datatype.mysql;

import com.wuda.foundation.lang.datatype.DataDefinition;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
import com.wuda.foundation.lang.datatype.ValidateResult;

import java.util.Objects;

public class MySQLVarcharDataTypeHandler implements DataTypeHandler<String> {

    @Override
    public ValidateResult validate(DataDefinition dataDefinition, String value) {
        check(dataDefinition);
        return ValidateResult.ok();
    }

    @Override
    public String parseValue(String value) {
        return value;
    }

    private void check(DataDefinition dataDefinition) {
        Objects.requireNonNull(dataDefinition);
        assert dataDefinition.getDataType().equals(MySQLDataType.VARCHAR);
    }
}
