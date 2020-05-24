package com.wuda.foundation.commons;

import com.wuda.foundation.lang.EmailUsage;

public enum BuiltinEmailUsage implements EmailUsage {

    FOR_LOGIN(0, "用于登录");

    private int usage;
    private String desc;

    BuiltinEmailUsage(int usage, String desc) {
        this.usage = usage;
        this.desc = desc;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
