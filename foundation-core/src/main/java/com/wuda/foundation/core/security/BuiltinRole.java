package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.UniqueCodeDescriptor;

/**
 * 内置角色.
 *
 * @author wuda
 * @since 1.0.3
 */
public enum BuiltinRole implements UniqueCodeDescriptor<Long> {

    /**
     * 组中的成员分配的Owner类型的角色,这个组是Store.
     */
    USER_BELONGS_TO_GROUP_STORE_OWNER(1L, "组中的成员分配的Owner类型的角色,这个组是Store."),
    /**
     * 组中的成员分配的Manager类型的角色,这个组是Store.
     */
    USER_BELONGS_TO_GROUP_STORE_MANAGER(1L, "组中的成员分配的Manager类型的角色,这个组是Store."),
    /**
     * 组中的成员分配的Member类型的角色,这个组是Store.
     */
    USER_BELONGS_TO_GROUP_STORE_MEMBER(1L, "组中的成员分配的Member类型的角色,这个组是Store."),
    ;

    /**
     * permission role id.即{@link DescribePermissionRole#getId()}.
     */
    private Long code;
    /**
     * 描述.
     */
    private String description;

    /**
     * 构建内置角色.
     *
     * @param code        {@link #code}
     * @param description {@link #description}
     */
    BuiltinRole(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Long getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
