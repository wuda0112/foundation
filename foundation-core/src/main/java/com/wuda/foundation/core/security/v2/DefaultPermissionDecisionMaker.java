package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.lang.tree.Tree;
import com.wuda.foundation.lang.tree.TreeNode;
import com.wuda.foundation.lang.tree.TreeUtils;
import com.wuda.foundation.lang.utils.MyCollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 默认实现类.
 *
 * @author wuda
 */
public class DefaultPermissionDecisionMaker implements PermissionDecisionMaker {

    @Override
    public <T extends TreeNode<Long>> Tree<Long, HierarchicalPermission<T>> decide(Tree<Long, T> permissionTargetTree,
                                                                                   Function<T, HierarchicalPermission<T>> targetToPermissionMapper,
                                                                                   Tree<Long, HierarchicalPermissionAssignmentCollection> permissionAssignmentCollectionTree) {
        if (permissionTargetTree == null) {
            return null;
        }
        Tree<Long, HierarchicalPermission<T>> permissionTree = TreeUtils.copy(permissionTargetTree, targetToPermissionMapper);
        if (permissionAssignmentCollectionTree == null || permissionTree == null) {
            return permissionTree;
        }
        TreeUtils.depthFirstTraverse(permissionAssignmentCollectionTree, permissionAssignmentCollectionTree.getRoot().getId(), permissionAssignmentCollection -> {
            _applyPermissionAssignment2(permissionTree, permissionAssignmentCollection);
        });
        permissionExtendsHandle(permissionTree);
        return permissionTree;
    }

    @Override
    public <T extends TreeNode<Long>> Tree<Long, HierarchicalPermission<T>> decide(Tree<Long, T> permissionTargetTree,
                                                                                   Function<T, HierarchicalPermission<T>> targetToPermissionMapper,
                                                                                   PermissionAssignmentCollection permissionAssignmentCollection) {
        if (permissionTargetTree == null) {
            return null;
        }
        Tree<Long, HierarchicalPermission<T>> permissionTree = TreeUtils.copy(permissionTargetTree, targetToPermissionMapper);
        _applyPermissionAssignment2(permissionTree, permissionAssignmentCollection);
        permissionExtendsHandle(permissionTree);
        return permissionTree;
    }

    @Override
    public <T> List<FlatPermission<T>> decide(List<T> permissionTargets,
                                              Function<T, FlatPermission<T>> targetToPermissionMapper,
                                              Tree<Long, HierarchicalPermissionAssignmentCollection> permissionAssignmentTree) {
        List<FlatPermission<T>> flatPermissions = FlatPermission.newPermissions(permissionTargets, targetToPermissionMapper);
        if (permissionAssignmentTree == null) {
            return flatPermissions;
        }
        TreeUtils.depthFirstTraverse(permissionAssignmentTree, permissionAssignmentTree.getRoot().getId(), permissionAssignmentCollection -> {
            _applyPermissionAssignment5(flatPermissions, permissionAssignmentCollection);
        });
        return flatPermissions;
    }

    @Override
    public <T> List<FlatPermission<T>> decide(List<T> permissionTargets,
                                              Function<T, FlatPermission<T>> targetToPermissionMapper,
                                              PermissionAssignmentCollection permissionAssignmentCollection) {
        List<FlatPermission<T>> flatPermissions = FlatPermission.newPermissions(permissionTargets, targetToPermissionMapper);
        _applyPermissionAssignment5(flatPermissions, permissionAssignmentCollection);
        return flatPermissions;
    }

    @Override
    public <T extends TreeNode<Long>> void applyPermissionAssignment(Tree<Long, HierarchicalPermission<T>> permissionTree,
                                                                     PermissionAssignmentCollection permissionAssignmentCollection) {
        _applyPermissionAssignment2(permissionTree, permissionAssignmentCollection);
    }

