package com.wuda.foundation.commons;

/**
 * 内置email state.
 *
 * @author wuda
 */
public enum BuiltinEmailState implements EmailState {

    ZERO(0, "初始化状态,刚添加到系统中");

    private int code;
    private String description;

    BuiltinEmailState(int code, String description) {
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
