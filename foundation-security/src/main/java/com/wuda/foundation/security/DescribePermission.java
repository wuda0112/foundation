package com.wuda.foundation.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 描述{@link Subject}所拥有的{@link Target}和{@link Action}.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@ToString
public class DescribePermission {
    /**
     * 该权限的主体.
     */
    private Subject subject;
    /**
     * 为主体分配的对象.
     */
    private Target target;

    private DescribePermissionOnTarget describeTarget;
    private DescribePermissionOnAction describeAction;

}
