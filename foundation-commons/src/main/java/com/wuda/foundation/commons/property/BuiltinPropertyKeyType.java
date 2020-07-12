package com.wuda.foundation.commons.property;

import com.wuda.foundation.commons.EmailUse;

public enum BuiltinPropertyKeyType implements PropertyKeyType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPropertyKeyType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDescription(){
        return description;
    }
}
