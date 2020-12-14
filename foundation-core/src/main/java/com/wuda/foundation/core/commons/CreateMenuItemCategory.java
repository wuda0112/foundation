package com.wuda.foundation.core.commons;

import lombok.Data;

import java.util.Objects;

@Data
public class CreateMenuItemCategory extends CreateTreeNode {

    private Long menuId;

    public static class Builder extends CreateTreeNodeBuilder<CreateMenuItemCategory, Builder> {
        private Long menuId;

        public Builder setMenuId(Long menuId) {
            this.menuId = menuId;
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
        public CreateMenuItemCategory build() {
            CreateMenuItemCategory createItemCategory = new CreateMenuItemCategory();
            createItemCategory.id = Objects.requireNonNull(this.id);
            createItemCategory.parentId = Objects.requireNonNull(parentId);
            createItemCategory.menuId = Objects.requireNonNull(this.menuId);
            createItemCategory.name = Objects.requireNonNull(this.name);
            createItemCategory.description = this.description;
            return createItemCategory;
        }
    }
}
