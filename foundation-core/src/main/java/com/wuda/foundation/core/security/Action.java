package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 代表权限体系中分配的操作.
 *
 * @author wuda
 * @since 1.0.3
 */
public class Action extends LongIdentifier {

    /**
     * virtual.
     */
    private static Action VIRTUAL = new Action(Constant.NOT_EXISTS_ID, BuiltinIdentifierType.VIRTUAL);

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public Action(Long value, IdentifierType type) {
        super(value, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Action) {
            Action other = (Action) obj;
            return this.getType().getCode() == other.getType().getCode() && this.getValue().equals(other.getValue());
        }
        return false;
    }

    /**
     * 一个不存在的，假的identifier.
     *
     * @return virtual
     */
    public static Action virtual() {
        return VIRTUAL;
    }

    /**
     * 是否{@link #virtual()}所代表的action.
     *
     * @param action action
     * @return <code>true</code>-如果是
     */
    public static boolean isVirtual(Action action) {
        if (action == null) {
            return false;
        }
        return VIRTUAL.equals(action);
    }
}
