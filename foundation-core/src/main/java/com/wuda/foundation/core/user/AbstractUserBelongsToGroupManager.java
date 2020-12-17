package com.wuda.foundation.core.user;

import com.wuda.foundation.core.commons.DescribeMenuItemCore;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.DescribePermissionRole;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.List;

public abstract class AbstractUserBelongsToGroupManager implements UserBelongsToGroupManager {
    @Override
    public CreateResult createUserBelongsToGroupCore(CreateUserBelongsToGroupCoreRequest request, CreateMode createMode, Long opUserId) {
        return createUserBelongsToGroupCoreDbOp(request, createMode, opUserId);
    }

    protected abstract CreateResult createUserBelongsToGroupCoreDbOp(CreateUserBelongsToGroupCoreRequest request, CreateMode createMode, Long opUserId);

    @Override
    public void removeUserFromGroup(RemoveUserFromGroupRequest request, Long opUserId) {
        removeUserFromGroupDbOp(request, opUserId);
    }

    protected abstract void removeUserFromGroupDbOp(RemoveUserFromGroupRequest request, Long opUserId);

    @Override
    public DescribeUserBelongsToGroupCore getUserBelongsToGroupCore(Long id) {
        return getUserBelongsToGroupCoreDbOp(id);
    }

    protected abstract DescribeUserBelongsToGroupCore getUserBelongsToGroupCoreDbOp(Long id);

    @Override
    public Long getUserBelongsToGroupId(Long userId, LongIdentifier group) {
        return getUserBelongsToGroupIdDbOp(userId, group);
    }

    protected abstract Long getUserBelongsToGroupIdDbOp(Long userId, LongIdentifier group);

    @Override
    public CreateResult createUserBelongsToGroupGeneral(CreateUserBelongsToGroupGeneralRequest request, CreateMode createMode, Long opUserId) {
        return createUserBelongsToGroupGeneralDbOp(request, createMode, opUserId);
    }

    protected abstract CreateResult createUserBelongsToGroupGeneralDbOp(CreateUserBelongsToGroupGeneralRequest request, CreateMode createMode, Long opUserId);

    @Override
    public void updateUserBelongsToGroupGeneral(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId) {
        updateUserBelongsToGroupGeneralDbOp(request, opUserId);
    }

    protected abstract void updateUserBelongsToGroupGeneralDbOp(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId);

    @Override
    public DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneral(Long id) {
        return getUserBelongsToGroupGeneralDbOp(id);
    }

    protected abstract DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneralDbOp(Long id);

    @Override
    public CreateResult createUserBelongsToGroupRole(CreateUserBelongsToGroupRoleRequest request, CreateMode createMode, Long opUserId) {
        return createUserBelongsToGroupRoleDbOp(request, createMode, opUserId);
    }

    protected abstract CreateResult createUserBelongsToGroupRoleDbOp(CreateUserBelongsToGroupRoleRequest request, CreateMode createMode, Long opUserId);

    @Override
    public void removeUsersRoleFromGroup(RemoveUsersRoleFromGroupRequest request, Long opUserId) {
        removeUsersRoleFromGroupDbOp(request, opUserId);
    }

    protected abstract void removeUsersRoleFromGroupDbOp(RemoveUsersRoleFromGroupRequest request, Long opUserId);

    @Override
    public DescribeUserBelongsToGroupRole getUserBelongsToGroupRole(Long id) {
        return getUserBelongsToGroupRoleDbOp(id);
    }

    protected abstract DescribeUserBelongsToGroupRole getUserBelongsToGroupRoleDbOp(Long id);

    @Override
    public List<LongIdentifier> getGroups(Long userId) {
        return getGroupsDbOp(userId);
    }

    protected abstract List<LongIdentifier> getGroupsDbOp(Long userId);

    @Override
    public List<Long> getMembers(LongIdentifier group) {
        return getMembersDbOp(group);
    }

    protected abstract List<Long> getMembersDbOp(LongIdentifier group);

    @Override
    public List<DescribePermissionAssignment> getPermissionsFromRole(Long userId, LongIdentifier group) {
        return getPermissionsFromRoleDbOp(userId, group);
    }

    protected abstract List<DescribePermissionAssignment> getPermissionsFromRoleDbOp(Long userId, LongIdentifier group);

    @Override
    public List<DescribePermissionRole> getRoles(Long userId, LongIdentifier group) {
        return getRolesDbOp(userId, group);
    }

    protected abstract List<DescribePermissionRole> getRolesDbOp(Long userId, LongIdentifier group);

    @Override
    public List<DescribeMenuItemCore> getMenuItemsFromRole(Long userId, LongIdentifier group) {
        return getMenuItemsFromRoleDbOp(userId, group);
    }

    protected abstract List<DescribeMenuItemCore> getMenuItemsFromRoleDbOp(Long userId, LongIdentifier group);
}
