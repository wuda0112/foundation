package com.wuda.foundation.jooq;

import org.jooq.*;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.util.function.Consumer;

import static org.jooq.impl.DSL.select;

/**
 * 封装一些基本的数据库操作.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface JooqCommonDbOp {

    /**
     * 如果记录不存在则insert,如果存在则更新.
     *
     * @param dataSource               datasource
     * @param table                    操作的表
     * @param fields   如果记录不存在,则使用这些字段新增一条记录
     * @param existsRecordSelector     用于查询准备更新的记录,只能查询出一条,如果查询出多条记录会抛出异常
     * @param existsRecordUpdateAction 如果记录存在,则用于更新selector查询出的那条记录
     * @param idColumn                 该记录的id列,用于返回记录的ID
     * @param <R>                      被操作的记录的类型
     * @return 新增或者是更新的记录的ID
     */
    default <R extends Record> long insertOrUpdate(DataSource dataSource,
                                                   Table<R> table,
                                                   Field[] fields,
                                                   SelectConditionStep<R> existsRecordSelector,
                                                   Consumer<R> existsRecordUpdateAction,
                                                   TableField<R, ULong> idColumn) {
        Long id = insertIfNotExists(dataSource, table, fields, existsRecordSelector, idColumn);
        if (id == null) {
            R affectedRecord = existsRecordSelector.fetchOne();
            existsRecordUpdateAction.accept(affectedRecord);
            id = affectedRecord.get(idColumn).longValue();
        }
        return id;
    }

    /**
     * 先查询数据库中是否存在,如果不存在再执行插入操作.
     *
     * @param dataSource           datasource
     * @param table                操作的表
     * @param existsRecordSelector 用于查询已经存在的记录,只能查询出一条,如果查询出多条记录会抛出异常
     * @param fields               如果记录不存在,则使用这些字段新增一条记录
     * @param <R>                  被操作的记录的类型
     * @return 如果已经存在, 则返回<code>null</code>; 否则返回新增记录的ID
     */
    default <R extends Record> Long insertAfterSelectCheck(DataSource dataSource,
                                                           Table<R> table,
                                                           Field[] fields,
                                                           SelectConditionStep<R> existsRecordSelector,
                                                           TableField<R, ULong> idColumn) {

        R existsRecord = existsRecordSelector.fetchOne();
        if (existsRecord != null) {
            return existsRecord.get(idColumn).longValue();
        }
        return insert(dataSource, table, fields, idColumn);
    }

    /**
     * 直接插入数据库.
     *
     * @param dataSource datasource
     * @param table      操作的表
     * @param fields     如果记录不存在,则使用这些字段新增一条记录
     * @param <R>        被操作的记录的类型
     * @return 返回新增记录的ID
     */
    default <R extends Record> Long insert(DataSource dataSource,
                                           Table<R> table,
                                           Field[] fields,
                                           TableField<R, ULong> idColumn) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertOnDuplicateStep<R> insertSetStep = dslContext.insertInto(table)
                .values(fields);
        InsertResultStep<R> insertResultStep = insertSetStep.returning(idColumn);
        R r = insertResultStep.fetchOne();
        return r.get(idColumn).longValue();
    }

    /**
     * 执行insert into ... select ... from dual where not exists语句.
     *
     * @param dataSource             datasource
     * @param table                  操作的表
     * @param existsRecordSelector   用于查询已经存在的记录,只能查询出一条,如果查询出多条记录会抛出异常
     * @param insertIntoSelectFields 如果记录不存在,则使用这些字段新增一条记录,即insert into ...select fields语法的select fields.
     * @param <R>                    被操作的记录的类型
     * @return 如果已经存在, 则返回<code>null</code>; 否则返回新增记录的ID
     */
    default <R extends Record> Long insertIfNotExists(DataSource dataSource,
                                                      Table<R> table,
                                                      Field[] insertIntoSelectFields,
                                                      SelectConditionStep<R> existsRecordSelector,
                                                      TableField<R, ULong> idColumn) {

        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectSelectStep<R> selectFields = (SelectSelectStep<R>) select(insertIntoSelectFields);
        InsertOnDuplicateStep<R> insertSetStep = dslContext.insertInto(table)
                .select(
                        selectFields
                                // .from(table) from dual
                                .whereNotExists(
                                        existsRecordSelector
                                )
                );
        InsertResultStep<R> insertResultStep = insertSetStep.returning(idColumn);
        R r = insertResultStep.fetchOne();
        if (r == null) {
            r = existsRecordSelector.fetchOne();
        }
        return r.get(idColumn).longValue();
    }

}
