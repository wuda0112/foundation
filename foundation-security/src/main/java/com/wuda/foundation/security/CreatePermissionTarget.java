package com.wuda.foundation.security;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 表示permission作用的目标对象,比如文件.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePermissionTarget {

    /**
     * id.
     */
    private Long id;

    /**
     * 所属分类的ID
     */
    private Long categoryId;

    /**
     * 名称.
     */
    private String name;
    /**
     * 作用对象的类型,比如文件.
     */
    private PermissionTargetType type;
    /**
     * 关联的外部对象的identifier.
     */
    private LongIdentifier referencedIdentifier;
    /**
     * 描述.
     */
    private String description;

    /**
     * for build {@link CreatePermissionTarget}
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePermissionTarget> {

        private Long id;
        private Long categoryId;
        private String name;
        private PermissionTargetType type;
        private LongIdentifier referencedIdentifier;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPermissionTargetType(PermissionTargetType type) {
            this.type = type;
            return this;
        }

        public Builder setReferencedIdentifier(LongIdentifier referencedIdentifier) {
            this.referencedIdentifier = referencedIdentifier;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public CreatePermissionTarget build() {
            CreatePermissionTarget createPermissionTarget = new CreatePermissionTarget();
            createPermissionTarget.id = Objects.requireNonNull(id);
            createPermissionTarget.categoryId = Objects.requireNonNull(categoryId);
            createPermissionTarget.name = Objects.requireNonNull(name);
            createPermissionTarget.type = Objects.requireNonNull(type);
            createPermissionTarget.referencedIdentifier = Objects.requireNonNull(referencedIdentifier);
            createPermissionTarget.description = Objects.requireNonNull(description);
            return createPermissionTarget;
        }
    }
}
