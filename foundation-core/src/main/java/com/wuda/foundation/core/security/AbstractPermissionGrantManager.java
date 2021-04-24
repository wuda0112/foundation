package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractPermissionGrantManager implements PermissionGrantManager {
    @Override
    public void createAssignment(Subject subject, Set<Target> targetSet, PermissionEffect permissionEffect, Long version, Long opUserId) {
        createAssignmentDbOp(subject, targetSet, permissionEffect, version, opUserId);
    }

    protected abstract void createAssignmentDbOp(Subject subject, Set<Target> targetSet, PermissionEffect permissionEffect, Long version, Long opUserId);

    @Override
    public void createAssignment(Subject subject, Target target, Set<Action> actionSet, PermissionEffect permissionEffect, Long version, Long opUserId) {
        Objects.requireNonNull(actionSet);
        if (!actionSet.isEmpty()) {
            if (assignedTargetToSubject(subject, target, permissionEffect)) {
                return;
            }
            createAssignmentDbOp(subject, target, actionSet, permissionEffect, version, opUserId);
        }
    }

    protected abstract void createAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, PermissionEffect permissionEffect, Long version, Long opUserId);

    @Override
    public void clearAssigment(Subject subject, Set<Target> targetSet, Long opUserId) {
        clearAssignmentDbOp(subject, targetSet, opUserId);
    }

    protected abstract void clearAssignmentDbOp(Subject subject, Set<Target> targetSet, Long opUserId);

    @Override
    public void clearAssigment(Subject subject, Target target, Set<Action> actionSet, Long opUserId) {
        clearAssignmentDbOp(subject, target, actionSet, opUserId);
    }

    protected abstract void clearAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, Long opUserId);

    @Override
    public List<DescribePermissionAssignment> getPermissions(Subject subject) {
        List<DescribePermissionAssignment> list = getPermissionsDbOp(subject, null);
        return DescribePermissionAssignment.mergeUseGreaterVersion(list);
    }

    protected abstract List<DescribePermissionAssignment> getPermissionsDbOp(Subject subject, IdentifierType targetType);

    @Override
    public List<DescribePermissionAssignment> getPermissions(Subject subject, IdentifierType targetType) {
        List<DescribePermissionAssignment> list = getPermissionsDbOp(subject, targetType);
        return DescribePermissionAssignment.mergeUseGreaterVersion(list);
    }

    @Override
    public List<DescribePermissionAssignment> getPermissions(List<Subject> subjects) {
        List<DescribePermissionAssignment> list = getPermissionsDbOp(subjects, null);
        return DescribePermissionAssignment.mergeUseGreaterVersion(list);
    }

    protected abstract List<DescribePermissionAssignment> getPermissionsDbOp(List<Subject> subjects, IdentifierType targetType);

    @Override
    public List<DescribePermissionAssignment> getPermissions(List<Subject> subjects, IdentifierType targetType) {
        List<DescribePermissionAssignment> list = getPermissionsDbOp(subjects, targetType);
        return DescribePermissionAssignment.mergeUseGreaterVersion(list);
    }

    @Override
    public boolean assignedTargetToSubject(Subject subject, Target target, PermissionEffect permissionEffect) {
        return assignedTargetToSubjectDbOp(subject, target, permissionEffect);
    }

    protected abstract boolean assignedTargetToSubjectDbOp(Subject subject, Target target, PermissionEffect permissionEffect);
}
