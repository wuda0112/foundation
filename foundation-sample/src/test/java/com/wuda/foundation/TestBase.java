package com.wuda.foundation;

import com.wuda.foundation.commons.property.BuiltinPropertyKeyType;
import com.wuda.foundation.commons.property.BuiltinPropertyKeyUse;
import com.wuda.foundation.lang.datatype.mysql.MySQLDataType;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;

import javax.sql.DataSource;

public class TestBase {

    protected KeyGeneratorSnowflake keyGenerator = new KeyGeneratorSnowflake(1);

    protected long opUserId = 0L;

    protected DataSource dataSource;

    public TestBase() {
        dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUrl("jdbc:mysql://192.168.1.179:3306/foundation_commons?serverTimezone=UTC");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUsername("root");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPassword("123");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMaxActive(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMaxIdle(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMinIdle(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setInitialSize(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setDriverClassName("com.mysql.cj.jdbc.Driver");

        MySQLDataType.VARCHAR.register();
        MySQLDataType.INT.register();
        BuiltinPropertyKeyType.ZERO.register();
        BuiltinPropertyKeyUse.ZERO.register();
    }

    protected DataSource getDataSource() {
        return dataSource;
    }
}
