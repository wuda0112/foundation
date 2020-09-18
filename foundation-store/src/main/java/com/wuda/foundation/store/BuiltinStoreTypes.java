package com.wuda.foundation.store;

/**
 * 内建的identifier type.
 *
 * @author wuda
 * @since 1.0.0
 */
public class BuiltinStoreTypes {

    /**
     * virtual.
     */
    public final static StoreType VIRTUAL = new StoreType((byte) 0, "virtual");

    /**
     * 不需要实例化.
     */
    private BuiltinStoreTypes() {

    }
}
