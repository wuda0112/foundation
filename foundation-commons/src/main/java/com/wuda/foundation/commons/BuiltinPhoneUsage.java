package com.wuda.foundation.commons;

public enum BuiltinPhoneUsage implements PhoneUsage {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPhoneUsage(int code, String description) {
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
