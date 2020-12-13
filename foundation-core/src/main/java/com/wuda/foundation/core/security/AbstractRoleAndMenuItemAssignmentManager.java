package com.wuda.foundation.core.security;

import com.wuda.foundation.core.security.menu.MenuItemAndCategoryComparator;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;

import java.util.List;

public abstract class AbstractRoleAndMenuItemAssignmentManager implements RoleAndMenuItemAssignmentManager {

    protected PermissionGrantManager permissionGrantManager;

    protected MenuItemAndCategoryComparator menuItemAndCategoryComparator;

    public void setPermissionGrantManager(PermissionGrantManager permissionGrantManager) {
        this.permissionGrantManager = permissionGrantManager;
    }

    public void setMenuItemAndCategoryComparator(MenuItemAndCategoryComparator menuItemAndCategoryComparator) {
        this.menuItemAndCategoryComparator = menuItemAndCategoryComparator;
    }

    @Override
    public void assignMenuItemCategoryToRole(Long permissionRoleId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId) {
        assignMenuItemCategoryToRoleDbOp(permissionRoleId, menuItemCategoryId, allowOrDeny, opUserId);
    }

    protected abstract void assignMenuItemCategoryToRoleDbOp(Long permissionRoleId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId);

    @Override
    public void clearAssignmentBetweenMenuItemCategoryAndRole(Long permissionRoleId, Long menuItemCategoryId, Long opUserId) {
        clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(permissionRoleId, menuItemCategoryId, opUserId);
    }

    protected abstract void clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(Long permissionRoleId, Long menuItemCategoryId, Long opUserId);

    @Override
    public void assignMenuItemToRole(Long permissionRoleId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId) {
        assignMenuItemToRoleDbOp(permissionRoleId, menuItemId, allowOrDeny, opUserId);
    }

    protected abstract void assignMenuItemToRoleDbOp(Long permissionRoleId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId);

    @Override
    public void clearAssignmentBetweenMenuItemAndRole(Long permissionRoleId, Long menuItemId, Long opUserId) {
        clearAssignmentBetweenMenuItemAndRoleDbOp(permissionRoleId, menuItemId, opUserId);
    }

    protected abstract void clearAssignmentBetweenMenuItemAndRoleDbOp(Long permissionRoleId, Long menuItemId, Long opUserId);

    @Override
    public DescribePermissionAssignment getMenuItemCategoryPermission(Long permissionRoleId, Long menuItemCategoryId) {
        return null;
    }

    @Override
    public DescribePermissionAssignment getMenuItemPermission(Long permissionRoleId, Long menuItemId) {
        return null;
    }

    @Override
    public List<MergedPermissionAssignment> getMenuPermission(Long permissionRoleId) {
        Subject subject = new Subject(permissionRoleId, BuiltinIdentifierType.PERMISSION_ROLE);
        List<DescribePermissionAssignment> originalPermissionAssignments = permissionGrantManager.getPermissions(subject);
        PermissionAssignmentMerger permissionAssignmentMerger = new PermissionAssignmentMerger();
        return permissionAssignmentMerger.merge(originalPermissionAssignments, null, menuItemAndCategoryComparator);
    }
}
