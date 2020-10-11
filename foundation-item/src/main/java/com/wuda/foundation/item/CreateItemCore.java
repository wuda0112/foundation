package com.wuda.foundation.item;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建item core.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateItemCore {

    private Long id;
    private Long itemId;
    private Long storeId;
    private Long categoryId;
    private Byte itemType;
    private Byte itemState;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateItemCore() {

    }

    /**
     * 用于创建{@link CreateItemCore}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItemCore> {

        private Long id;
        private Long itemId;
        private Long storeId;
        private Long categoryId;
        private Byte itemType;
        private Byte itemState;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setItemType(Byte itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setItemState(Byte itemState) {
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
        public CreateItemCore build() {
            CreateItemCore createItemCore = new CreateItemCore();
            createItemCore.id = Objects.requireNonNull(this.id);
            createItemCore.itemId = Objects.requireNonNull(this.itemId);
            createItemCore.itemType = Objects.requireNonNull(this.itemType);
            createItemCore.itemState = Objects.requireNonNull(this.itemState);
            createItemCore.categoryId = Objects.requireNonNull(this.categoryId);
            createItemCore.storeId = Objects.requireNonNull(this.storeId);
            return createItemCore;
        }
    }
}
