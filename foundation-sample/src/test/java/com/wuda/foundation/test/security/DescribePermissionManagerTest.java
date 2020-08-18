package com.wuda.foundation.test.security;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.*;
import com.wuda.foundation.security.impl.PermissionManagerImpl;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class DescribePermissionManagerTest extends TestBase {

    @Test
    public void testCreatePermission() {
        PermissionManager permissionManager = getPermissionManager();
        long permissionTargetId = keyGenerator.next();
        CreatePermissionTarget target = new CreatePermissionTarget.Builder()
                .setId(permissionTargetId)
                .setCategoryId(keyGenerator.next())
                .setPermissionTargetType(byte0)
                .setDescription("target-desc-" + permissionTargetId)
                .setName("target-name-" + permissionTargetId)
                .setReferencedIdentifier(new LongIdentifier(0L, BuiltinIdentifierType.TABLE_ITEM))
                .build();

        long actionId = keyGenerator.next();
        CreatePermissionAction action = new CreatePermissionAction.Builder()
                .setId(actionId)
                .setPermissionTargetId(permissionTargetId)
                .setName("read-" + actionId)
                .setReferencedIdentifier(new LongIdentifier(0L, BuiltinIdentifierType.TABLE_ITEM))
                .setDescription("action-desc-" + actionId)
                .build();

        permissionManager.createPermission(target, Collections.singleton(action), opUserId);

        DescribePermission describePermission = permissionManager.getPermission(permissionTargetId);
        DescribePermissionAction describePermissionAction = permissionManager.getPermissionActionById(action.getId());
        List<DescribePermissionAction> describePermissionActions = permissionManager.getPermissionActionByTarget(permissionTargetId);
        System.out.println(describePermission);
        System.out.println(describePermissionAction);
        System.out.println(describePermissionActions);

        permissionManager.deleteTarget(permissionTargetId, opUserId);
        permissionManager.deleteAction(action.getId(), opUserId);
        permissionManager.deleteActionByTarget(permissionTargetId, opUserId);

        CreateResult createResult = permissionManager.createPermissionTarget(target, opUserId);
        permissionManager.createPermissionAction(Collections.singleton(action), opUserId);
        permissionManager.deleteAction(action.getId(), opUserId);
        permissionManager.createPermissionAction(action, opUserId);
        permissionManager.getPermissionTargetById(permissionTargetId);
    }

    private PermissionManager getPermissionManager() {
        PermissionManagerImpl permissionManager = new PermissionManagerImpl();
        permissionManager.setDataSource(getDataSource());
        return permissionManager;
    }
}
