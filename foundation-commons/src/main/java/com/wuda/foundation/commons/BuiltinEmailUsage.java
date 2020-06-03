package com.wuda.foundation.commons;

public enum BuiltinEmailUsage implements EmailUsage {

    ZERO(0, "用于登录");

    private int code;
    private String description;

    BuiltinEmailUsage(int code, String description) {
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