    @Override
    public <T> void applyPermissionAssignment(List<FlatPermission<T>> permissions,
                                              PermissionAssignmentCollection permissionAssignmentCollection) {
        _applyPermissionAssignment5(permissions, permissionAssignmentCollection);
    }

    private <T extends TreeNode<Long>> void _applyPermissionAssignment0(Tree<Long, HierarchicalPermission<T>> permissionTree, DescribePermissionAssignment permissionAssignment) {
        long targetId = permissionAssignment.getTarget().getValue();
        HierarchicalPermission hierarchicalPermission = permissionTree.get(targetId);
        _applyPermissionAssignment4(hierarchicalPermission, permissionAssignment);
    }

    private <T extends TreeNode<Long>> void _applyPermissionAssignment1(Tree<Long, HierarchicalPermission<T>> permissionTree, List<DescribePermissionAssignment> permissionAssignments) {
        if (permissionAssignments == null || permissionAssignments.isEmpty()) {
            return;
        }
        for (DescribePermissionAssignment permissionAssignment : permissionAssignments) {
            _applyPermissionAssignment0(permissionTree, permissionAssignment);
        }
    }

    private <T extends TreeNode<Long>> void _applyPermissionAssignment2(Tree<Long, HierarchicalPermission<T>> permissionTree, PermissionAssignmentCollection permissionAssignmentCollection) {
        if (permissionAssignmentCollection == null || permissionAssignmentCollection.isEmpty() || permissionTree == null) {
            return;
        }
        List<DescribePermissionAssignment> permissionAssignments = permissionAssignmentCollection.getPermissionAssignments();
        _applyPermissionAssignment1(permissionTree, permissionAssignments);
    }

    private <T extends TreeNode<Long>> void permissionExtendsHandle(Tree<Long, HierarchicalPermission<T>> permissionTree) {
        if (permissionTree == null) {
            return;
        }
        TreeUtils.depthFirstTraverse(permissionTree, permissionTree.getRoot().getId(), hierarchicalPermission -> {
            HierarchicalPermission<T> parent = permissionTree.getParent(hierarchicalPermission.getId());
            if (parent != null && !hierarchicalPermission.hasAction()) {
                // 继承父级
                hierarchicalPermission.reset(parent.getActionAndEffectParis());
            }
        });
    }

    private <T> void _applyPermissionAssignment3(List<FlatPermission<T>> flatPermissions, DescribePermissionAssignment permissionAssignment) {
        if (flatPermissions == null || permissionAssignment == null) {
            return;
        }
        long targetId = permissionAssignment.getTarget().getValue();
        Map<Long, FlatPermission<T>> byTargetIdMap = MyCollectionUtils.toMap(flatPermissions, AbstractPermission::getId);
        FlatPermission flatPermission = byTargetIdMap.get(targetId);
        _applyPermissionAssignment4(flatPermission, permissionAssignment);
    }

    private void _applyPermissionAssignment4(Permission permission, DescribePermissionAssignment permissionAssignment) {
        if (permission != null && permissionAssignment != null) {
            LongIdentifier action = permissionAssignment.getAction();
            PermissionEffect effect = permissionAssignment.getEffect();
            PermissionEffect existsEffect = permission.getEffect(action);
            if (existsEffect == null) {
                permission.add(action, effect);
            } else if (existsEffect == PermissionEffect.ALLOW && effect == PermissionEffect.DENY) {
                // deny优先于allow
                permission.remove(action, effect);
                permission.add(action, effect);
            }
        }
    }

    private <T> void _applyPermissionAssignment5(List<FlatPermission<T>> flatPermissions, PermissionAssignmentCollection permissionAssignmentCollection) {
        if (flatPermissions == null || permissionAssignmentCollection == null) {
            return;
        }
        List<DescribePermissionAssignment> permissionAssignments = permissionAssignmentCollection.getPermissionAssignments();
        for (DescribePermissionAssignment permissionAssignment : permissionAssignments) {
            _applyPermissionAssignment3(flatPermissions, permissionAssignment);
        }
    }

}
