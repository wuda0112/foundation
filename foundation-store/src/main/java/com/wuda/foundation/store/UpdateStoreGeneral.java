package com.wuda.foundation.store;

import com.wuda.foundation.lang.Updatable;
import lombok.Getter;

import java.util.Objects;

/**
 * 用于更新店铺基本信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateStoreGeneral implements Updatable {

    private Long id;
    private String storeName;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private UpdateStoreGeneral() {

    }

    /**
     * 当执行create or update时有用.
     *
     * @param createStoreGeneral 创建参数
     * @return 更新参数
     */
    public static UpdateStoreGeneral from(Long storeGeneralId, CreateStoreGeneral createStoreGeneral) {
        return new UpdateStoreGeneral.Builder()
                .setId(storeGeneralId)
                .setStoreName(createStoreGeneral.getStoreName())
                .build();
    }

    /**
     * 用于创建{@link UpdateStoreGeneral}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateStoreGeneral> {

        private Long id;
        private String storeName;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        @Override
        public UpdateStoreGeneral build() {
            UpdateStoreGeneral createStoreGeneral = new UpdateStoreGeneral();
            createStoreGeneral.id = Objects.requireNonNull(id);
            if (this.storeName != null) {
                createStoreGeneral.storeName = this.storeName;
            }
            return createStoreGeneral;
        }
    }
}
