package com.wuda.foundation.test.lang;

import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.PropertyKeyTypeSchema;
import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorRegistry;
import org.junit.Assert;
import org.junit.Test;

public class UniqueCodeDescriptorTest {

    @Test
    public void test() {
        BuiltinPropertyKeyType.ZERO.register();
        UniqueCodeDescriptor uniqueCodeDescriptor = UniqueCodeDescriptorRegistry.defaultRegistry.lookup(PropertyKeyTypeSchema.class, BuiltinPropertyKeyType.ZERO.getCode());
        Assert.assertTrue(uniqueCodeDescriptor instanceof BuiltinPropertyKeyType);
    }
}
