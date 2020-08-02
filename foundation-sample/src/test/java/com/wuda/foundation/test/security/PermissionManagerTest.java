package com.wuda.foundation.test.security;

import com.wuda.foundation.TestBase;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.*;
import com.wuda.foundation.security.impl.PermissionManagerImpl;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class PermissionManagerTest extends TestBase {

    @Test
    public void testCreatePermission() {
        PermissionManager permissionManager = getPermissionManager();
        long permissionTargetId = keyGenerator.next();
        CreatePermissionTarget target = new CreatePermissionTarget.Builder()
                .setId(permissionTargetId)
                .setCategoryId(keyGenerator.next())
                .setPermissionTargetType(BuiltinPermissionTargetType.ZERO)
                .setDescription("target-desc")
                .setName("target-name")
                .setReferencedIdentifier(new LongIdentifier(0L,BuiltinIdentifierType.MOCK))
                .build();

        PermissionActionName permissionActionName = new PermissionActionName() {
            @Override
            public String getCode() {
                return "action-name";
            }

            @Override
            public String getDescription() {
                return "action-desc";
            }
        };
        permissionActionName.register();
        CreatePermissionAction action = new CreatePermissionAction.Builder()
                .setId(keyGenerator.next())
                .setPermissionTargetId(permissionTargetId)
                .setPermissionActionName(permissionActionName)
                .setReferencedIdentifier(new LongIdentifier(0L, BuiltinIdentifierType.MOCK))
                .setDescription("action-desc")
                .build();

        permissionManager.createPermission(target, Collections.singleton(action), opUserId);

        Permission permission = permissionManager.getPermission(permissionTargetId);
        DescribePermissionAction describePermissionAction = permissionManager.getPermissionActionById(action.getId());
        List<DescribePermissionAction> describePermissionActions = permissionManager.getPermissionActionByTarget(permissionTargetId);
        System.out.println(permission);
        System.out.println(describePermissionAction);
        System.out.println(describePermissionActions);

        permissionManager.deleteTarget(permissionTargetId, opUserId);
        permissionManager.deleteAction(action.getId(), opUserId);
    }

    private PermissionManager getPermissionManager() {
        PermissionManagerImpl permissionManager = new PermissionManagerImpl();
        permissionManager.setDataSource(getDataSource());
        return permissionManager;
    }
}
