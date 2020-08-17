package com.wuda.foundation.lang.datatype;

import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;

/**
 * 注册MySQL数据类型.
 *
 * @author wuda
 * @since 1.0.0
 */
final class MySQLDataTypeRegister {

    static void register() {
        MySQLDataType[] mySQLDataTypes = MySQLDataType.values();
        for (MySQLDataType dataType : mySQLDataTypes) {
            DataTypeRegistry.defaultRegistry.register(dataType);
        }
    }
}
