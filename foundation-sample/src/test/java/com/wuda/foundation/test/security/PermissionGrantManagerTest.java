package com.wuda.foundation.test.security;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.*;
import com.wuda.foundation.security.impl.PermissionGrantManagerImpl;
import com.wuda.foundation.security.impl.PermissionManagerImpl;
import org.junit.Test;

import java.util.Collections;

public class PermissionGrantManagerTest extends TestBase {

    Subject subject = new Subject(1024L, BuiltinIdentifierType.TABLE_ITEM);

    @Test
    public void testGrantTarget() {

        PermissionManager permissionManager = getPermissionManager();
        long targetId = keyGenerator.next();
        CreatePermissionTarget target = new CreatePermissionTarget.Builder()
                .setId(targetId)
                .setCategoryId(keyGenerator.next())
                .setPermissionTargetType(byte0)
                .setDescription("target-desc-" + targetId)
                .setName("target-name-" + targetId)
                .setReferencedIdentifier(new LongIdentifier(0L, BuiltinIdentifierType.TABLE_ITEM))
                .build();

        long actionId = keyGenerator.next();
        CreatePermissionAction action = new CreatePermissionAction.Builder()
                .setId(actionId)
                .setPermissionTargetId(targetId)
                .setName("read-" + actionId)
                .setReferencedIdentifier(new LongIdentifier(0L, BuiltinIdentifierType.TABLE_ITEM))
                .setDescription("action-desc-" + actionId)
                .build();

        permissionManager.createPermission(target, Collections.singleton(action), opUserId);

        PermissionGrantManager permissionGrantManager = getPermissionGrantManager();
        permissionGrantManager.grantTarget(subject, Collections.singleton(targetId), opUserId);
        permissionGrantManager.grantTarget(subject, Collections.singleton(targetId), opUserId);
        permissionGrantManager.grantAction(subject, targetId, Collections.singleton(actionId), opUserId);
        permissionGrantManager.grantAction(subject, targetId, Collections.singleton(actionId), opUserId);
        permissionGrantManager.revokeAction(subject, targetId, Collections.singleton(actionId), opUserId);
        permissionGrantManager.revokeAction(subject, targetId, Collections.singleton(actionId), opUserId);
        permissionGrantManager.revokeTarget(subject, Collections.singleton(targetId), opUserId);
        permissionGrantManager.revokeTarget(subject, Collections.singleton(targetId), opUserId);

        permissionGrantManager.grantAction(subject, targetId, Collections.singleton(actionId), opUserId);
        permissionGrantManager.grantTarget(subject, Collections.singleton(targetId), opUserId);
    }

    private PermissionGrantManager getPermissionGrantManager() {
        PermissionGrantManagerImpl permissionGrantManager = new PermissionGrantManagerImpl();
        permissionGrantManager.setDataSource(dataSource);
        return permissionGrantManager;
    }

    private PermissionManager getPermissionManager() {
        PermissionManagerImpl permissionManager = new PermissionManagerImpl();
        permissionManager.setDataSource(getDataSource());
        return permissionManager;
    }
}
