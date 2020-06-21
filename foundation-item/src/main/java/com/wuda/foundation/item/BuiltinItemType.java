package com.wuda.foundation.item;

/**
 * 内置的item类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinItemType implements ItemType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinItemType(int code, String description) {
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
