package com.wuda.foundation.core.security.impl;

import com.wuda.foundation.core.security.*;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RoleAndMenuItemAssignmentManagerImpl extends AbstractRoleAndMenuItemAssignmentManager {

    @Override
    protected void assignMenuItemCategoryToRoleDbOp(Long permissionRoleId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Set<Target> menuItemCategories = menuItemCategoryTargets(Collections.singletonList(menuItemCategoryId));
        permissionGrantManager.createAssignment(role, menuItemCategories, allowOrDeny, opUserId);
    }

    @Override
    protected void clearAssignmentBetweenMenuItemCategoryAndRoleDbOp(Long permissionRoleId, Long menuItemCategoryId, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Set<Target> menuItemCategories = menuItemCategoryTargets(Collections.singletonList(menuItemCategoryId));
        permissionGrantManager.clearAssigment(role, menuItemCategories, opUserId);
    }

    @Override
    protected void assignMenuItemToRoleDbOp(Long permissionRoleId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Set<Target> menuItems = menuItemTargets(Collections.singletonList(menuItemId));
        permissionGrantManager.createAssignment(role, menuItems, allowOrDeny, opUserId);
    }

    @Override
    protected void clearAssignmentBetweenMenuItemAndRoleDbOp(Long permissionRoleId, Long menuItemId, Long opUserId) {
        Subject role = roleSubject(permissionRoleId);
        Set<Target> menuItems = menuItemTargets(Collections.singletonList(menuItemId));
        permissionGrantManager.clearAssigment(role, menuItems, opUserId);
    }

    private Subject roleSubject(Long permissionRoleId) {
        return new Subject(permissionRoleId, BuiltinIdentifierType.PERMISSION_ROLE);
    }

    private Target menuItemCategoryTarget(Long menuItemCategoryId) {
        return new Target(menuItemCategoryId, BuiltinIdentifierType.MENU_ITEM_CATEGORY);
    }

    private Set<Target> menuItemCategoryTargets(Collection<Long> menuItemCategoryIds) {
        Set<Target> targets = new HashSet<>(menuItemCategoryIds.size());
        for (Long menuItemCategoryId : menuItemCategoryIds) {
            Target target = menuItemCategoryTarget(menuItemCategoryId);
            targets.add(target);
        }
        return targets;
    }

    private Target menuItemTarget(Long menuItemId) {
        return new Target(menuItemId, BuiltinIdentifierType.MENU_ITEM);
    }

    private Set<Target> menuItemTargets(Collection<Long> menuItemIds) {
        Set<Target> targets = new HashSet<>(menuItemIds.size());
        for (Long menuItemId : menuItemIds) {
            Target target = menuItemTarget(menuItemId);
            targets.add(target);
        }
        return targets;
    }
}
