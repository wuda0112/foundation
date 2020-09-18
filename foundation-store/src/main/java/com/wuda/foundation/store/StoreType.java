package com.wuda.foundation.store;

import com.wuda.foundation.lang.AbstractUniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorRegistry;

/**
 * store type.虽然该类可以通过<i>new</i>关键字任意实例化,但是
 * 你应该把店铺类型作为常量,就好像{@link BuiltinStoreTypes}中定义的一样.
 *
 * @author wuda
 * @since 1.0.3
 */
public class StoreType extends AbstractUniqueCodeDescriptor<Byte> {

    /**
     * 构造实例,并且将自己注册到{@link UniqueCodeDescriptorRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    public StoreType(Byte code, String description) {
        super(code, description);
    }

    @Override
    public final Class<Schema> getSchemaClass() {
        return Schema.class;
    }

    /**
     * store type的schema.
     *
     * @author wuda
     */
    public final class Schema implements UniqueCodeDescriptor.Schema {

    }
}
