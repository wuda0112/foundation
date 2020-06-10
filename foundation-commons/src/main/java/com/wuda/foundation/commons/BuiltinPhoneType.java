package com.wuda.foundation.commons;

public enum BuiltinPhoneType implements PhoneType {

    ZERO(0, "手机"),
    ONE(1, "固话");

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
