package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

/**
 * 以{@link Subject}和{@link Target}为核心,合并permission assignment.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@ToString
public class MergedPermissionAssignment {
    /**
     * 该权限的主体.
     */
    private Subject subject;
    /**
     * 为主体分配的对象.
     */
    private Target target;
    /**
     * 围绕同一个{@link Subject}和{@link Target}的权限分配.
     */
    private Collection<DescribePermissionAssignment> assignments;
}
