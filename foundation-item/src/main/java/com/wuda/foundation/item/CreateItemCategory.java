package com.wuda.foundation.item;

import com.wuda.foundation.commons.CreateTreeNode;
import lombok.Data;

import java.util.Objects;

@Data
public class CreateItemCategory extends CreateTreeNode {
    private Long storeId;

    public static class Builder extends CreateTreeNode.Builder {
        private Long storeId;

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

        public Builder setParentNodeId(Long parentNodeId) {
            this.parentNodeId = parentNodeId;
            return this;
        }

        @Override
        public CreateItemCategory build() {
            CreateItemCategory createItemCategory = new CreateItemCategory();
            createItemCategory.id = Objects.requireNonNull(this.id);
            createItemCategory.name = Objects.requireNonNull(this.name);
            createItemCategory.description = this.description;
            createItemCategory.parentNodeId = Objects.requireNonNull(parentNodeId);
            createItemCategory.storeId = Objects.requireNonNull(storeId);
            return createItemCategory;
        }
    }
}
