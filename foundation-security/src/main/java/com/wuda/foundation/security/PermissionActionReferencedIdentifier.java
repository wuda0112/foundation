package com.wuda.foundation.security;

import com.wuda.foundation.lang.Identifier;

/**
 * target关联的外部对象的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionActionReferencedIdentifier extends Identifier<Long> {

    @Override
    PermissionActionReferencedType getType();

}
