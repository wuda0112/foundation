package com.wuda.foundation.commons;

public enum BuiltinPhoneType implements PhoneType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPhoneType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
