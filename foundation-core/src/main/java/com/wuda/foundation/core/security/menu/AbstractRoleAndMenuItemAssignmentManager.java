package com.wuda.foundation.core.security.menu;

import com.wuda.foundation.core.commons.Menu;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.*;
import com.wuda.foundation.core.security.menu.MenuItemAndCategoryComparator;
import com.wuda.foundation.core.security.menu.MenuPermissionUtils;
import com.wuda.foundation.core.security.menu.RoleAndMenuItemAssignmentManager;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;

import java.util.List;

public abstract class AbstractRoleAndMenuItemAssignmentManager implements RoleAndMenuItemAssignmentManager {

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

    public void setMenuComparator(MenuComparator menuComparator){
        this.menuComparator = menuComparator;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void assignMenuItemCategoryToRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId) {
        assignMenuItemCategoryToRoleDbOp(permissionRoleId, menuId, menuItemCategoryId, allowOrDeny, opUserId);
    }

    protected abstract void assignMenuItemCategoryToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId);

    @Override
    public void clearAssignmentBetweenMenuItemCategoryAndRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId) {
        clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(permissionRoleId, menuId, menuItemCategoryId, opUserId);
    }

    protected abstract void clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId);

    @Override
    public void assignMenuItemToRole(Long permissionRoleId, Long menuId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId) {
        assignMenuItemToRoleDbOp(permissionRoleId, menuId, menuItemId, allowOrDeny, opUserId);
    }

    protected abstract void assignMenuItemToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId);

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
    public List<MergedPermissionAssignment> getMenuPermissionAssignments(Long permissionRoleId, Long menuId) {
        Subject subject = new Subject(permissionRoleId, BuiltinIdentifierType.PERMISSION_ROLE);
        List<DescribePermissionAssignment> originalPermissionAssignments = permissionGrantManager.getPermissions(subject);
        PermissionAssignmentMerger permissionAssignmentMerger = new PermissionAssignmentMerger();
        return permissionAssignmentMerger.merge(originalPermissionAssignments, menuComparator, menuItemAndCategoryComparator);
    }

    @Override
    public Menu getPermittedMenu(Long permissionRoleId, Long menuId) {
        List<MergedPermissionAssignment> permissionAssignments = getMenuPermissionAssignments(permissionRoleId, menuId);
        Menu menu = menuManager.getMenu(menuId);
        MenuPermissionUtils.applyPermission(menu, permissionAssignments);
        return menu;
    }
}
