package com.wuda.foundation.user;

/**
 * 内置user phone state.
 *
 * @author wuda
 */
public enum BuiltinUserEmailState implements UserEmailState {

    ZERO(0, "正常用于登录");

    private int code;
    private String description;

    BuiltinUserEmailState(int code, String description) {
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
