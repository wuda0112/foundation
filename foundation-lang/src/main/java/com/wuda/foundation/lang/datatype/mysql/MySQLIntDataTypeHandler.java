package com.wuda.foundation.lang.datatype.mysql;

import com.wuda.foundation.lang.datatype.DataDefinition;
import com.wuda.foundation.lang.datatype.DataTypeHandler;
import com.wuda.foundation.lang.datatype.ValidateResult;

import java.util.Objects;

public class MySQLIntDataTypeHandler implements DataTypeHandler<Integer> {

    @Override
    public ValidateResult validate(DataDefinition dataDefinition, String value) {
        check(dataDefinition);
        return canParseInt(value);
    }

    @Override
    public Integer parseValue(String value) {
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }

    private void check(DataDefinition dataDefinition) {
        Objects.requireNonNull(dataDefinition);
        assert dataDefinition.getDataType().equals(MySQLDataType.INT);
    }

    private ValidateResult canParseInt(String value) {
        if (value == null) {
            return ValidateResult.ok();
        }
        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (Exception e) {
            return new ValidateResult(false, value + "不能转换成Int");
        }
        if (!value.equals(intValue + "")) {
            return new ValidateResult(false, value + "转换成Int后的值是" + intValue + ",前后不一致");
        }
        return ValidateResult.ok();
    }
}
