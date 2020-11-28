package com.wuda.foundation.security;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * to {@link Subject}.
     *
     * @return {@link Subject}
     */
    public Subject toSubject() {
        return new Subject(id, null);
    }

    /**
     * to {@link Subject} list.
     *
     * @return {@link Subject}s
     */
    public static List<Subject> toSubjects(List<DescribePermissionRole> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return roles.stream().map(DescribePermissionRole::toSubject).collect(Collectors.toList());
    }

}
