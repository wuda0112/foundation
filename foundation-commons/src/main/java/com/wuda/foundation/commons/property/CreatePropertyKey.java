package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.identify.Identifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建property key.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePropertyKey {

    private Long id;
    private Identifier<Long> owner;
    private String key;
    private Byte type;
    private Byte use;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePropertyKey> {

        private Long id;
        private Identifier<Long> owner;
        private String key;
        private Byte type;
        private Byte use;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setOwner(Identifier<Long> owner) {
            this.owner = owner;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setType(Byte type) {
            this.type = type;
            return this;
        }

        public Builder setUse(Byte use) {
            this.use = use;
            return this;
        }

        @Override
        public CreatePropertyKey build() {
            CreatePropertyKey createPropertyKey = new CreatePropertyKey();
            createPropertyKey.id = Objects.requireNonNull(this.id);
            createPropertyKey.owner = Objects.requireNonNull(this.owner);
            createPropertyKey.key = Objects.requireNonNull(this.key);
            createPropertyKey.type = Objects.requireNonNull(this.type);
            createPropertyKey.use = Objects.requireNonNull(this.use);
            return createPropertyKey;
        }
    }
}
