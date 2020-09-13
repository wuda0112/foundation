package com.wuda.foundation.lang.identify;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinIdentifierType implements IdentifierType {
    /**
     * VIRTUAL.
     */
    VIRTUAL(0, "表示不存在的,通常用于虚拟数据"),
    /**
     * 表示item表.
     */
    TABLE_ITEM(1, "表示item表"),
    /**
     * 表示store表.
     */
    TABLE_STORE(2, "表示store表"),
    ;

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
