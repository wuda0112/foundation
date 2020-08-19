package com.wuda.foundation.security;

import java.util.List;

public abstract class AbstractPermissionGrantManager implements PermissionGrantManager {
    @Override
    public void grantPermission(GrantPermissionRequest request, Long opUserId) {
        grantPermissionDbOp(request, opUserId);
    }

    protected abstract void grantPermissionDbOp(GrantPermissionRequest request, Long opUserId);

    @Override
    public void revokePermission(GrantPermissionRequest request, Long opUserId) {
        revokePermissionDbOp(request, opUserId);
    }

    protected abstract void revokePermissionDbOp(GrantPermissionRequest request, Long opUserId);

    @Override
    public List<SubjectPermission> getPermission(Subject subject) {
        return getPermissionDbOp(subject);
    }

    protected abstract List<SubjectPermission> getPermissionDbOp(Subject subject);
}
