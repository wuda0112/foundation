package com.wuda.foundation.core.security.menu;

import com.wuda.foundation.core.commons.DescribeMenuNode;
import com.wuda.foundation.core.commons.Menu;
import com.wuda.foundation.core.security.Action;
import com.wuda.foundation.core.security.AllowOrDeny;
import com.wuda.foundation.core.security.MergedPermissionAssignment;
import com.wuda.foundation.lang.tree.TreeUtils;
import com.wuda.foundation.lang.utils.MyCollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 菜单权限工具类.
 *
 * @author wuda
 * @since 1.0.3
 */
public class MenuPermissionUtils {

    /**
     * 不需要实例化.
     */
    private MenuPermissionUtils() {

    }

    /**
     * 给一个菜单实体,将分配了权限的菜单项设置为可见.
     * 想象一下,在火锅店吃火锅时,服务员会给你一个菜单,你想吃哪些,就在后面打钩,这个方法的作用也是差不多的.
     *
     * @param menu                  菜单
     * @param permissionAssignments 分配的权限
     */
    public static void applyPermission(Menu menu, List<MergedPermissionAssignment> permissionAssignments) {
        long rootId = menu.getRoot().getId();
        // 初始化所有菜单项都不可见(没有权限)
        TreeUtils.depthFirstTraverse(menu, rootId, (menuNode -> menuNode.setVisibility(false)));
        if (permissionAssignments == null || permissionAssignments.isEmpty()) {
            return;
        }
        Map<Action, MergedPermissionAssignment> byActionMap = MyCollectionUtils.toMap(permissionAssignments, MergedPermissionAssignment::getAction);
        TreeUtils.depthFirstTraverse(menu, rootId, (menuNode -> {
            Action action = new Action(menuNode.getId(), menuNode.getType());
            MergedPermissionAssignment permissionAssignment = byActionMap.get(action);
            Boolean visibility = null;
            if (permissionAssignment != null) {
                // 如果为这个节点设置了权限,则使用这个节点自己的
                visibility = AllowOrDeny.allow(permissionAssignment.getAllowOrDeny());
            } else {
                // 如果没有为这个节点设置权限,则继承父级的
                DescribeMenuNode parent = menu.getParent(menuNode.getId());
                if (parent != null) {
                    // root节点就没有parent,因此需要非空判断
                    visibility = parent.isVisibility();
                }
            }
            if (visibility != null) {
                menuNode.setVisibility(visibility);
            }
        }));
    }
}
