package com.wuda.foundation.security;

import lombok.Data;

/**
 * create permission role request.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribePermissionRole {

    private Long id;
    private PermissionRoleType type;
    private String name;
    private String description;

}
