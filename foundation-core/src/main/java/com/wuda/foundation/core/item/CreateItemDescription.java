package com.wuda.foundation.core.item;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建item.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateItemDescription {

    private Long id;
    private Long itemId;
    private Long itemVariationId;
    private String content;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateItemDescription() {

    }

    /**
     * 用于创建{@link CreateItemDescription}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItemDescription> {
        private Long id;
        private Long itemId;
        private Long itemVariationId;
        private String content;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setItemVariationId(Long itemVariationId) {
            this.itemVariationId = itemVariationId;
            return this;
        }


        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        @Override
        public CreateItemDescription build() {
            CreateItemDescription createItemDescription = new CreateItemDescription();
            createItemDescription.id = Objects.requireNonNull(id);
            createItemDescription.itemId = Objects.requireNonNull(itemId);
            createItemDescription.itemVariationId = Objects.requireNonNull(itemVariationId);
            createItemDescription.content = content;
            return createItemDescription;
        }
    }
}
