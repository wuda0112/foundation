package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 在权限体系中,Subject是拥有权限的主体对象,比如用户,用户组等等.
 *
 * @author wuda
 * @see FlatPermissionTarget
 * @see HierarchicalPermissionSubject
 */
public interface PermissionSubject {

    /**
     * 获取该Subject的唯一标记
     *
     * @return 唯一标记
     */
    LongIdentifier getIdentifier();

    /**
     * get name.
     *
     * @return name
     */
    String getName();

    /**
     * get description.
     *
     * @return description
     */
    String getDescription();
}
