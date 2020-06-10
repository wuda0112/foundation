package com.wuda.foundation.user;

import com.wuda.foundation.lang.PhoneState;

/**
 * 内置user phone state.
 *
 * @author wuda
 */
public enum BuiltinUserPhoneState implements UserPhoneState {

    ZERO(0, "正常用于登录");

    private int code;
    private String description;

    BuiltinUserPhoneState(int code, String description) {
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
