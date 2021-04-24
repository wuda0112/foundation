package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.DescribePermissionAssignment;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 权限分配信息的集合.
 *
 * @author wuda
 */
public class PermissionAssignmentCollection extends AbstractCollection<DescribePermissionAssignment> {

    /**
     * 该Subject的权限分配信息.
     */
    protected List<DescribePermissionAssignment> permissionAssignments;

    /**
     * 构建实例,用于存储{@link DescribePermissionAssignment permission assignment}.
     *
     * @param initialCapacity 集合的初始容量
     */
    public PermissionAssignmentCollection(int initialCapacity) {
        permissionAssignments = new ArrayList<>(initialCapacity);
    }

    /**
     * 构建实例,用于存储{@link DescribePermissionAssignment permission assignment}.
     *
     * @param permissionAssignments 初始的权限分配信息
     */
    public PermissionAssignmentCollection(List<DescribePermissionAssignment> permissionAssignments) {
        this.permissionAssignments = new ArrayList<>(permissionAssignments);
    }

    /**
     * 获取当前Subject的权限分配信息.
     *
     * @return 当前Subject的权限分配信息
     */
    public List<DescribePermissionAssignment> getPermissionAssignments() {
        return permissionAssignments;
    }

    @Override
    public Iterator<DescribePermissionAssignment> iterator() {
        return permissionAssignments.iterator();
    }

    @Override
    public int size() {
        return permissionAssignments == null ? 0 : permissionAssignments.size();
    }
}
