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
public class CreateItemGeneral {

    private String name;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateItemGeneral() {

    }

    /**
     * 用于创建{@link CreateItemGeneral}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItemGeneral> {

        private String name;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public CreateItemGeneral build() {
            CreateItemGeneral createItemGeneral = new CreateItemGeneral();
            createItemGeneral.name = Objects.requireNonNull(this.name);
            return createItemGeneral;
        }
    }
}
