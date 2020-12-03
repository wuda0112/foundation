package com.wuda.foundation.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
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
     * allowOrDeny.
     */
    private AllowOrDeny allowOrDeny;

    /**
     * 是否分配了整个{@link Target},可以是allow,也可以是deny.比如文件A分配给了用户A,并且没有指定任何文件的操作,
     * 则表示把整个文件A都分配给了用户A,所表示的意思就是用户A拥有了文件A的所有操作.
     *
     * @param assignment assignment记录
     * @return <code>true</code>-如果整个{@link Target}分配给了{@link Subject}
     */
    public static boolean assignWholeTarget(DescribePermissionAssignment assignment) {
        return Action.isVirtual(assignment.action);
    }

    /**
     * 合并.每个{@link Subject}对给定的{@link Target}所能执行的{@link Action}的分配是多种多样的,并且有可能会重复,
     * 比如一开始给用户A分配了文件A的read/write权限,过一段时间后又分配了delete/write权限,则合并后,该用户拥有文件的read/write/delete权限(write重复,合并后只剩一个).
     *
     * @param assignments 权限分配集合,可能有重叠,这些分配信息只能属于同一个{@link Subject},如果不是会抛出异常
     * @return 合并后的权限列表
     */
    public static List<MergedPermissionAssignment> sameSubjectMerge(List<DescribePermissionAssignment> assignments) {
        if (assignments == null || assignments.isEmpty()) {
            return null;
        }
        // 所有的分配都是给这个subject
        Subject subject = checkSubjectUniqueAndReturn(assignments);

        List<MergedPermissionAssignment> list = new ArrayList<>();
        Map<Target, List<DescribePermissionAssignment>> groupByTargetMap = assignments.stream().collect(Collectors.groupingBy(DescribePermissionAssignment::getTarget));
        Set<Map.Entry<Target, List<DescribePermissionAssignment>>> entrySet = groupByTargetMap.entrySet();
        for (Map.Entry<Target, List<DescribePermissionAssignment>> entry : entrySet) {
            Target target = entry.getKey();
            List<DescribePermissionAssignment> assignmentList = entry.getValue();

            Map<Action, DescribePermissionAssignment> byActionMap = new HashMap<>();
            for (DescribePermissionAssignment assignment : assignmentList) {
                Action action = assignment.action;
                AllowOrDeny allowOrDeny = assignment.allowOrDeny;
                if (!assignWholeTarget(assignment)) {
                    DescribePermissionAssignment wholeTargetAssignment = byActionMap.get(Action.virtual());
                    if (wholeTargetAssignment != null) {
                        if (allowOrDeny == wholeTargetAssignment.allowOrDeny) {
                            // 把整个target都分配给了subject,这时该target上allow or deny和target保持一致的具体action自然也被包含了,
                            // 因此,这个action可以被"合并"了.
                            // 比如文件A分配给了用户A,并且是deny,如果文件A的read操作也分配给了
                            // 用户A,并且也是deny,那么该read操作就可以被覆盖,因为整个文件都分配了,自然就包含这个文件上的所有Action
                            // (allow和deny又是一致的).
                            continue;
                        }
                    }
                }
                DescribePermissionAssignment previous = byActionMap.get(action);
                if (previous != null) {
                    // 同一个action的分配记录有多条
                    if (AllowOrDeny.deny(allowOrDeny)) {
                        // 同时存在allow和deny,则使用deny覆盖allow,就好像同时存在允许删除文件和拒绝删除文件的分配记录,
                        // 则以拒绝删除为最终的权限
                        byActionMap.put(action, assignment);
                    }
                } else {
                    byActionMap.put(action, assignment);
                }

            }
            MergedPermissionAssignment mergedPermissionAssignment = new MergedPermissionAssignment(subject, target, byActionMap.values());
            list.add(mergedPermissionAssignment);
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
