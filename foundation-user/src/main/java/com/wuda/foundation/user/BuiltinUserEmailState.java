package com.wuda.foundation.user;

/**
 * 内置state.
 *
 * @author wuda
 */
public enum BuiltinUserEmailState implements UserEmailState {

    ZERO(0, "初始化");

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
