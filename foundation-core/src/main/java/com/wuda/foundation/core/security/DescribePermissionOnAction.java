package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class DescribePermissionOnAction {
    /**
     * 该权限的主体.
     */
    private Subject subject;
    /**
     * 为主体分配的对象.
     */
    private Target target;
    /**
     * 为主体分配的目标对象的操作.
     */
    private Set<Action> actions;
    /**
     * 对这些action执行的inclusionOrExclusion.
     */
    private InclusionOrExclusion inclusionOrExclusion;
}
