package com.wuda.foundation.core.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限分配,group by {@link Target}.
 *
 * @author wuda
 * @since 1.0.3
 */
public class PermissionAssignmentGroupByTarget {

    private Map<Target, List<DescribePermissionAssignment>> groupByTargetMap = new HashMap<>();

    /**
     * 添加到组中.
     *
     * @param permissionAssignment permission assignment
     */
    public void add(DescribePermissionAssignment permissionAssignment) {
        Target groupKey = permissionAssignment.getTarget();
        List<DescribePermissionAssignment> permissionAssignments = groupByTargetMap.computeIfAbsent(groupKey, k -> new ArrayList<>());
        permissionAssignments.add(permissionAssignment);
    }

    /**
     * 获取permission assignment.
     *
     * @param target target
     * @return permission assignment
     */
    public List<DescribePermissionAssignment> getPermissionAssignments(Target target) {
        return groupByTargetMap.get(target);
    }
}
