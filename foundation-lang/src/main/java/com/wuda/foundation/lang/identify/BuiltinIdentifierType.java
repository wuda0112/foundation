package com.wuda.foundation.lang.identify;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinIdentifierType implements IdentifierType {
    /**
     * 表示item表.
     */
    TABLE_ITEM(0, "表示item表");

    private int code;
    private String description;

    BuiltinIdentifierType(int code, String description) {
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
