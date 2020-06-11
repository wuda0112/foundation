package com.wuda.foundation.user;

/**
 * 内置的用户状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinUserState implements UserState {

    ZERO(0, "初始化");

    private int code;
    private String description;

    BuiltinUserState(int code, String description) {
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
