package com.wuda.foundation.security;

import com.wuda.foundation.lang.CRUDEntity;
import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.IdValidator;
import lombok.Getter;

import java.util.Objects;

/**
 * permission action..
 */
@Getter
public class PermissionAction extends CRUDEntity {

    /**
     * id.
     */
    private Long permissionActionId;

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
     * 关联的外部对象的类型.
     */
    private PermissionActionReferencedType referencedType;

    /**
     * 关联的外部对象的identifier.
     */
    private Long referencedIdentifier;

    @Override
    protected void forCreateCheck() {
        forCreateCheckShortcut0();
        IdValidator.notEqualsZeroAndGreaterThanMin(permissionActionId);
        Objects.requireNonNull(permissionTargetId);
        Objects.requireNonNull(name);
        forCreateCheckAboutReferencedObject();
    }

    @Override
    protected void forUpdateCheck() {
        forUpdateCheckShortcut0();
        IdValidator.notEqualsZeroAndGreaterThanMin(permissionActionId);
        forUpdateCheckAboutReferencedObject();
    }

    /**
     * 关联外部对象的检查.
     */
    private void forCreateCheckAboutReferencedObject() {
        boolean eitherAllNullOrAllNonNull = false;
        if ((referencedType != null && referencedIdentifier != null)
                || (referencedType == null && referencedIdentifier == null)) {
            eitherAllNullOrAllNonNull = true;
        }
        if (!eitherAllNullOrAllNonNull) {
            throw new IllegalStateException("referencedType和referencedIdentifier或者全部不为null,表示关联了外部对象;或者全部为空,表示没有关联外部对象,不能一个为null,一个不为null");
        }
    }

    /**
     * 关联外部对象的检查.
     */
    private void forUpdateCheckAboutReferencedObject() {
        if (referencedType != null || referencedIdentifier != null) {
            throw new IllegalStateException("不能更新关联的外部对象的引用");
        }
    }

    public static class Builder extends CRUDEntityBuilder<PermissionAction,Builder> {

        /**
         * 即将被构建的{@link PermissionAction}的CRUD状态.
         *
         * @param crudState {@link CRUDEntityState}
         */
        protected Builder(CRUDEntityState crudState) {
            super(crudState);
        }

        @Override
        public PermissionAction build() {
            return null;
        }

        @Override
        public Builder createTime(Long createTime) {
            return null;
        }

        @Override
        public Builder createUserId(Long createUserId) {
            return null;
        }

        @Override
        public Builder lastModifyTime(Long lastModifyTime) {
            return null;
        }

        @Override
        public Builder lastModifyUserId(Long lastModifyUserId) {
            return null;
        }
    }

}
