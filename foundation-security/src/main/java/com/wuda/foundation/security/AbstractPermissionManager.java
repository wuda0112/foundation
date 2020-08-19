package com.wuda.foundation.security;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.ExtObjects;

import java.util.List;
import java.util.Set;

public abstract class AbstractPermissionManager implements PermissionManager {

    @Override
    public CreateResult createPermissionTarget(CreatePermissionTarget target, CreateMode createMode, Long opUserId) {
        ExtObjects.requireNonNull(target, opUserId);
        return createPermissionTargetDbOp(target,createMode, opUserId);
    }

    protected abstract CreateResult createPermissionTargetDbOp(CreatePermissionTarget target,CreateMode createMode, Long opUserId);

    @Override
    public CreateResult createPermissionAction(CreatePermissionAction action, CreateMode createMode,Long opUserId) {
        ExtObjects.requireNonNull(action, opUserId);
        return createPermissionActionDbOp(action,createMode, opUserId);
    }

    protected abstract CreateResult createPermissionActionDbOp(CreatePermissionAction action,CreateMode createMode, Long opUserId);

    @Override
    public long createPermission(CreatePermissionTarget target, Set<CreatePermissionAction> actions, Long opUserId) {
        ExtObjects.requireNonNull(target, actions, opUserId);
        long targetId = target.getId();
        for (CreatePermissionAction action : actions) {
            if (targetId != action.getPermissionTargetId()) {
                throw new IllegalStateException("permission target和action的target id必须相等");
            }
        }
        return createPermissionDbOp(target, actions, opUserId);
    }

    protected abstract long createPermissionDbOp(CreatePermissionTarget target, Set<CreatePermissionAction> actions, Long opUserId);

    @Override
    public void createPermissionAction(Set<CreatePermissionAction> actions, Long opUserId) {
        ExtObjects.requireNonNull(actions, opUserId);
        createPermissionActionDbOp(actions, opUserId);
    }

    protected abstract void createPermissionActionDbOp(Set<CreatePermissionAction> actions, Long opUserId);

    @Override
    public void deleteAction(Long permissionActionId, Long opUserId) {
        ExtObjects.requireNonNull(permissionActionId, opUserId);
        deleteActionDbOp(permissionActionId, opUserId);
    }

    @Override
    public void deleteActionByTarget(Long permissionTargetId, Long opUserId){
        deleteActionByTargetDbOp(permissionTargetId, opUserId);
    }

    protected abstract void deleteActionByTargetDbOp(Long permissionTargetId, Long opUserId);

    protected abstract void deleteActionDbOp(Long permissionActionId, Long opUserId);

    @Override
    public void deleteTarget(Long permissionTargetId, Long opUserId) {
        ExtObjects.requireNonNull(permissionTargetId, opUserId);
        deleteTargetDbOp(permissionTargetId, opUserId);
    }

    protected abstract void deleteTargetDbOp(Long permissionTargetId, Long opUserId);

    @Override
    public void updatePermissionTarget(Long targetId, String name, String description, Long opUserId) throws AlreadyExistsException{
        ExtObjects.requireNonNull(targetId, name, description, opUserId);
        updatePermissionTargetDbOp(targetId, name, description, opUserId);
    }

    protected abstract void updatePermissionTargetDbOp(Long targetId, String name, String description, Long opUserId) throws AlreadyExistsException;

    @Override
    public void updatePermissionAction(Long actionId, String name, String description, Long opUserId) throws AlreadyExistsException{
        ExtObjects.requireNonNull(actionId, name, description, opUserId);
        updatePermissionActionDbOp(actionId, name, description, opUserId);
    }

    @Override
    public DescribePermissionTarget getPermissionTargetById(Long permissionTargetId) {
        return getPermissionTargetByIdDbOp(permissionTargetId);
    }

    protected abstract DescribePermissionTarget getPermissionTargetByIdDbOp(Long permissionTargetId);

    @Override
    public DescribePermissionAction getPermissionActionById(Long permissionActionId) {
        return getPermissionActionByIdDbOp(permissionActionId);
    }

    protected abstract DescribePermissionAction getPermissionActionByIdDbOp(Long permissionActionId);

    @Override
    public List<DescribePermissionAction> getPermissionActionByTarget(Long permissionTargetId) {
        return getPermissionActionByTargetDbOp(permissionTargetId);
    }

    protected abstract List<DescribePermissionAction> getPermissionActionByTargetDbOp(Long permissionTargetId);

    protected abstract void updatePermissionActionDbOp(Long actionId, String name, String description, Long opUserId) throws AlreadyExistsException;

    @Override
    public DescribePermission getPermission(Long permissionTargetId) {
        return getPermissionDbOp(permissionTargetId);
    }

    protected abstract DescribePermission getPermissionDbOp(Long permissionTargetId);
}
