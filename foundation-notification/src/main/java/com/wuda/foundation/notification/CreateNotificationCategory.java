package com.wuda.foundation.notification;

import com.wuda.foundation.commons.BuiltinTreeNodeUse;
import com.wuda.foundation.commons.CreateGroup;
import com.wuda.foundation.commons.CreateTreeNode;
import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.identify.BuiltinIdentifierTypes;
import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification category的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationCategory {

    private Long id;
    private String name;
    private String description;
    private Long parentCategoryId;

    /**
     * 生成用于创建树节点的参数.
     *
     * @return {@link CreateTreeNode}
     */
    public CreateTreeNode toCreateTreeNode() {
        return new CreateTreeNode.Builder()
                .setId(this.id)
                .setParentNodeId(this.parentCategoryId)
                .build();
    }

    /**
     * 生成用于创建组的参数.
     *
     * @return {@link CreateGroup}
     */
    public CreateGroup toCreateGroup() {
        return new CreateGroup.Builder()
                .setGroupId(this.id)
                .setParentGroupId(this.parentCategoryId)
                .build();
    }

    /**
     * for build {@link CreateNotificationCategory}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationCategory> {

        private Long id;
        private String name;
        private String description;
        private Long parentCategoryId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setParentCategoryId(Long parentCategoryId) {
            this.parentCategoryId = parentCategoryId;
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
        public CreateNotificationCategory build() {
            CreateNotificationCategory createNotificationCategory = new CreateNotificationCategory();
            createNotificationCategory.id = Objects.requireNonNull(id);
            createNotificationCategory.parentCategoryId = Objects.requireNonNull(parentCategoryId);
            createNotificationCategory.name = Objects.requireNonNull(name);
            createNotificationCategory.description = Objects.requireNonNull(description);
            return createNotificationCategory;
        }
    }

}
