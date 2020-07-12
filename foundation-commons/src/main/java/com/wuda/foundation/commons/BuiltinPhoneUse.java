package com.wuda.foundation.commons;

public enum BuiltinPhoneUse implements PhoneUse {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPhoneUse(int code, String description) {
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
