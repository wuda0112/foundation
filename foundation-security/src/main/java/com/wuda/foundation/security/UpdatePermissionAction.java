package com.wuda.foundation.security;

import lombok.Getter;

import java.util.Objects;

/**
 * 更新action.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdatePermissionAction {

    private Long id;
    private String name;
    private String description;

    /**
     * for build {@link CreatePermissionTarget}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdatePermissionAction> {

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
        public UpdatePermissionAction build() {
            UpdatePermissionAction updatePermissionAction = new UpdatePermissionAction();
            updatePermissionAction.id = Objects.requireNonNull(this.id);
            updatePermissionAction.name = this.name;
            updatePermissionAction.description = this.description;
            return updatePermissionAction;
        }
    }
}
