package com.wuda.foundation.core.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限分配,group by {@link Subject}和{@link Target}.
 *
 * @author wuda
 * @since 1.0.3
 */
public class PermissionAssignmentGroupBySubjectAndTarget {

    private Map<String, List<DescribePermissionAssignment>> groupBySubjectAndTargetMap = new HashMap<>();

    /**
     * 添加到组中.
     *
     * @param permissionAssignment permission assignment
     */
    public void add(DescribePermissionAssignment permissionAssignment) {
        Subject subject = permissionAssignment.getSubject();
        Target target = permissionAssignment.getTarget();
        String groupKey = getGroupKey(subject, target);
        List<DescribePermissionAssignment> permissionAssignments = groupBySubjectAndTargetMap.computeIfAbsent(groupKey, k -> new ArrayList<>());
        permissionAssignments.add(permissionAssignment);
    }

    /**
     * 获取permission assignment.
     *
     * @param subject subject
     * @param target  target
     * @return permission assignment
     */
    public List<DescribePermissionAssignment> getPermissionAssignments(Subject subject, Target target) {
        String groupKey = getGroupKey(subject, target);
        return groupBySubjectAndTargetMap.get(groupKey);
    }

    private String getGroupKey(Subject subject, Target target) {
        return subject.toString() + ":" + target.toString();
    }
}
