package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractPermissionGrantManager implements PermissionGrantManager {
    @Override
    public void createAssignment(Subject subject, Set<Target> targetSet, AllowOrDeny allowOrDeny, Long opUserId) {
        createAssignmentDbOp(subject, targetSet, allowOrDeny, opUserId);
    }

    protected abstract void createAssignmentDbOp(Subject subject, Set<Target> targetSet, AllowOrDeny allowOrDeny, Long opUserId);

    @Override
    public void createAssignment(Subject subject, Target target, Set<Action> actionSet, AllowOrDeny allowOrDeny, Long opUserId) {
        Objects.requireNonNull(actionSet);
        if (!actionSet.isEmpty()) {
            if (assignedTargetToSubject(subject, target, allowOrDeny)) {
                return;
            }
            createAssignmentDbOp(subject, target, actionSet, allowOrDeny, opUserId);
        }
    }

    protected abstract void createAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet, AllowOrDeny allowOrDeny, Long opUserId);

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
    public List<MergedPermissionAssignment> getPermissions(Subject subject) {
        return getPermissionsDbOp(subject, null);
    }

    protected abstract List<MergedPermissionAssignment> getPermissionsDbOp(Subject subject, IdentifierType targetType);

    @Override
    public List<MergedPermissionAssignment> getPermissions(Subject subject, IdentifierType targetType) {
        return getPermissionsDbOp(subject, targetType);
    }

    @Override
    public List<MergedPermissionAssignment> getPermissions(List<Subject> subjects) {
        return getPermissionsDbOp(subjects,null);
    }

    protected abstract List<MergedPermissionAssignment> getPermissionsDbOp(List<Subject> subjects, IdentifierType targetType);

    @Override
    public List<MergedPermissionAssignment> getPermissions(List<Subject> subjects, IdentifierType targetType){
        return getPermissionsDbOp(subjects, targetType);
    }

    @Override
    public boolean assignedTargetToSubject(Subject subject, Target target, AllowOrDeny allowOrDeny) {
        return assignedTargetToSubjectDbOp(subject, target, allowOrDeny);
    }

    protected abstract boolean assignedTargetToSubjectDbOp(Subject subject, Target target, AllowOrDeny allowOrDeny);
}
