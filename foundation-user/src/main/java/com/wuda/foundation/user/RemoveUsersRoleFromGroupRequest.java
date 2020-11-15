package com.wuda.foundation.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 移除用户在组的角色.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class RemoveUsersRoleFromGroupRequest {

    private Long userId;
    private LongIdentifier group;
    private Long permissionRoleId;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private RemoveUsersRoleFromGroupRequest() {

    }

    /**
     * 用于创建{@link RemoveUsersRoleFromGroupRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<RemoveUsersRoleFromGroupRequest> {

        private Long userId;
        private LongIdentifier group;
        private Long permissionRoleId;

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
        public RemoveUsersRoleFromGroupRequest build() {
            RemoveUsersRoleFromGroupRequest request = new RemoveUsersRoleFromGroupRequest();
            request.userId = Objects.requireNonNull(this.userId);
            request.group = Objects.requireNonNull(this.group);
            request.permissionRoleId = Objects.requireNonNull(this.permissionRoleId);
            return request;
        }
    }
}
