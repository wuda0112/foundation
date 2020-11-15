package com.wuda.foundation.security;

import lombok.Getter;

import java.util.Objects;

/**
 * create permission role request.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreatePermissionRoleRequest {

    private Long id;
    private PermissionRoleType type;
    private String name;
    private String description;

    /**
     * 使用Builder创建实例.
     */
    private CreatePermissionRoleRequest() {

    }

    /**
     * for build {@link CreatePermissionRoleRequest}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePermissionRoleRequest> {

        private Long id;
        private PermissionRoleType type;
        private String name;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }


        public Builder setPermissionRoleType(PermissionRoleType type) {
            this.type = type;
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
        public CreatePermissionRoleRequest build() {
            CreatePermissionRoleRequest request = new CreatePermissionRoleRequest();
            request.id = Objects.requireNonNull(id);
            request.type = Objects.requireNonNull(type);
            request.name = Objects.requireNonNull(name);
            request.description = Objects.requireNonNull(description);
            return request;
        }
    }

}
