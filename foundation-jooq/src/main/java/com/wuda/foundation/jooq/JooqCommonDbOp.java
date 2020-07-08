package com.wuda.foundation.jooq;

import org.jooq.*;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.util.function.Consumer;

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
     * @param forUpdateRecordSelector  用于查询准备更新的记录,只能查询出一条,如果查询出多条记录会抛出异常
     * @param insertIntoSelectFields   如果记录不存在,则使用这些字段新增一条记录,即insert into ...select fields语法的select fields.
     * @param existsRecordUpdateAction 如果记录存在,则用于更新selector查询出的那条记录
     * @param idColumn                 该记录的id列,用于返回记录的ID
     * @param <R>                      被操作的记录的类型
     * @return 新增或者是更新的记录的ID
     */
    default <R extends Record> long insertOrUpdate(DataSource dataSource,
                                                   Table<R> table,
                                                   SelectConditionStep<R> forUpdateRecordSelector,
                                                   SelectSelectStep<R> insertIntoSelectFields,
                                                   Consumer<R> existsRecordUpdateAction,
                                                   TableField<R, ULong> idColumn) {
        R affectedRecord;
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        affectedRecord = dslContext.insertInto(table)
                .select(
                        insertIntoSelectFields
                                .from(table)
                                .whereNotExists(
                                        forUpdateRecordSelector
                                )
                ).returning(idColumn).fetchOne();
        if (affectedRecord == null) {
            affectedRecord = forUpdateRecordSelector.fetchOne();
            existsRecordUpdateAction.accept(affectedRecord);
        }
        return affectedRecord.get(idColumn).longValue();
    }
}
