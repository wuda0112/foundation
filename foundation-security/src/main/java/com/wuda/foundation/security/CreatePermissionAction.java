package com.wuda.foundation.security;

import lombok.Getter;

import java.util.Objects;

/**
 * 表示permission允许执行的动作,比如文件的read/write.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePermissionAction {

    /**
     * id.
     */
    private Long id;

    /**
     * 所属的permission target的id.
     */
    private Long permissionTargetId;

    /**
     * action.
     */
    private PermissionActionName name;
    /**
     * 描述.
     */
    private String description;

    /**
     * 关联的外部对象的identifier.
     */
    private PermissionActionReferencedIdentifier referencedIdentifier;

    /**
     * for build {@link CreatePermissionTarget}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePermissionAction> {

        private Long id;
        private Long permissionTargetId;
        private PermissionActionName name;
        private String description;

        /**
         * 关联的外部对象的identifier.
         */
        private PermissionActionReferencedIdentifier referencedIdentifier;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPermissionTargetId(Long permissionTargetId) {
            this.permissionTargetId = permissionTargetId;
            return this;
        }

        public Builder setPermissionActionName(PermissionActionName name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPermissionActionReferencedIdentifier(PermissionActionReferencedIdentifier referencedIdentifier) {
            this.referencedIdentifier = referencedIdentifier;
            return this;
        }

        @Override
        public CreatePermissionAction build() {
            CreatePermissionAction createPermissionAction = new CreatePermissionAction();
            createPermissionAction.id = Objects.requireNonNull(id);
            createPermissionAction.permissionTargetId = Objects.requireNonNull(permissionTargetId);
            createPermissionAction.name = Objects.requireNonNull(name);
            createPermissionAction.referencedIdentifier = Objects.requireNonNull(referencedIdentifier);
            createPermissionAction.description = Objects.requireNonNull(description);
            return createPermissionAction;
        }
    }

}
