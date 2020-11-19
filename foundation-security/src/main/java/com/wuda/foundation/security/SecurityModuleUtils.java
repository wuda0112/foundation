package com.wuda.foundation.security;

import com.wuda.foundation.lang.Constant;

/**
 * security模块的工具类.
 *
 * @author wuda
 * @since 1.0.3
 */
public class SecurityModuleUtils {

    public static boolean toWholeTarget(DescribePermissionAssignment permissionAssignment) {
        return permissionAssignment.getActionId() == Constant.NOT_EXISTS_ID;
    }
}
