package com.wuda.foundation.core.item;

import com.wuda.foundation.core.commons.CreateGroup;
import com.wuda.foundation.core.commons.CreateTreeNode;
import lombok.Data;

import java.util.Objects;

@Data
public class CreateItemCategory extends CreateTreeNode {

    private Long storeId;

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

    public static class Builder extends CreateTreeNodeBuilder<CreateItemCategory, Builder> {
        private Long storeId;

        public Builder setStoreId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder setParentId(Long parentId) {
            this.parentId = parentId;
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
        public CreateItemCategory build() {
            CreateItemCategory createItemCategory = new CreateItemCategory();
            createItemCategory.id = Objects.requireNonNull(this.id);
            createItemCategory.parentId = Objects.requireNonNull(parentId);
            createItemCategory.storeId = Objects.requireNonNull(this.storeId);
            createItemCategory.name = Objects.requireNonNull(this.name);
            createItemCategory.description = this.description;
            return createItemCategory;
        }
    }
}
