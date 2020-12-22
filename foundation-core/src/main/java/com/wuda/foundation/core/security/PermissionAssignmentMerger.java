package com.wuda.foundation.core.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限分配是多种多样的,并且有可能会重复,比如一开始给用户A分配了文件A的read/write权限,
 * 过一段时间后又分配了delete/write权限,则合并后,该用户拥有文件的read/write/delete权限(write重复,合并后只剩一个).
 *
 * @author wuda
 * @since 1.0.3
 */
public class PermissionAssignmentMerger {

    /**
     * 合并权限.
     *
     * @param permissionAssignments 分配的权限集合,集合中所有的{@link Target}必须是{@link TargetComparator}所支持的类型,
     *                              并且{@link Action}必须是{@link ActionComparator}所支持的类型,如果不是会抛出异常
     * @param targetComparator      target comparator
     * @param actionComparator      action comparator
     * @return 合并后的
     */
    public List<MergedPermissionAssignment> merge(List<DescribePermissionAssignment> permissionAssignments,
                                                  TargetComparator targetComparator,
                                                  ActionComparator actionComparator) {
        if (permissionAssignments == null || permissionAssignments.isEmpty()) {
            return null;
        }
        // 用于保存合并后最终留下来的
        List<DescribePermissionAssignment> selectedList = new ArrayList<>(permissionAssignments.size());
        List<DescribePermissionAssignment> candidateList = new ArrayList<>(permissionAssignments);
        Set<Integer> removedIndexSet = new HashSet<>(candidateList.size());

        int competitiveIndex = 0;
        while (candidateList.size() > 1) {
            DescribePermissionAssignment competitive = candidateList.get(competitiveIndex);
            checkCompareSupport(competitive, targetComparator, actionComparator);
            for (int index = 1; index < candidateList.size(); index++) {
                DescribePermissionAssignment next = candidateList.get(index);
                checkCompareSupport(next, targetComparator, actionComparator);
                Target nextTarget = next.getTarget();
                Action nextAction = next.getAction();
                boolean targetComparable = targetComparator.comparable(competitive.getTarget(), nextTarget);
                if (!targetComparable) {
                    continue;
                }
                boolean actionComparable = actionComparator.comparable(competitive.getAction(), nextAction);
                if (!actionComparable) {
                    continue;
                }
                AllowOrDeny nextAllowOrDeny = next.getAllowOrDeny();
                if (competitive.getAllowOrDeny().equals(nextAllowOrDeny)) {
                    if (targetComparator.impliesOrEquals(competitive.getTarget(), nextTarget)
                            && actionComparator.impliesOrEquals(competitive.getAction(), nextAction)) {
                        removedIndexSet.add(index);
                    } else if (targetComparator.impliesOrEquals(nextTarget, competitive.getTarget())
                            && actionComparator.impliesOrEquals(nextAction, competitive.getAction())) {
                        removedIndexSet.add(index - 1);
                        competitive = next;
                        competitiveIndex = index;
                    }
                } else {
                    if (targetComparator.equals(competitive.getTarget(), nextTarget)
                            && actionComparator.equals(competitive.getAction(), nextAction)) {
                        // 对同一个target的同一个action既允许又禁止,则以禁止作为最终结果,
                        // 就好像用户对于文件就允许删除,又禁止删除,则最终用户对该文件的权限是禁止删除
                        if (competitive.getAllowOrDeny().equals(AllowOrDeny.DENY)) {
                            removedIndexSet.add(index);
                        } else {
                            removedIndexSet.add(index - 1);
                            competitive = next;
                            competitiveIndex = index;
                        }
                    }
                }
            }
            selectedList.add(competitive);
            // 被选择了就要从候选中移除
            removedIndexSet.add(competitiveIndex);
            candidateList = remove(candidateList, removedIndexSet);
        }
        if (candidateList.size() == 1) {
            selectedList.addAll(candidateList);
        }
        return MergedPermissionAssignment.copyFrom(selectedList);
    }

    private List<DescribePermissionAssignment> remove(List<DescribePermissionAssignment> list, Set<Integer> removedIndexSet) {
        if (removedIndexSet == null || removedIndexSet.isEmpty()
                || list == null || list.isEmpty()) {
            return list;
        }
        List<DescribePermissionAssignment> newList = new ArrayList<>(list.size());
        for (int index = 0; index < list.size(); index++) {
            if (!removedIndexSet.contains(index)) {
                newList.add(list.get(index));
            }
        }
        return newList;
    }

    private void checkCompareSupport(DescribePermissionAssignment permissionAssignment,
                                     TargetComparator targetComparator,
                                     ActionComparator actionComparator) {
        if (!targetComparator.support(permissionAssignment.getTarget().getType())
                || !actionComparator.support(permissionAssignment.getAction().getType())) {
            throw new UnsupportedOperationException();
        }
    }
}
