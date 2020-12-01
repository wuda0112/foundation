package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DescribePermissionOnTarget {
    /**
     * 该权限的主体.
     */
    private Subject subject;
    /**
     * 为主体分配的对象.
     */
    private Target target;
    /**
     * 主体对于该对象说执行的inclusionOrExclusion.
     */
    private InclusionOrExclusion inclusionOrExclusion;
}
