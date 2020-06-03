package com.wuda.foundation.user;

/**
 * 内置的用户账号状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinUserAccountState implements UserAccountState {

    ZERO(0, "系统内建的一个状态值,业务不可使用,就好像系统保留的关键字一样,用于系统用户初始化");
    private int code;
    private String description;

    BuiltinUserAccountState(int code, String description) {
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
