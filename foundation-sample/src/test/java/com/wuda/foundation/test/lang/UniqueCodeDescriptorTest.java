package com.wuda.foundation.test.lang;

import com.wuda.foundation.lang.datatype.DataType;
import com.wuda.foundation.lang.datatype.DataTypeRegistry;
import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.IdentifierTypeRegistry;
import org.junit.Test;

public class UniqueCodeDescriptorTest {

    @Test
    public void testDataTypeRegistry() {
        DataType dataType = DataTypeRegistry.defaultRegistry.lookup(MySQLDataType.INT.getFullName());
        System.out.println(dataType);
    }

    @Test
    public void testIdentifierTypeRegistry() {
        IdentifierType identifierType = IdentifierTypeRegistry.defaultRegistry.lookup(BuiltinIdentifierType.TABLE_ITEM.getCode());
        System.out.println(identifierType);
    }
}
