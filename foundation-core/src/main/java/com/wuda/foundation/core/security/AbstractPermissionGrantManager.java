package com.wuda.foundation.core.security;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractPermissionGrantManager implements PermissionGrantManager {
    @Override
    public void createAssignment(Subject subject, Set<Target> targetSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        createAssignmentDbOp(subject, targetSet, inclusionOrExclusion, opUserId);
    }

    protected abstract void createAssignmentDbOp(Subject subject, Set<Target> targetSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId);

    @Override
    public void createAssignment(Subject subject, Target target, Set<Action> actionSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId) {
        Objects.requireNonNull(actionSet);
        if (!actionSet.isEmpty()) {
            if (subjectTargetAssigned(subject, target, inclusionOrExclusion)) {
                return;
            }
            createAssignmentDbOp(subject, target, actionSet,inclusionOrExclusion, opUserId);
        }
    }

    protected abstract void createAssignmentDbOp(Subject subject, Target target, Set<Action> actionSet,InclusionOrExclusion inclusionOrExclusion, Long opUserId);

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
    public List<DescribePermission> getPermissions(Subject subject) {
        return getPermissionsDbOp(subject);
    }

    protected abstract List<DescribePermission> getPermissionsDbOp(Subject subject);

    @Override
    public List<DescribePermission> getPermissions(List<Subject> subjects) {
        return getPermissionsDbOp(subjects);
    }

    protected abstract List<DescribePermission> getPermissionsDbOp(List<Subject> subjects);

    public boolean subjectTargetAssigned(Subject subject, Target target, InclusionOrExclusion inclusionOrExclusion) {
        return subjectTargetAssignedDbOp(subject, target, inclusionOrExclusion);
    }

    protected abstract boolean subjectTargetAssignedDbOp(Subject subject, Target target, InclusionOrExclusion inclusionOrExclusion);
}
