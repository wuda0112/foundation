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

    /**
     * 表示item_category表.
     */
    ITEM_CATEGORY(3, "表示item_category表"),

    /**
     * 表示permission_role表.
     */
    PERMISSION_ROLE(4, "表示permission_role表"),
    /**
     * 表示menu_item表.
     */
    MENU_ITEM(5, "表示menu_item表"),
    /**
     * 表示menu_item_category表.
     */
    MENU_ITEM_CATEGORY(6, "表示menu_item_category表");

    /**
     * unique code.
     */
    protected int code;
    /**
     * 描述信息.
     */
    protected String description;

    /**
     * 构造实例,并且将自己注册到{@link IdentifierTypeRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    BuiltinIdentifierType(int code, String description) {
        this.code = code;
        this.description = description;
        IdentifierTypeRegistry.defaultRegistry.register(this);
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
