package com.wuda.foundation.commons.property;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建property value.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePropertyValue {

    private Long id;
    private Long propertyKeyId;
    private String value;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePropertyValue> {

        private Long id;
        private Long propertyKeyId;
        private String value;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPropertyKeyId(Long propertyKeyId) {
            this.propertyKeyId = propertyKeyId;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public CreatePropertyValue build() {
            CreatePropertyValue createPropertyKey = new CreatePropertyValue();
            createPropertyKey.id = Objects.requireNonNull(this.id);
            createPropertyKey.propertyKeyId = Objects.requireNonNull(this.propertyKeyId);
            createPropertyKey.value = Objects.requireNonNull(this.value);
            return createPropertyKey;
        }
    }
}
