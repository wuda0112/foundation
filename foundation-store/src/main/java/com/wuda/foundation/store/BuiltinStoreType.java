package com.wuda.foundation.store;

/**
 * 内置的店铺类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinStoreType implements StoreType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinStoreType(int code, String description) {
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
