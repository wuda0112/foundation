package com.wuda.foundation.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 同一个{@link Subject}可以分配多个permission,它们可能会重叠,
 * 有些时候分配的是{@link DescribePermissionTarget target},需要展开为更具体的{@link DescribePermissionAction action}.
 *
 * @author wuda
 * @since 1.0.3
 */
public class OneSubjectPermissionAssignmentMerger {


    /**
     * 数据库中保存的权限分配是一个"收缩"的,有重叠的,因此需要做一些计算,
     * 最后转换成最具体的{@link DescribePermissionAction}.
     *
     * @param original 原始的,没有经过处理的权限分配
     * @return 经过整理后, 具体到 {@link DescribePermissionAction}的权限
     */
    public OneSubjectPermissionAssignment merge(OneSubjectPermissionAssignment original) {
        if (original == null) {
            return original;
        }
        List<DescribePermissionAssignment> originalPermissionAssignments = original.getPermissionAssignments();
        if (originalPermissionAssignments == null || originalPermissionAssignments.isEmpty()) {
            return original;
        }
        OneSubjectPermissionAssignment newOneSubjectPermissionAssignment = new OneSubjectPermissionAssignment(original.getSubject());
        Set<Long> revokedActionIdSet = new HashSet<>();
        for (DescribePermissionAssignment permissionAssignment : originalPermissionAssignments) {
            if (SecurityModuleUtils.toWholeTarget(permissionAssignment)) {
                List<DescribePermissionAssignment> expandedList = expand(permissionAssignment, revokedActionIdSet);
                newOneSubjectPermissionAssignment.addAll(expandedList);
            } else {
                boolean revoke = permissionAssignment.getCommand() == PermissionAssignmentCommand.REVOKE;
                long actionId = permissionAssignment.getActionId();
                if (revoke) {
                    revokedActionIdSet.add(permissionAssignment.getActionId());
                } else if (!revokedActionIdSet.contains(actionId)) {
                    newOneSubjectPermissionAssignment.add(permissionAssignment);
                }
            }
        }
        return newOneSubjectPermissionAssignment;
    }

    /**
     * 展开{@link DescribePermissionTarget}为具体的{@link DescribePermissionAction}.
     *
     * @param permissionTargetId permission target id
     * @return 该permission target的所有action
     */
    private List<Long> expand(Long permissionTargetId) {
        return null;
    }

    /**
     * 展开{@link DescribePermissionTarget}为具体的{@link DescribePermissionAction}.
     *
     * @param assignTarget permission target
     * @return 该permission target的所有action
     */
    private List<DescribePermissionAssignment> expand(DescribePermissionAssignment assignTarget, Set<Long> revokedActionIdSet) {
        List<Long> permissionActionIds = expand(assignTarget.getTargetId());
        List<DescribePermissionAssignment> list = new ArrayList<>(permissionActionIds.size());
        for (Long permissionActionId : permissionActionIds) {
            if (!revokedActionIdSet.contains(permissionActionId)) {
                DescribePermissionAssignment describePermissionAssignment = new DescribePermissionAssignment(assignTarget.getId(), assignTarget.getSubject(), assignTarget.getTargetId(), permissionActionId, assignTarget.getCommand());
                list.add(describePermissionAssignment);
            }
        }
        return list;
    }
}
