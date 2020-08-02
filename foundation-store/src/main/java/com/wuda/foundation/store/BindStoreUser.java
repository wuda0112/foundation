package com.wuda.foundation.store;

import lombok.Getter;

import java.util.Objects;

/**
 * 绑定用户和店铺的关系.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class BindStoreUser {

    private Long id;
    private Long storeId;
    private Long userId;
    private Boolean isStoreOwner;

    public static class Builder implements com.wuda.foundation.lang.Builder<BindStoreUser> {

        private Long id;
        private Long storeId;
        private Long userId;
        private Boolean isStoreOwner;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStoreId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder isStoreOwner(Boolean isStoreOwner) {
            this.isStoreOwner = isStoreOwner;
            return this;
        }

        @Override
        public BindStoreUser build() {
            BindStoreUser bindStoreUser = new BindStoreUser();
            bindStoreUser.id = Objects.requireNonNull(this.id);
            bindStoreUser.storeId = Objects.requireNonNull(this.storeId);
            bindStoreUser.userId = Objects.requireNonNull(this.userId);
            bindStoreUser.isStoreOwner = Objects.requireNonNull(this.isStoreOwner);
            return bindStoreUser;
        }
    }
}
