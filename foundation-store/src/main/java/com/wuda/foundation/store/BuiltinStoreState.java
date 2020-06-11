package com.wuda.foundation.store;

/**
 * 内置的店铺状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinStoreState implements StoreState {

    ZERO(0, "初始化");

    private int code;
    private String description;

    BuiltinStoreState(int code, String description) {
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
