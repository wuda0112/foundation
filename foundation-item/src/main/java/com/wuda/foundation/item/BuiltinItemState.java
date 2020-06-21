package com.wuda.foundation.item;

/**
 * 内置的item状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinItemState implements ItemState {

    ZERO(0, "初始化");

    private int code;
    private String description;

    BuiltinItemState(int code, String description) {
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
