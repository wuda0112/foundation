package com.wuda.foundation.commons;

public enum BuiltinEmailUse implements EmailUse {

    ZERO(0, "MOCK"),
    FOR_SIGN_IN(1, "用于登录");

    private int code;
    private String description;

    BuiltinEmailUse(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
