package com.wuda.foundation.security;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将多个subject的permission合并.
 *
 * @author wuda
 * @since 1.0.3
 */
public class MultiSubjectPermissionAssignmentMerger {

    /**
     * 将多个subject的permission合并.
     *
     * @param multiple 多个subject,以及分配给他们的permission.
     * @return 合并后的permission.pair key是permission target id, pair value是permission action id.
     */
    public Set<ImmutablePair<Long, Long>> merge(List<OneSubjectPermissionAssignment> multiple) {
        if (multiple == null || multiple.isEmpty()) {
            return null;
        }
        OneSubjectPermissionAssignmentMerger oneSubjectPermissionAssignmentMerger = new OneSubjectPermissionAssignmentMerger();
        Set<ImmutablePair<Long, Long>> targetActionPairSet = new HashSet<>();
        for (OneSubjectPermissionAssignment oneSubjectPermissionAssignment : multiple) {
            oneSubjectPermissionAssignment = oneSubjectPermissionAssignmentMerger.merge(oneSubjectPermissionAssignment);
            List<DescribePermissionAssignment> list = oneSubjectPermissionAssignment.getPermissionAssignments();
            if (list == null || list.isEmpty()) {
                continue;
            }
            for (DescribePermissionAssignment describePermissionAssignment : list) {
                ImmutablePair<Long, Long> assignment = new ImmutablePair<>(describePermissionAssignment.getTargetId(), describePermissionAssignment.getActionId());
                // 由于是set,因此重复的就会合并掉
                targetActionPairSet.add(assignment);
            }
        }
        return targetActionPairSet;
    }
}
