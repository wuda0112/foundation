package com.wuda.foundation.commons.property;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建property key.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePropertyKeyWithDefinition {

    private CreatePropertyKey propertyKey;
    private CreatePropertyKeyDefinition definition;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePropertyKeyWithDefinition> {

        private CreatePropertyKey propertyKey;
        private CreatePropertyKeyDefinition definition;

        public Builder setCreatePropertyKey(CreatePropertyKey propertyKey) {
            this.propertyKey = propertyKey;
            return this;
        }

        public Builder setCreatePropertyDefinition(CreatePropertyKeyDefinition definition) {
            this.definition = definition;
            return this;
        }

        @Override
        public CreatePropertyKeyWithDefinition build() {
            CreatePropertyKeyWithDefinition createPropertyKeyWithDefinition = new CreatePropertyKeyWithDefinition();
            createPropertyKeyWithDefinition.propertyKey = Objects.requireNonNull(this.propertyKey);
            createPropertyKeyWithDefinition.definition = Objects.requireNonNull(this.definition);
            if (!this.propertyKey.getId().equals(this.definition.getPropertyKeyId())) {
                throw new IllegalArgumentException("property definition中的key id和对应的key的id不一致");
            }
            return createPropertyKeyWithDefinition;
        }
    }
}
