package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示合并后的permission assignment.
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
     * action
     */
    private Action action;
    /**
     * allow or deny.
     */
    private PermissionEffect permissionEffect;

    /**
     * 从{@link DescribePermissionAssignment}复制.
     *
     * @param permissionAssignment {@link DescribePermissionAssignment}
     * @return {@link MergedPermissionAssignment}
     */
    public static MergedPermissionAssignment copyFrom(DescribePermissionAssignment permissionAssignment) {
        return new MergedPermissionAssignment(permissionAssignment.getSubject(),
                permissionAssignment.getTarget(), permissionAssignment.getAction(), permissionAssignment.getEffect());
    }

    /**
     * 从{@link DescribePermissionAssignment}复制.
     *
     * @param permissionAssignments {@link DescribePermissionAssignment}
     * @return {@link MergedPermissionAssignment}
     */
    public static List<MergedPermissionAssignment> copyFrom(List<DescribePermissionAssignment> permissionAssignments) {
        if (permissionAssignments == null || permissionAssignments.isEmpty()) {
            return null;
        }
        List<MergedPermissionAssignment> list = new ArrayList<>(permissionAssignments.size());
        for (DescribePermissionAssignment permissionAssignment : permissionAssignments) {
            MergedPermissionAssignment mergedPermissionAssignment = copyFrom(permissionAssignment);
            list.add(mergedPermissionAssignment);
        }
        return list;
    }

}
