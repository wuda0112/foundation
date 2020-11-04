package com.wuda.foundation.item;

import com.wuda.foundation.commons.CreateGroup;
import com.wuda.foundation.commons.CreateTreeNode;
import lombok.Data;

import java.util.Objects;

@Data
public class CreateItemCategory {

    private Long id;
    private Long storeId;
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
                .setParentTreeNodeId(this.parentCategoryId)
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

    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItemCategory> {
        private Long id;
        private Long storeId;
        private String name;
        private String description;
        private Long parentCategoryId;

        public Builder setStoreId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

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

        public Builder setParentCategoryId(Long parentCategoryId) {
            this.parentCategoryId = parentCategoryId;
            return this;
        }

        @Override
        public CreateItemCategory build() {
            CreateItemCategory createItemCategory = new CreateItemCategory();
            createItemCategory.id = Objects.requireNonNull(this.id);
            createItemCategory.storeId = Objects.requireNonNull(this.storeId);
            createItemCategory.name = Objects.requireNonNull(this.name);
            createItemCategory.description = this.description;
            createItemCategory.parentCategoryId = Objects.requireNonNull(parentCategoryId);
            return createItemCategory;
        }
    }
}
