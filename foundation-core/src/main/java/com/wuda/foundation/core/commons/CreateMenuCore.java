package com.wuda.foundation.core.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for create menu core.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateMenuCore {

    private Long id;
    private Long menuId;
    private Long opUserId;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateMenuCore() {

    }

    /**
     * 用于创建{@link CreateMenuCore}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateMenuCore> {

        private Long id;
        private Long menuId;
        private Long opUserId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setMenuId(Long menuId) {
            this.menuId = menuId;
            return this;
        }

        public Builder setOpUserId(Long opUserId) {
            this.opUserId = opUserId;
            return this;
        }

        @Override
        public CreateMenuCore build() {
            CreateMenuCore createMenuItemCore = new CreateMenuCore();
            createMenuItemCore.id = Objects.requireNonNull(this.id);
            createMenuItemCore.menuId = Objects.requireNonNull(this.menuId);
            createMenuItemCore.opUserId = Objects.requireNonNull(opUserId);
            return createMenuItemCore;
        }
    }
}
