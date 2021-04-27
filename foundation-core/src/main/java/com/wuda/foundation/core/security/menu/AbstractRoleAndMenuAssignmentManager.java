package com.wuda.foundation.core.security.menu;

import com.wuda.foundation.core.commons.DescribeMenuCore;
import com.wuda.foundation.core.commons.DescribeMenuNode;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.core.security.PermissionGrantManager;
import com.wuda.foundation.core.security.Subject;
import com.wuda.foundation.core.security.v2.*;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.tree.Tree;

import java.util.Collections;
import java.util.List;

public abstract class AbstractRoleAndMenuAssignmentManager implements RoleAndMenuAssignmentManager {

    protected PermissionGrantManager permissionGrantManager;

    protected MenuItemAndCategoryComparator menuItemAndCategoryComparator;

    protected MenuComparator menuComparator;

    protected MenuManager menuManager;

    public void setPermissionGrantManager(PermissionGrantManager permissionGrantManager) {
        this.permissionGrantManager = permissionGrantManager;
    }

    public void setMenuItemAndCategoryComparator(MenuItemAndCategoryComparator menuItemAndCategoryComparator) {
        this.menuItemAndCategoryComparator = menuItemAndCategoryComparator;
    }

    public void setMenuComparator(MenuComparator menuComparator) {
        this.menuComparator = menuComparator;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void assignMenuItemCategoryToRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, PermissionEffect permissionEffect, Long version,Long opUserId) {
        assignMenuItemCategoryToRoleDbOp(permissionRoleId, menuId, menuItemCategoryId, permissionEffect,version, opUserId);
    }

    protected abstract void assignMenuItemCategoryToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, PermissionEffect permissionEffect,Long version, Long opUserId);

    @Override
    public void clearAssignmentBetweenMenuItemCategoryAndRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId) {
        clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(permissionRoleId, menuId, menuItemCategoryId, opUserId);
    }

    protected abstract void clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId);

    @Override
    public void assignMenuItemToRole(Long permissionRoleId, Long menuId, Long menuItemId, PermissionEffect permissionEffect, Long version,Long opUserId) {
        assignMenuItemToRoleDbOp(permissionRoleId, menuId, menuItemId, permissionEffect, version,opUserId);
    }

    protected abstract void assignMenuItemToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemId, PermissionEffect permissionEffect,Long version, Long opUserId);

    @Override
    public void clearAssignmentBetweenMenuItemAndRole(Long permissionRoleId, Long menuId, Long menuItemId, Long opUserId) {
        clearAssignmentBetweenMenuItemAndRoleDbOp(permissionRoleId, menuId, menuItemId, opUserId);
    }

    protected abstract void clearAssignmentBetweenMenuItemAndRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemId, Long opUserId);

    @Override
    public DescribePermissionAssignment getMenuItemCategoryPermission(Long permissionRoleId, Long menuId, Long menuItemCategoryId) {
        return null;
    }

    @Override
    public DescribePermissionAssignment getMenuItemPermission(Long permissionRoleId, Long menuId, Long menuItemId) {
        return null;
    }

    @Override
    public PermissionAssignmentCollection getMenuPermissionAssignments(Long permissionRoleId, Long menuId) {
        Subject subject = new Subject(permissionRoleId, BuiltinIdentifierType.TABLE_PERMISSION_ROLE);
        List<DescribePermissionAssignment> permissionAssignments = permissionGrantManager.getPermissions(subject);
        return new PermissionAssignmentCollection(permissionAssignments);
    }

    @Override
    public FlatPermission<DescribeMenuCore> getMenuPermission(Long permissionRoleId, Long menuId) {
        PermissionAssignmentCollection permissionAssignmentCollection = getMenuPermissionAssignments(permissionRoleId, menuId);
        DescribeMenuCore describeMenuCore = menuManager.getMenuCore(menuId);
        PermissionDecisionMaker permissionDecisionMaker = new DefaultPermissionDecisionMaker();
        List<DescribeMenuCore> describeMenuCores = Collections.singletonList(describeMenuCore);
        List<FlatPermission<DescribeMenuCore>> menuPermissions = permissionDecisionMaker.decide(describeMenuCores,
                describeMenuCore1 -> new FlatPermission<>(describeMenuCore1.getMenuId(),
                        "菜单名称",
                        describeMenuCore1),
                permissionAssignmentCollection
        );
        if(menuPermissions == null || menuPermissions.isEmpty()){
            return null;
        }
        return menuPermissions.get(0);
    }
}
