package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.DataType;
import com.wuda.foundation.lang.DataTypeRegistry;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CreatePropertyDefinition {

    private Long id;
    private Long propertyKeyId;
    private DataType dataType;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePropertyDefinition> {

        private Long id;
        private Long propertyKeyId;
        private DataType dataType;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPropertyKeyId(Long propertyKeyId) {
            this.propertyKeyId = propertyKeyId;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        @Override
        public CreatePropertyDefinition build() {
            CreatePropertyDefinition createPropertyDefinition = new CreatePropertyDefinition();
            createPropertyDefinition.id = Objects.requireNonNull(this.id);
            createPropertyDefinition.propertyKeyId = Objects.requireNonNull(this.propertyKeyId);
            // 先检查是否能找到
            dataType = DataTypeRegistry.defaultRegistry.getDataType(this.dataType.getFullName());
            createPropertyDefinition.dataType = Objects.requireNonNull(this.dataType);
            return createPropertyDefinition;
        }
    }
}
