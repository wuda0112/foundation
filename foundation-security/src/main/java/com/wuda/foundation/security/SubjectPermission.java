package com.wuda.foundation.security;

import java.util.Set;

/**
 * 表示一个{@link Subject}对{@link DescribePermissionTarget}可以执行{@link DescribePermissionAction}.
 *
 * @author wuda
 * @since 1.0.0
 */
public class SubjectPermission {

    private Subject subject;
    private DescribePermissionTarget target;
    private Set<DescribePermissionAction> actions;

}
