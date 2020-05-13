package com.wuda.foundation.user;

/**
 * 内置的用户账号状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinUserAccountStatus implements UserAccountStatus {

    ZERO(0, "系统内建的一个状态值,业务不可使用,就好像系统保留的关键字一样,用于系统用户初始化");
    private int status;
    private String desc;

    BuiltinUserAccountStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    @Override
    public int get() {
        return status;
    }
}
