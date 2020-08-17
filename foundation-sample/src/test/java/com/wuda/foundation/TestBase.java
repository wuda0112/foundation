package com.wuda.foundation;

import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;

import javax.sql.DataSource;

public class TestBase {

    protected KeyGeneratorSnowflake keyGenerator = new KeyGeneratorSnowflake(1);

    protected long opUserId = 0L;

    protected DataSource dataSource;

    protected Byte byte0 = (byte) 0;

    public TestBase() {
        dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/foundation_commons?serverTimezone=UTC");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUsername("root");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPassword("123456");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMaxActive(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMaxIdle(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setMinIdle(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setInitialSize(2);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    protected DataSource getDataSource() {
        return dataSource;
    }
}
