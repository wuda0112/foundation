package com.wuda.foundation.lang.identify;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public class BuiltinIdentifierTypes {

    /**
     * VIRTUAL.
     */
    public final static IdentifierType VIRTUAL = new IdentifierTypeImpl(0, "表示不存在的,通常用于虚拟数据");
    /**
     * 表示item表.
     */
    public final static IdentifierType TABLE_ITEM = new IdentifierTypeImpl(1, "表示item表");
    /**
     * 表示store表.
     */
    public final static IdentifierType TABLE_STORE = new IdentifierTypeImpl(2, "表示store表");

    /**
     * 不需要实例化.
     */
    private BuiltinIdentifierTypes(){

    }
}
