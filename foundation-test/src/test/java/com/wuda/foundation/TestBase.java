package com.wuda.foundation;

import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;
import org.apache.logging.log4j.LogManager;
import org.jooq.tools.LoggerListener;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.util.logging.Level;

public class TestBase {

    protected KeyGeneratorSnowflake keyGenerator = new KeyGeneratorSnowflake(1);

    protected long opUserId = 0L;

    protected DataSource getDataSource() {
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/?serverTimezone=UTC");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setUsername("root");
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPassword("123456");
        return dataSource;
    }
}
