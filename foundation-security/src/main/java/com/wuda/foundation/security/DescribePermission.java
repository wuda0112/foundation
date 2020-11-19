package com.wuda.foundation.security;

import com.wuda.foundation.lang.utils.MyCollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参考{@link java.io.FilePermission},permission只包含target和action,不包含subject.
 * 具体可以查看{@link java.io.FilePermission}的定义.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@ToString
public class DescribePermission {

    private DescribePermissionTarget target;
    private List<DescribePermissionAction> actions;

    /**
     * 合并permission.每个{@link Subject}的permission的来源是多种多样的,并且有可能会重复,
     * 比如{@link Subject}拥有角色A和角色B这两个角色,角色A有文件的read/write权限,
     * 角色B有文件的write/delete权限,则合并后,该{@link Subject}拥有文件的read/write/delete权限.
     *
     * @param permissions 所有的权限,可能有重叠
     * @return 合并后的权限列表
     */
    public static List<DescribePermission> merge(List<DescribePermission> permissions) {
        if (permissions == null || permissions.isEmpty() || permissions.size() == 1) {
            return permissions;
        }
        List<DescribePermissionTarget> allTargets = permissions.stream().map(DescribePermission::getTarget).collect(Collectors.toList());
        List<DescribePermissionAction> allActions = new ArrayList<>();
        for (DescribePermission permission : permissions) {
            if (permission.getActions() != null && !permission.getActions().isEmpty()) {
                allActions.addAll(permission.getActions());
            }
        }
        // 这个过程就相当于是合并了target,因为相同Id的target只有一个了
        Map<Long, DescribePermissionTarget> targetByIdMap = MyCollectionUtils.toMap(allTargets, DescribePermissionTarget::getId);

        // 这个过程就相当于是合并了action,因为相同Id的action只有一个了
        Map<Long, DescribePermissionAction> actionByIdMap = MyCollectionUtils.toMap(allActions, DescribePermissionAction::getId);

        // key: target id; value: list of action
        Map<Long, List<DescribePermissionAction>> actionsByTargetIdMap = actionByIdMap.values().stream().collect(Collectors.groupingBy(DescribePermissionAction::getPermissionTargetId));

        List<DescribePermission> mergedPermissions = new ArrayList<>();

        Collection<DescribePermissionTarget> mergedTargets = targetByIdMap.values();
        for (DescribePermissionTarget target : mergedTargets) {
            List<DescribePermissionAction> actionList = actionsByTargetIdMap.get(target.getId());
            DescribePermission permission = new DescribePermission(target, actionList);
            mergedPermissions.add(permission);
        }
        return mergedPermissions;
    }

}
