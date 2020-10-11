package com.wuda.foundation.store;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建店铺核心信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateStoreCore {

    private Long id;
    private Long storeId;
    private Byte storeType;
    private Byte storeState;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateStoreCore() {

    }

    /**
     * 用于创建{@link CreateStoreCore}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateStoreCore> {

        private Long id;
        private Long storeId;
        private Byte storeType;
        private Byte storeState;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStoreId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder setStoreType(Byte storeType) {
            this.storeType = storeType;
            return this;
        }

        public Builder setStoreState(Byte storeState) {
            this.storeState = storeState;
            return this;
        }

        @Override
        public CreateStoreCore build() {
            CreateStoreCore createStoreCore = new CreateStoreCore();
            createStoreCore.id = Objects.requireNonNull(this.id);
            createStoreCore.storeId = Objects.requireNonNull(this.storeId);
            createStoreCore.storeType = Objects.requireNonNull(this.storeType);
            createStoreCore.storeState = Objects.requireNonNull(this.storeState);
            return createStoreCore;
        }
    }
}
