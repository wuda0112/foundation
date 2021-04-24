package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 在权限体系中,Target是权限作用的目标对象,比文件夹,文件等等.
 *
 * @author wuda
 * @see FlatPermissionTarget
 * @see HierarchicalPermissionTarget
 */
public interface PermissionTarget {

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
