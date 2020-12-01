package com.wuda.foundation.core.notification;

import com.wuda.foundation.core.commons.CreateGroup;
import com.wuda.foundation.core.commons.CreateTreeNode;
import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification category的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationCategory extends CreateTreeNode {

    /**
     * 生成用于创建组的参数.
     *
     * @return {@link CreateGroup}
     */
    public CreateGroup toCreateGroup() {
        return new CreateGroup.Builder()
                .setGroupId(this.id)
                .setParentGroupId(this.parentId)
                .build();
    }

    /**
     * for build {@link CreateNotificationCategory}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder extends CreateTreeNodeBuilder<CreateNotificationCategory, Builder> {

        @Override
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder setParentId(Long parentId) {
            this.parentId = parentId;
            return this;
        }

        @Override
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public CreateNotificationCategory build() {
            CreateNotificationCategory createNotificationCategory = new CreateNotificationCategory();
            createNotificationCategory.id = Objects.requireNonNull(id);
            createNotificationCategory.parentId = Objects.requireNonNull(parentId);
            createNotificationCategory.name = Objects.requireNonNull(name);
            createNotificationCategory.description = Objects.requireNonNull(description);
            return createNotificationCategory;
        }
    }

}
