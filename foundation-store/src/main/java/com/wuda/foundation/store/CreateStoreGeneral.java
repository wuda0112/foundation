package com.wuda.foundation.store;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建店铺基本信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateStoreGeneral {

    private String storeName;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateStoreGeneral() {

    }

    /**
     * 用于创建{@link CreateStoreGeneral}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateStoreGeneral> {

        private String storeName;

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        @Override
        public CreateStoreGeneral build() {
            CreateStoreGeneral createStoreGeneral = new CreateStoreGeneral();
            createStoreGeneral.storeName = Objects.requireNonNull(this.storeName);
            return createStoreGeneral;
        }
    }
}
