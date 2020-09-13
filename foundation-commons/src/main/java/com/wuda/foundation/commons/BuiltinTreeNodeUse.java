package com.wuda.foundation.commons;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinTreeNodeUse implements TreeNodeUse {
    /**
     * VIRTUAL.
     */
    VIRTUAL((byte)0, "表示不存在的,通常用于虚拟数据"),
    /**
     * 用于表示item_category
     */
    USED_FOR_ITEM_CATEGORY((byte) 1, "用于表示item_category"),
    ;

    private Byte code;
    private String description;

    BuiltinTreeNodeUse(Byte code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Byte getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
