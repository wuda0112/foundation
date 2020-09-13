package com.wuda.foundation.item;

import com.wuda.foundation.commons.UpdateTreeNode;
import lombok.Data;

import java.util.Objects;

@Data
public class UpdateItemCategory extends UpdateTreeNode {

    public static class Builder extends UpdateTreeNode.Builder {

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
        public UpdateItemCategory build() {
            UpdateItemCategory createItemCategory = new UpdateItemCategory();
            createItemCategory.id = Objects.requireNonNull(this.id);
            createItemCategory.name = Objects.requireNonNull(this.name);
            createItemCategory.description = this.description;
            return createItemCategory;
        }
    }
}
