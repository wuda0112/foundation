package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeRegistry;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CreatePropertyKeyDefinition {

    private Long id;
    private Long propertyKeyId;
    private DataType dataType;
    private boolean multiValued = false;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePropertyKeyDefinition> {

        private Long id;
        private Long propertyKeyId;
        private DataType dataType;
        private boolean multiValued = false;

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

        public Builder setMultiValued(boolean multiValued) {
            this.multiValued = multiValued;
            return this;
        }

        @Override
        public CreatePropertyKeyDefinition build() {
            CreatePropertyKeyDefinition createPropertyKeyDefinition = new CreatePropertyKeyDefinition();
            createPropertyKeyDefinition.id = Objects.requireNonNull(this.id);
            createPropertyKeyDefinition.propertyKeyId = Objects.requireNonNull(this.propertyKeyId);
            // 先检查是否能找到
            dataType = DataTypeRegistry.defaultRegistry.lookup(this.dataType.getFullName());
            createPropertyKeyDefinition.dataType = Objects.requireNonNull(this.dataType);
            createPropertyKeyDefinition.multiValued = this.multiValued;
            return createPropertyKeyDefinition;
        }
    }
}
