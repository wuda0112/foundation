package com.wuda.foundation.store;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建店铺.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateStore {

    private Long id;
    private StoreType storeType;
    private StoreState storeState;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateStore() {

    }

    /**
     * 用于创建{@link CreateStore}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateStore> {

        private Long id;
        private StoreType storeType;
        private StoreState storeState;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStoreType(StoreType storeType) {
            this.storeType = storeType;
            return this;
        }

        public Builder setStoreState(StoreState storeState) {
            this.storeState = storeState;
            return this;
        }

        @Override
        public CreateStore build() {
            CreateStore createStore = new CreateStore();
            createStore.id = Objects.requireNonNull(this.id);
            createStore.storeType = Objects.requireNonNull(this.storeType);
            createStore.storeState = Objects.requireNonNull(this.storeState);
            return createStore;
        }
    }
}
