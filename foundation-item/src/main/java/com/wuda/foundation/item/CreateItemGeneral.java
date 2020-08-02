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

    private Long id;
    private Long itemId;
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
        private Long id;
        private Long itemId;
        private String name;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        @Override
        public CreateItemGeneral build() {
            CreateItemGeneral createItemGeneral = new CreateItemGeneral();
            createItemGeneral.id = Objects.requireNonNull(id);
            createItemGeneral.itemId = Objects.requireNonNull(itemId);
            createItemGeneral.name = Objects.requireNonNull(this.name);
            return createItemGeneral;
        }
    }
}
