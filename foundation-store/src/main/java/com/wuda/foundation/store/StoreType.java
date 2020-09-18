package com.wuda.foundation.store;

import com.wuda.foundation.lang.AbstractUniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorRegistry;

public class StoreType extends AbstractUniqueCodeDescriptor<Byte> {

    /**
     * 构造实例,并且将自己注册到{@link UniqueCodeDescriptorRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    protected StoreType(Byte code, String description) {
        super(code, description);
    }

    @Override
    public Class<Schema> getSchemaClass() {
        return Schema.class;
    }

    protected class Schema implements UniqueCodeDescriptor.Schema {

    }
}
