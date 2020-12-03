package com.wuda.foundation.core.security;

public abstract class AbstractRoleAndMenuItemAssignmentManager implements RoleAndMenuItemAssignmentManager {
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
        assignMenuItemToRoleDbOp(permissionRoleId,menuItemId,allowOrDeny,opUserId);
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
    public DescribePermissionAssignment getMenuPermission(Long permissionRoleId) {
        return null;
    }
}
