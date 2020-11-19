package com.wuda.foundation.security;

import java.util.List;
import java.util.Set;

public abstract class AbstractPermissionGrantManager implements PermissionGrantManager {
    @Override
    public void grantTarget(Subject subject, Set<Long> targetIdSet, Long opUserId) {
        grantTargetDbOp(subject, targetIdSet, opUserId);
    }

    protected abstract void grantTargetDbOp(Subject subject, Set<Long> targetIdSet, Long opUserId);

    @Override
    public void grantAction(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId) {
        grantActionDbOp(subject,targetId, actionIdSet, opUserId);
    }

    protected abstract void grantActionDbOp(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId);

    @Override
    public void revokeTarget(Subject subject, Set<Long> targetIdSet, Long opUserId) {
        revokeTargetDbOp(subject, targetIdSet, opUserId);
    }

    protected abstract void revokeTargetDbOp(Subject subject, Set<Long> targetIdSet, Long opUserId);

    @Override
    public void revokeAction(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId) {
        revokeActionDbOp(subject, targetId, actionIdSet, opUserId);
    }

    protected abstract void revokeActionDbOp(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId);

    @Override
    public List<DescribePermission> getPermissions(Subject subject) {
        return getPermissionsDbOp(subject);
    }

    protected abstract List<DescribePermission> getPermissionsDbOp(Subject subject);
}
