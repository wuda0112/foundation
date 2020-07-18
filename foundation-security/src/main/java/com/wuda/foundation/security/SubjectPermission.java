package com.wuda.foundation.security;

import java.util.Set;

/**
 * 表示一个{@link Subject}对{@link DescribePermissionTarget}可以执行{@link DescribePermissionAction}.
 *
 * @param <T> subject的唯一标记的数据类型
 * @author wuda
 * @since 1.0.0
 */
public class SubjectPermission<T> {

    private Subject<T> subject;
    private DescribePermissionTarget target;
    private Set<DescribePermissionAction> actions;

}
