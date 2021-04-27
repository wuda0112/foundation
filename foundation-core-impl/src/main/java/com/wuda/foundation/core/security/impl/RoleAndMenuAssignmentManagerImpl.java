package com.wuda.foundation.core.security.impl;

import com.wuda.foundation.core.security.Action;
import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.core.security.Subject;
import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.core.security.menu.AbstractRoleAndMenuAssignmentManager;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RoleAndMenuAssignmentManagerImpl extends AbstractRoleAndMenuAssignmentManager {

    @Override
    protected void assignMenuItemCategoryToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, PermissionEffect permissionEffect,Long version, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Target menu = menuTarget(menuId);
        Set<Action> menuItemCategories = menuItemCategoryActions(Collections.singletonList(menuItemCategoryId));
        permissionGrantManager.createAssignment(role, menu, menuItemCategories, permissionEffect,version, opUserId);
    }

    @Override
    protected void clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Target menu = menuTarget(menuId);
        Set<Action> menuItemCategories = menuItemCategoryActions(Collections.singletonList(menuItemCategoryId));
        permissionGrantManager.clearAssigment(role, menu, menuItemCategories, opUserId);
    }

    @Override
    protected void assignMenuItemToRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemId, PermissionEffect permissionEffect,Long version, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Target menu = menuTarget(menuId);
        Set<Action> menuItems = menuItemActions(Collections.singletonList(menuItemId));
        permissionGrantManager.createAssignment(role, menu, menuItems, permissionEffect, version,opUserId);
    }

    @Override
    protected void clearAssignmentBetweenMenuItemAndRoleDbOp(Long permissionRoleId, Long menuId, Long menuItemId, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Target menu = menuTarget(menuId);
        Set<Action> menuItems = menuItemActions(Collections.singletonList(menuItemId));
        permissionGrantManager.clearAssigment(role, menu, menuItems, opUserId);
    }

    private Subject roleSubject(Long permissionRoleId) {
        return new Subject(permissionRoleId, BuiltinIdentifierType.TABLE_PERMISSION_ROLE);
    }

    private Target menuTarget(Long menuId) {
        return new Target(menuId, BuiltinIdentifierType.MENU);
    }

    private Action menuItemCategoryAction(Long menuItemCategoryId) {
        return new Action(menuItemCategoryId, BuiltinIdentifierType.TABLE_MENU_ITEM_CATEGORY);
    }

    private Set<Action> menuItemCategoryActions(Collection<Long> menuItemCategoryIds) {
        Set<Action> actions = new HashSet<>(menuItemCategoryIds.size());
        for (Long menuItemCategoryId : menuItemCategoryIds) {
            Action action = menuItemCategoryAction(menuItemCategoryId);
            actions.add(action);
        }
        return actions;
    }

    private Action menuItemAction(Long menuItemId) {
        return new Action(menuItemId, BuiltinIdentifierType.FOUNDATION_MENU_ITEM);
    }

    private Set<Action> menuItemActions(Collection<Long> menuItemIds) {
        Set<Action> actions = new HashSet<>(menuItemIds.size());
        for (Long menuItemId : menuItemIds) {
            Action action = menuItemAction(menuItemId);
            actions.add(action);
        }
        return actions;
    }
}
