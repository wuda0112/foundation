package com.wuda.foundation.core.security;

import lombok.Getter;

import java.util.Objects;

/**
 * 更新target.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdatePermissionTarget {

    private Long id;
    private String name;
    private String description;

    /**
     * for build {@link UpdatePermissionTarget}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdatePermissionTarget> {

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
        public UpdatePermissionTarget build() {
            UpdatePermissionTarget updatePermissionTarget = new UpdatePermissionTarget();
            updatePermissionTarget.id = Objects.requireNonNull(id);
            updatePermissionTarget.name = name;
            updatePermissionTarget.description = description;
            return updatePermissionTarget;
        }
    }
}
