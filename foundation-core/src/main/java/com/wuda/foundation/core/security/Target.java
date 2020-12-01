package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 代表权限体系中分配的目标对象.
 *
 * @author wuda
 * @since 1.0.3
 */
public class Target extends LongIdentifier {

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public Target(Long value, IdentifierType type) {
        super(value, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Target) {
            Target other = (Target) obj;
            return this.getType().getCode() == other.getType().getCode() && this.getValue().equals(other.getValue());
        }
        return false;
    }
}
