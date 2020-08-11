package com.wuda.foundation.commons.property;

public enum BuiltinPropertyKeyType implements PropertyKeyType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPropertyKeyType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static {
        BuiltinPropertyKeyType[] types = BuiltinPropertyKeyType.values();
        for (BuiltinPropertyKeyType type : types) {
            type.register();
        }
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
