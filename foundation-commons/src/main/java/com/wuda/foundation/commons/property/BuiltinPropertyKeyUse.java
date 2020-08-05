package com.wuda.foundation.commons.property;

public enum BuiltinPropertyKeyUse implements PropertyKeyUse {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPropertyKeyUse(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
