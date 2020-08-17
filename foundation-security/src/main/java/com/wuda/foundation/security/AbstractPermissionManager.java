package com.wuda.foundation.security;

import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.ExtObjects;

import java.util.List;
import java.util.Set;

public abstract class AbstractPermissionManager implements PermissionManager {

    @Override
    public CreateResult createPermissionTarget(CreatePermissionTarget target, Long opUserId) {
        ExtObjects.requireNonNull(target, opUserId);
        return createPermissionTargetDbOp(target, opUserId);
    }

    protected abstract CreateResult createPermissionTargetDbOp(CreatePermissionTarget target, Long opUserId);

    @Override
    public CreateResult createPermissionAction(CreatePermissionAction action, Long opUserId) {
        ExtObjects.requireNonNull(action, opUserId);
        return createPermissionActionDbOp(action, opUserId);
    }

    protected abstract CreateResult createPermissionActionDbOp(CreatePermissionAction action, Long opUserId);

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

    /**
     * 作为{@link #createPermission(CreatePermissionTarget, Set, Long)}方法的一部分,参数的校验已经在{@link #createPermission(CreatePermissionTarget, Set, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param target   该permission作用的对象
     * @param actions  该permission允许的动作,可以为空
     * @param opUserId 操作人用户ID
     * @return permission target id
     */
    protected abstract long createPermissionDbOp(CreatePermissionTarget target, Set<CreatePermissionAction> actions, Long opUserId);

    @Override
    public void createPermissionAction(Set<CreatePermissionAction> actions, Long opUserId) {
        ExtObjects.requireNonNull(actions, opUserId);
        createPermissionActionDbOp(actions, opUserId);
    }

    /**
     * 作为{@link #createPermissionAction(Set, Long)}方法的一部分,参数的校验已经在{@link #createPermissionAction(Set, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param opUserId 操作人用户ID
     * @param actions  action set
     */
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

    /**
     * 作为{@link #deleteActionByTarget(Long, Long)}方法的一部分,参数的校验已经在{@link #deleteActionByTarget(Long, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param opUserId           操作人用户ID
     * @param permissionTargetId permission target id
     */
    protected abstract void deleteActionByTargetDbOp(Long permissionTargetId, Long opUserId);

    /**
     * 作为{@link #deleteAction(Long, Long)}方法的一部分,参数的校验已经在{@link #deleteAction(Long, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param opUserId           操作人用户ID
     * @param permissionActionId permission action id
     */
    protected abstract void deleteActionDbOp(Long permissionActionId, Long opUserId);

    @Override
    public void deleteTarget(Long permissionTargetId, Long opUserId) {
        ExtObjects.requireNonNull(permissionTargetId, opUserId);
        deleteTargetDbOp(permissionTargetId, opUserId);
    }

    /**
     * 作为{@link #deleteTarget(Long, Long)}方法的一部分,参数的校验已经在{@link #deleteTarget(Long, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param opUserId           操作人用户ID
     * @param permissionTargetId permission target id
     */
    protected abstract void deleteTargetDbOp(Long permissionTargetId, Long opUserId);

    @Override
    public void updatePermissionTarget(Long permissionTargetId, String name, String description, Long opUserId) {
        ExtObjects.requireNonNull(permissionTargetId, name, description, opUserId);
        updatePermissionTargetDbOp(permissionTargetId, name, description, opUserId);
    }

    /**
     * 作为{@link #updatePermissionTarget(Long, String, String, Long)}方法的一部分,参数的校验已经在{@link #updatePermissionTarget(Long, String, String, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param opUserId           操作人用户ID
     * @param permissionTargetId permission target id
     */
    protected abstract void updatePermissionTargetDbOp(Long permissionTargetId, String name, String description, Long opUserId);

    @Override
    public void updatePermissionAction(Long permissionActionId, String action, String description, Long opUserId) {
        ExtObjects.requireNonNull(permissionActionId, action, description, opUserId);
        updatePermissionActionDbOp(permissionActionId, action, description, opUserId);
    }

    @Override
    public DescribePermissionTarget getPermissionTargetById(Long permissionTargetId) {
        return getPermissionTargetByIdDbOp(permissionTargetId);
    }

    /**
     * 作为{@link #getPermissionTargetById(Long)}方法的一部分,参数的校验已经在{@link #getPermissionTargetById(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param permissionTargetId permission target id
     * @return permission target
     */
    protected abstract DescribePermissionTarget getPermissionTargetByIdDbOp(Long permissionTargetId);

    @Override
    public DescribePermissionAction getPermissionActionById(Long permissionActionId) {
        return getPermissionActionByIdDbOp(permissionActionId);
    }

    /**
     * 作为{@link #getPermissionActionById(Long)}方法的一部分,参数的校验已经在{@link #getPermissionActionById(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param permissionActionId permission action id
     * @return permission action
     */
    protected abstract DescribePermissionAction getPermissionActionByIdDbOp(Long permissionActionId);

    @Override
    public List<DescribePermissionAction> getPermissionActionByTarget(Long permissionTargetId) {
        return getPermissionActionByTargetDbOp(permissionTargetId);
    }

    /**
     * 作为{@link #getPermissionActionByTarget(Long)}方法的一部分,参数的校验已经在{@link #getPermissionActionByTarget(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param permissionTargetId permission target id
     * @return permission action
     */
    protected abstract List<DescribePermissionAction> getPermissionActionByTargetDbOp(Long permissionTargetId);

    /**
     * 作为{@link #updatePermissionAction(Long, String, String, Long)}方法的一部分,参数的校验已经在{@link #updatePermissionAction(Long, String, String, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param permissionActionId permission action id
     * @param action             permission action
     * @param opUserId           操作人用户ID
     * @param description        描述信息
     */
    protected abstract void updatePermissionActionDbOp(Long permissionActionId, String action, String description, Long opUserId);

    @Override
    public DescribePermission getPermission(Long permissionTargetId) {
        return getPermissionDbOp(permissionTargetId);
    }

    /**
     * 作为{@link #getPermission(Long)}方法的一部分,参数的校验已经在{@link #getPermission(Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param permissionTargetId permission target id
     * @return a permission
     */
    protected abstract DescribePermission getPermissionDbOp(Long permissionTargetId);
}
