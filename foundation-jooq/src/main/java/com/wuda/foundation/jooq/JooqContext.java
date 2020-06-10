package com.wuda.foundation.jooq;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * jooq框架一些类统一管理.
 *
 * @author wuda
 */
public class JooqContext {

    private static Map<DataSource, DSLContext> dslContextByDatasourceMap = new HashMap<>();
    private static Lock dslContextLock = new ReentrantLock();

    public static DSLContext getOrCreateDSLContext(DataSource dataSource) {
        if (dslContextByDatasourceMap.get(dataSource) == null) {
            dslContextLock.lock();
            try {
                if (dslContextByDatasourceMap.get(dataSource) == null) {
                    DSLContext dslContext = DSL.using(dataSource, SQLDialect.MYSQL);
                    dslContextByDatasourceMap.put(dataSource, dslContext);
                }
            } catch (Exception e) {
                throw new RuntimeException("getOrCreateDSLContext exception", e);
            } finally {
                dslContextLock.unlock();
            }
        }
        return dslContextByDatasourceMap.get(dataSource);
    }

    public static Configuration getConfiguration(DataSource dataSource) {
        Configuration configuration = new DefaultConfiguration()
                .set(SQLDialect.MYSQL)
                .set(dataSource);
        return configuration;
    }
}
