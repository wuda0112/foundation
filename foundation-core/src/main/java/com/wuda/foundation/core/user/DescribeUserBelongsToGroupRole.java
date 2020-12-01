package com.wuda.foundation.core.user;

import lombok.Data;

/**
 * 描述用户在组中的角色.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribeUserBelongsToGroupRole {

    private Long id;
    private Long userBelongsToGroupId;
    private Long permissionRoleId;
}
