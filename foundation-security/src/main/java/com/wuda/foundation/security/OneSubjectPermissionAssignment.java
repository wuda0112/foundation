package com.wuda.foundation.security;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有的permission都属于同一个{@link Subject}.
 *
 * @author wuda
 * @since 1.0.3
 */
public class OneSubjectPermissionAssignment {

    /**
     * 所有分配的permission都属于这一个subject.
     */
    private Subject subject;

    /**
     * 这些分配的permission都属于同一个{@link Subject}.
     */
    private List<DescribePermissionAssignment> permissionAssignments = new ArrayList<>();

    /**
     * 构建实例.
     *
     * @param subject 所有的permission都属于该subject
     */
    public OneSubjectPermissionAssignment(Subject subject) {
        this.subject = subject;
    }

    /**
     * the subject
     *
     * @return subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * 同一个subject的permission assignment
     *
     * @return 分配给同一个subject的permission
     */
    public List<DescribePermissionAssignment> getPermissionAssignments() {
        return permissionAssignments;
    }

    /**
     * 添加一个.
     *
     * @param permissionAssignment permission assignment
     */
    public void add(DescribePermissionAssignment permissionAssignment) {
        if (!permissionAssignment.getSubject().equals(subject)) {
            throw new IllegalArgumentException("不属于同一个subject");
        }
        permissionAssignments.add(permissionAssignment);
    }

    /**
     * 添加多个.
     *
     * @param permissionAssignments list of permission assignment
     */
    public void addAll(List<DescribePermissionAssignment> permissionAssignments) {
        if (permissionAssignments == null || permissionAssignments.isEmpty()) {
            return;
        }
        for (DescribePermissionAssignment permissionAssignment : permissionAssignments) {
            add(permissionAssignment);
        }
    }
}
