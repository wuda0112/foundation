package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * describe permission assignment.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DescribePermissionAssignment {

    /**
     * 数据库的ID.
     */
    private Long id;

    /**
     * subject.
     */
    private Subject subject;
    /**
     * target.
     */
    private Target target;
    /**
     * action.
     */
    private Action action;
    /**
     * inclusionOrExclusion.
     */
    private InclusionOrExclusion inclusionOrExclusion;

    /**
     * 合并.每个{@link Subject}对给定的{@link Target}所能执行的{@link Action}的分配是多种多样的,并且有可能会重复,
     * 比如一开始给用户A分配了文件A的read/write权限,过一段时间后又分配了delete权限,则合并后,该用户拥有文件的read/write/delete权限.
     *
     * @param assignments 权限分配集合,可能有重叠,这些分配信息只能属于同一个{@link Subject},如果不是会抛出异常
     * @return 合并后的权限列表
     */
    public static List<DescribePermission> sameSubjectCalculate(List<DescribePermissionAssignment> assignments) {
        if (assignments == null || assignments.isEmpty()) {
            return null;
        }
        List<DescribePermission> list = new ArrayList<>();
        Subject subject = checkSubjectUniqueAndReturn(assignments);
        Map<Target, List<DescribePermissionAssignment>> groupByTargetMap = assignments.stream().collect(Collectors.groupingBy(DescribePermissionAssignment::getTarget));
        Set<Map.Entry<Target, List<DescribePermissionAssignment>>> entrySet = groupByTargetMap.entrySet();
        for (Map.Entry<Target, List<DescribePermissionAssignment>> entry : entrySet) {
            Target target = entry.getKey();
            List<DescribePermissionAssignment> assignmentList = entry.getValue();

            DescribePermissionOnTarget describePermissionOnTarget = null;

            Set<Action> actions = new HashSet<>();
            InclusionOrExclusion inclusionOrExclusionOfAction = null;
            for (DescribePermissionAssignment assignment : assignmentList) {
                if (assignment.getAction().equals(Action.fake())) {
                    if (describePermissionOnTarget == null) {
                        describePermissionOnTarget = new DescribePermissionOnTarget(subject, target, assignment.getInclusionOrExclusion());
                    } else if (!describePermissionOnTarget.getInclusionOrExclusion().equals(assignment.inclusionOrExclusion)) {
                        throw new IllegalStateException("为subject分配的同一个target,InclusionOrExclusion应该只有一种");
                    }
                } else {
                    if (inclusionOrExclusionOfAction == null) {
                        inclusionOrExclusionOfAction = assignment.inclusionOrExclusion;
                    } else if (!inclusionOrExclusionOfAction.equals(assignment.inclusionOrExclusion)) {
                        throw new IllegalStateException("为subject分配的同一个target的action,InclusionOrExclusion应该只有一种");
                    }
                    actions.add(assignment.getAction());
                }
            }
            DescribePermissionOnAction describePermissionOnAction = new DescribePermissionOnAction(subject, target, actions, inclusionOrExclusionOfAction);
            DescribePermission describePermission = new DescribePermission(subject, target, describePermissionOnTarget, describePermissionOnAction);
            list.add(describePermission);
        }
        return list;
    }


    private static Subject checkSubjectUniqueAndReturn(List<DescribePermissionAssignment> assignments) {
        Subject subject = null;
        for (DescribePermissionAssignment assignment : assignments) {
            if (subject == null) {
                subject = assignment.getSubject();
            } else {
                if (!assignment.getSubject().equals(subject)) {
                    throw new IllegalArgumentException("不是同一个subject的权限分配信息");
                }
            }
        }
        return subject;
    }

}
