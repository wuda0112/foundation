package com.wuda.foundation.core.commons;

import lombok.Data;

import java.util.Objects;

@Data
public class UpdateMenuItemCategory extends UpdateTreeNode {

    public static class Builder extends UpdateTreeNodeBuilder {

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
        public UpdateMenuItemCategory build() {
            UpdateMenuItemCategory updateItemCategory = new UpdateMenuItemCategory();
            updateItemCategory.id = Objects.requireNonNull(this.id);
            updateItemCategory.name = this.name;
            updateItemCategory.description = this.description;
            return updateItemCategory;
        }
    }
}
