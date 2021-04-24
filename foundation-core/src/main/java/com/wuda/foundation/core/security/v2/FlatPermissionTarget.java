package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 在权限体系中,Target是权限作用的目标对象,比文件夹,文件等等.
 * 这些Target有些是没有层次结构的,而有些是有层次结构的,比如文件系统.
 * 这里用于表示没有层次结构的Target.
 *
 * @author wuda
 */
public abstract class FlatPermissionTarget extends AbstractPermissionTarget {

    /**
     * 构造实例.
     *
     * @param identifier  target的唯一标记
     * @param name        name
     * @param description description
     */
    public FlatPermissionTarget(LongIdentifier identifier, String name, String description) {
        super(identifier, name, description);
    }
}
