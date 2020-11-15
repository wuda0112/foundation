package com.wuda.foundation.jooq;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * jooq框架一些类统一管理.
 *
 * @author wuda
 */
public class JooqContext {

    private static Map<DataSource, DSLContext> dslContextByDatasourceMap = new HashMap<>();
    private static Lock dslContextLock = new ReentrantLock();

    /**
     * 用于获取{@link DataSource}.
     */
    private static Supplier<DataSource> dataSourceSupplier = null;

    public static DSLContext getOrCreateDSLContext(){
        return getOrCreateDSLContext(getDataSource());
    }

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

    public static Configuration getConfiguration(){
        return getConfiguration(getDataSource());
    }

    public static Configuration getConfiguration(DataSource dataSource) {
        Configuration configuration = new DefaultConfiguration()
                .set(SQLDialect.MYSQL)
                .set(dataSource);
        return configuration;
    }

    /**
     * 获取{@link DataSource}.
     *
     * @return {@link DataSource}
     */
    public static DataSource getDataSource() {
        if (dataSourceSupplier == null) {
            throw new NullPointerException("请先通过setDataSourceSupplier()方法设置DataSource Supplier");
        }
        return dataSourceSupplier.get();
    }

    /**
     * 设置{@link DataSource}的{@link Supplier}.
     *
     * @param dataSourceSupplier datasource supplier
     */
    public static void setDataSourceSupplier(Supplier<DataSource> dataSourceSupplier) {
        JooqContext.dataSourceSupplier = Objects.requireNonNull(dataSourceSupplier);
        if (JooqContext.dataSourceSupplier.get() == null) {
            throw new IllegalArgumentException("无效的supplier,获取的DataSource为null");
        }
    }
}
