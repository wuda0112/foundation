package com.wuda.foundation.test.lang;

import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.commons.property.PropertyKeyTypeSchema;
import com.wuda.foundation.commons.property.PropertyKeyUse;
import com.wuda.foundation.lang.DataType;
import com.wuda.foundation.lang.DataTypeRegistry;
import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorRegistry;
import com.wuda.foundation.lang.datatype.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import org.junit.Test;
import sun.reflect.Reflection;

public class UniqueCodeDescriptorTest {

    @Test
    public void test() {
        UniqueCodeDescriptor uniqueCodeDescriptor = UniqueCodeDescriptorRegistry.defaultRegistry.lookup(PropertyKeyTypeSchema.class, BuiltinPropertyKeyType.ZERO.getCode());
        System.out.println(uniqueCodeDescriptor);
    }

    @Test
    public void testDataTypeRegistry() {
        DataType dataType = DataTypeRegistry.defaultRegistry.lookup(MySQLDataType.VARCHAR.getFullName());
        System.out.println(dataType);
    }

    @Test
    public void testIdentifierTypeRegistry() {
        IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(BuiltinIdentifierType.TABLE_ITEM.getCode());
        System.out.println(identifierType);
    }

    @Test
    public void testSupperClass(){
        Class[] supperClass = null;
        while (( supperClass = BuiltinPropertyKeyUse.ZERO.getClass().getInterfaces()) != null){
            System.out.println(supperClass);
        }
        System.out.println(supperClass);
    }
}
