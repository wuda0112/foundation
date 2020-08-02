package com.wuda.foundation.commons.property;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于update property value.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdatePropertyValue {

    private Long id;
    private String value;

    public static class Builder implements com.wuda.foundation.lang.Builder<UpdatePropertyValue> {

        private Long id;
        private String value;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public UpdatePropertyValue build() {
            UpdatePropertyValue updatePropertyValue = new UpdatePropertyValue();
            updatePropertyValue.id = Objects.requireNonNull(this.id);
            updatePropertyValue.value = Objects.requireNonNull(this.value);
            return updatePropertyValue;
        }
    }
}
