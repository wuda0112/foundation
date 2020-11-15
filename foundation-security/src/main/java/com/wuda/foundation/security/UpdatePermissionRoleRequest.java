package com.wuda.foundation.security;

import lombok.Getter;

import java.util.Objects;

/**
 * update permission role request.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdatePermissionRoleRequest {

    private Long id;
    private String name;
    private String description;

    /**
     * 使用Builder创建实例.
     */
    private UpdatePermissionRoleRequest() {

    }

    /**
     * for build {@link UpdatePermissionRoleRequest}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdatePermissionRoleRequest> {

        private Long id;
        private String name;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public UpdatePermissionRoleRequest build() {
            UpdatePermissionRoleRequest request = new UpdatePermissionRoleRequest();
            request.id = Objects.requireNonNull(id);
            request.name = Objects.requireNonNull(name);
            request.description = Objects.requireNonNull(description);
            return request;
        }
    }

}
