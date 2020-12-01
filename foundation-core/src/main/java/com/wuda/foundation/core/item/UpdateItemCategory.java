package com.wuda.foundation.core.item;

import com.wuda.foundation.core.commons.UpdateTreeNode;
import lombok.Data;

import java.util.Objects;

@Data
public class UpdateItemCategory extends UpdateTreeNode {

    public static class Builder extends UpdateTreeNode.UpdateTreeNodeBuilder {

        @Override
        public Builder setId(Long id) {
            this.id = id;
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
        public UpdateItemCategory build() {
            UpdateItemCategory updateItemCategory = new UpdateItemCategory();
            updateItemCategory.id = Objects.requireNonNull(this.id);
            updateItemCategory.name = this.name;
            updateItemCategory.description = this.description;
            return updateItemCategory;
        }
    }
}
