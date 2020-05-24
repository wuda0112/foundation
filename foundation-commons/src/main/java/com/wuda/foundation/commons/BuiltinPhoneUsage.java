package com.wuda.foundation.commons;

import com.wuda.foundation.lang.PhoneUsage;

public enum BuiltinPhoneUsage implements PhoneUsage {

    FOR_LOGIN(0, "用于登录");

    private int usage;
    private String desc;

    BuiltinPhoneUsage(int usage, String desc) {
        this.usage = usage;
        this.desc = desc;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
