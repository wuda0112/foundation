package com.wuda.foundation.store;

import com.wuda.foundation.lang.Updatable;
import lombok.Getter;

/**
 * 用于更新店铺基本信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateStoreGeneral implements Updatable {

    private String storeName;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private UpdateStoreGeneral() {

    }

    /**
     * 用于创建{@link UpdateStoreGeneral}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateStoreGeneral> {

        private String storeName;

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        @Override
        public UpdateStoreGeneral build() {
            UpdateStoreGeneral createStoreGeneral = new UpdateStoreGeneral();
            if (this.storeName != null) {
                createStoreGeneral.storeName = this.storeName;
            }
            return createStoreGeneral;
        }
    }
}
