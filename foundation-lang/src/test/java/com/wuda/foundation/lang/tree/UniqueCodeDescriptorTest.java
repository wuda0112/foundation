package com.wuda.foundation.lang.tree;

import com.wuda.foundation.lang.AbstractUniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorRegistry;
import org.junit.Test;

public class UniqueCodeDescriptorTest {

    public static UniqueCodeDescriptor<Long> one = new AbstractUniqueCodeDescriptor<Long>(1L, "0") {
        @Override
        public Class<Schema> getSchemaClass() {
            return UniqueCodeDescriptor.Schema.class;
        }
    };

    public static UniqueCodeDescriptor<Long> tow = new AbstractUniqueCodeDescriptor<Long>(2L, "0") {
        @Override
        public Class<Schema> getSchemaClass() {
            return UniqueCodeDescriptor.Schema.class;
        }
    };

    @Test
    public void test() {
        UniqueCodeDescriptor descriptor = UniqueCodeDescriptorRegistry.defaultRegistry.lookup(UniqueCodeDescriptor.Schema.class,1L);
        System.out.println(descriptor.getCode());
    }
}
