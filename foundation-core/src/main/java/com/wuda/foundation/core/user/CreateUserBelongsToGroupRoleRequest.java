package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
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
    private Long userId;
    private LongIdentifier group;
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
        private Long userId;
        private LongIdentifier group;
        private Long permissionRoleId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setGroup(LongIdentifier group) {
            this.group = group;
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
            request.userId = Objects.requireNonNull(this.userId);
            request.group = Objects.requireNonNull(this.group);
            request.permissionRoleId = Objects.requireNonNull(this.permissionRoleId);
            return request;
        }
    }
}
