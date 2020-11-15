package com.wuda.foundation.user;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建用户在组中的角色信息.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateUserBelongsToGroupRoleRequest {

    private Long id;
    private Long userBelongsToGroupId;
    private Long permissionRoleId;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserBelongsToGroupRoleRequest() {

    }

    /**
     * 用于创建{@link CreateUserBelongsToGroupRoleRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserBelongsToGroupRoleRequest> {

        private Long id;
        private Long userBelongsToGroupId;
        private Long permissionRoleId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserBelongsToGroupId(Long userBelongsToGroupId) {
            this.userBelongsToGroupId = userBelongsToGroupId;
            return this;
        }

        public Builder setPermissionRoleId(Long permissionRoleId) {
            this.permissionRoleId = permissionRoleId;
            return this;
        }

        @Override
        public CreateUserBelongsToGroupRoleRequest build() {
            CreateUserBelongsToGroupRoleRequest request = new CreateUserBelongsToGroupRoleRequest();
            request.id = Objects.requireNonNull(this.id);
            request.userBelongsToGroupId = Objects.requireNonNull(this.userBelongsToGroupId);
            request.permissionRoleId = Objects.requireNonNull(this.permissionRoleId);
            return request;
        }
    }
}
