package com.wuda.foundation.security;

import java.util.Set;

/**
 * 一个permission由一个{@link PermissionTarget}和多个{@link PermissionAction}组成.
 *
 * @author wuda
 * @since 1.0.0
 */
public class Permission {

    private PermissionTarget target;
    private Set<PermissionAction> actions;

}
