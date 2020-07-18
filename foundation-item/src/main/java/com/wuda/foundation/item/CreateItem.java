package com.wuda.foundation.item;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建item.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateItem {

    private Long storeId;
    private Long categoryId;
    private ItemType itemType;
    private ItemState itemState;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateItem() {

    }

    /**
     * 用于创建{@link CreateItem}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItem> {

        private Long storeId;
        private Long categoryId;
        private ItemType itemType;
        private ItemState itemState;

        public Builder setItemType(ItemType itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setItemState(ItemState itemState) {
            this.itemState = itemState;
            return this;
        }

        public Builder setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setStoreId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

        @Override
        public CreateItem build() {
            CreateItem createItem = new CreateItem();
            createItem.itemType = Objects.requireNonNull(this.itemType);
            createItem.itemState = Objects.requireNonNull(this.itemState);
            createItem.categoryId = Objects.requireNonNull(this.categoryId);
            createItem.storeId = Objects.requireNonNull(this.storeId);
            return createItem;
        }
    }
}
