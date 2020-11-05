package com.wuda.foundation.jooq;

import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import org.jooq.*;
import org.jooq.types.ULong;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Consumer;

import static org.jooq.impl.DSL.param;
import static org.jooq.impl.DSL.select;

/**
 * 封装一些基本的数据库操作.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface JooqCommonDbOp {

    /**
     * 如果记录不存在则insert,如果存在则update.这个操作不是原子的,即不是使用insert ... on duplicate update这样的语法,
     * 而是先尝试执行insert,如果在执行insert过程中发现记录已经存在时,再使用<i>existsRecordPKSelector</i>查询出这条记录,
     * 然后再去update.
     *
     * @param createMode               insert阶段的模式.不支持{@link CreateMode#DIRECT},因为这种模式下,不会去检查记录是否存在,而是直接执行insert into语句
     * @param dataSource               datasource
     * @param table                    操作的表
     * @param record                   如果记录不存在,则使用这些字段新增一条记录
     * @param existsRecordPKSelector   在insert时,用于检测记录是否已经存在,如果存在,也用于查询出该记录,被查询出的这条记录用于后续的update操作.只能查询出一条,如果查询出多条记录会抛出异常.
     * @param existsRecordUpdateAction 如果记录存在,则用于更新查询出的那条记录,传递的是已经存在的记录的ID
     * @param <R>                      被操作的记录的类型
     * @return 新增或者是更新的记录的ID
     */
    default <R extends Record> long insertOrUpdate(CreateMode createMode,
                                                   DataSource dataSource,
                                                   Table<R> table,
                                                   R record,
                                                   SelectConditionStep<Record1<ULong>> existsRecordPKSelector,
                                                   Consumer<Long> existsRecordUpdateAction) {
        if (createMode == CreateMode.DIRECT) {
            throw new UnsupportedOperationException("不支持的InsertMode," + createMode);
        }
        checkKeys(table);
        CreateResult createResult = insertDispatcher(dataSource, createMode, table, record, existsRecordPKSelector);
        createResult.valid();
        long id;
        if (createResult.getExistsRecordId() != null) {
            existsRecordUpdateAction.accept(createResult.getExistsRecordId());
            id = createResult.getExistsRecordId();
        } else {
            id = createResult.getNewlyAddedRecordId();
        }
        return id;
    }

    /**
     * 先查询数据库中是否存在,如果不存在再执行插入操作.
     *
     * @param dataSource             datasource
     * @param table                  操作的表
     * @param record                 如果记录不存在,则使用这些字段新增一条记录
     * @param existsRecordPKSelector 用于查询已经存在的记录,只能查询出一条,并且只查询primary key.如果查询出多条记录会抛出异常
     * @param <R>                    被操作的记录的类型
     * @return 执行的结果
     */
    default <R extends Record> CreateResult insertAfterSelectCheck(DataSource dataSource,
                                                                   Table<R> table,
                                                                   R record,
                                                                   SelectConditionStep<Record1<ULong>> existsRecordPKSelector) {
        checkKeys(table);
        attach(dataSource, existsRecordPKSelector);
        Record1<ULong> existsRecord = existsRecordPKSelector.fetchOne();
        if (existsRecord != null) {
            TableField<R, ULong> idColumn = getPrimaryKeyField(table);
            long existsRecordId = existsRecord.get(idColumn).longValue();
            return new CreateResult(CreateMode.CREATE_AFTER_SELECT_CHECK, false, 0, existsRecordId, null);
        }
        return insert(dataSource, table, record);
    }

    /**
     * 直接插入数据库.
     *
     * @param dataSource datasource
     * @param table      操作的表
     * @param record     如果记录不存在,则使用这些字段新增一条记录
     * @param <R>        被操作的记录的类型
     * @return 执行的结果
     */
    default <R extends Record> CreateResult insert(DataSource dataSource,
                                                   Table<R> table,
                                                   R record) {
        checkKeys(table);
        TableField<R, ULong> idColumn = getPrimaryKeyField(table);
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        InsertOnDuplicateStep<R> insertSetStep = dslContext.insertInto(table).set(record);
        InsertResultStep<R> insertResultStep = insertSetStep.returning(idColumn);
        R r = insertResultStep.fetchOne();
        long id = r.get(idColumn).longValue();
        return new CreateResult(CreateMode.DIRECT, true, 1, null, id);
    }

    /**
     * 执行insert into ... select ... from dual where not exists语句.
     *
     * @param dataSource             datasource
     * @param table                  操作的表
     * @param existsRecordPKSelector 用于查询已经存在的记录,只能查询出一条,并且只查询primary key.如果查询出多条记录会抛出异常
     * @param record                 如果记录不存在,则使用{@link Record#fields()}这些字段新增一条记录,即insert into ...select fields语法的select fields.
     * @param <R>                    被操作的记录的类型
     * @return 如果已经存在, 则返回<code>null</code>; 否则返回新增记录的ID
     */
    default <R extends Record> CreateResult insertIfNotExists(DataSource dataSource,
                                                              Table<R> table,
                                                              R record,
                                                              SelectConditionStep<Record1<ULong>> existsRecordPKSelector) {
        checkKeys(table);
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        SelectSelectStep<R> selectFields = getSelectFields(record);
        InsertOnDuplicateStep<R> insertSetStep = dslContext.insertInto(table)
                .select(
                        selectFields
                                // .from(table) from dual
                                .whereNotExists(
                                        existsRecordPKSelector
                                )
                );
        TableField<R, ULong> idColumn = getPrimaryKeyField(table);
        InsertResultStep<R> insertResultStep = insertSetStep.returning(idColumn);
        R r = insertResultStep.fetchOne();
        Long existsRecordId = null;
        Long newlyAddedRecordId = null;
        int affectedRows = 1;
        if (r == null) {
            attach(dataSource, existsRecordPKSelector);
            Record1<ULong> onlyContainsIdColumnRecord = existsRecordPKSelector.fetchOne();
            existsRecordId = onlyContainsIdColumnRecord.get(idColumn).longValue();
            affectedRows = 0;
        } else {
            newlyAddedRecordId = r.get(idColumn).longValue();
        }
        return new CreateResult(CreateMode.CREATE_WHERE_NOT_EXISTS, true, affectedRows, existsRecordId, newlyAddedRecordId);
    }

    /**
     * 执行insert into语句,根据{@link CreateMode}不同,执行的方式也不同.
     *
     * @param dataSource             datasource
     * @param table                  操作的表
     * @param existsRecordPKSelector 用于查询已经存在的记录,只能查询出一条,并且只查询primary key.如果查询出多条记录会抛出异常.如果{@link CreateMode}是{@link CreateMode#DIRECT},则此参数可以是<code>null</code>
     * @param record                 如果记录不存在,则使用这些字段新增一条记录,即insert into ...select fields语法的select fields.
     * @param <R>                    被操作的记录的类型
     * @return 如果已经存在, 则返回<code>null</code>; 否则返回新增记录的ID
     */
    default <R extends Record> CreateResult insertDispatcher(DataSource dataSource,
                                                             CreateMode createMode,
                                                             Table<R> table,
                                                             R record,
                                                             SelectConditionStep<Record1<ULong>> existsRecordPKSelector) {
        CreateResult result;
        switch (createMode) {
            case DIRECT:
                result = insert(dataSource, table, record);
                break;
            case CREATE_AFTER_SELECT_CHECK:
                result = insertAfterSelectCheck(dataSource, table, record, existsRecordPKSelector);
                break;
            case CREATE_WHERE_NOT_EXISTS:
                result = insertIfNotExists(dataSource, table, record, existsRecordPKSelector);
                break;
            default:
                result = insertAfterSelectCheck(dataSource, table, record, existsRecordPKSelector);
                break;
        }
        return result;
    }

    /**
     * 检查表的primary key,auto_increment等内容.如果检查不通过会抛出{@link IllegalStateException}异常.
     *
     * @param table table
     * @param <R>   table type
     */
    default <R extends Record> void checkKeys(Table<R> table) {
        TableField<R, ?> primaryKeyField = getPrimaryKeyField(table);
        TableField<R, ?> autoIncrementField = getAutoIncrementField(table);
        if (!primaryKeyField.getName().equals(autoIncrementField.getName())) {
            throw new IllegalStateException("table name = " + table.getName()
                    + ",primary key所在的列与auto increment所在的列不一致"
                    + ",在设计表时虽然这是允许的,但是不利于统一处理,作为本项目的约束,强制要求为表primary key和auto increment作用在同一个列上");
        }
    }

    /**
     * 获取表的PRIMARY KEY字段.
     *
     * @param table table
     * @param <R>   table type
     * @return primary key字段
     */
    default <R extends Record> TableField<R, ULong> getPrimaryKeyField(Table<R> table) {
        UniqueKey<R> primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            throw new IllegalStateException("table name = " + table.getName()
                    + ",没有primary key"
                    + ",在设计表时虽然这是允许的,但是会影响很多功能的使用,作为本项目的约束,强制要求为表建立primary key");
        }
        List<TableField<R, ?>> fields = primaryKey.getFields();
        if (fields.size() > 1) {
            throw new IllegalStateException("table name = " + table.getName()
                    + ",primary key由多个字段构成"
                    + ",在设计表时虽然这是允许的,但是不利于统一处理"
                    + ",作为本项目的约束,强制要求primary key只能由一个列构成");
        }
        TableField<R, ?> field = fields.get(0);
        TableField<R, ULong> idField;
        try {
            idField = (TableField<R, ULong>) field;
        } catch (Exception e) {
            throw new IllegalStateException("table name = " + table.getName()
                    + ",primary key的数据类型不是unsigned long"
                    + ",在设计表时虽然这是允许的,但是不利于分布式环境下自动生成主键值的功能"
                    + ",作为本项目的约束,强制要求primary key只能是unsigned long");
        }
        return idField;
    }

    /**
     * 获取表的AUTO_INCREMENT字段.
     *
     * @param table table
     * @param <R>   table type
     * @return AUTO_INCREMENT字段
     */
    default <R extends Record> TableField<R, ?> getAutoIncrementField(Table<R> table) {
        if (table.getIdentity() == null || table.getIdentity().getField() == null) {
            throw new IllegalStateException("table name = " + table.getName()
                    + ",没有AUTO_INCREMENT column"
                    + ".在设计表时虽然这是允许的,但是会影响很多功能的使用,比如insert后取回主键值"
                    + ".作为本项目的约束,强制要求为主键字段设置AUTO_INCREMENT属性");
        }
        return table.getIdentity().getField();
    }

    default void attach(DataSource dataSource, SelectConditionStep<Record1<ULong>> existsRecordPKSelector) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        existsRecordPKSelector.attach(configuration);
    }

    default <R extends Record> void attach(DataSource dataSource, R r) {
        Configuration configuration = JooqContext.getConfiguration(dataSource);
        r.attach(configuration);
    }

    /**
     * 执行insert into ... values(a,b,c),(e,f,g)...这样的语法.
     *
     * @param dataSource datasource
     * @param table      操作的表
     * @param records    准备插入表中的记录
     * @param <R>        被操作的记录的类型
     */
    default <R extends Record> void batchInsert(DataSource dataSource,
                                                Table<R> table,
                                                List<R> records) {
        if (records == null || records.isEmpty()) {
            return;
        }
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(dataSource);
        R firstRecord = records.get(0);
        Object[] firstValues = getFieldValues(firstRecord);
        if (records.size() == 1) {
            dslContext.insertInto(table).values(firstValues).execute();
            return;
        }
        InsertSetStep<R> insertSetStep = dslContext.insertInto(table);
        InsertValuesStepN<R> insertValuesStepN = insertSetStep.values(firstValues);
        int last = records.size() - 1;
        for (int i = 1; i < last; i++) {
            R record = records.get(i);
            Object[] values = getFieldValues(record);
            insertValuesStepN = insertValuesStepN.values(values);
        }
        Object[] values = getFieldValues(records.get(last));
        insertValuesStepN.values(values);
        insertValuesStepN.execute();
    }

    /**
     * 按顺序取回record中的所有value.
     *
     * @param record record
     * @return values, 按在record中的顺序返回
     */
    default Object[] getFieldValues(Record record) {
        Field[] fields = record.fields();
        Object[] values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            values[i] = record.get(i);
        }
        return values;
    }

    /**
     * 按顺序返回record中的所有field和value.
     *
     * @param record record
     * @param <R>    record的类型
     * @return select field
     */
    default <R extends Record> SelectSelectStep<R> getSelectFields(R record) {
        int fieldSize = record.size();
        SelectField[] selectFields = new SelectField[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            Field field = record.field(i);
            Object value = record.get(i);
            selectFields[i] = param(field.getName(), value);
        }
        return (SelectSelectStep<R>) select(selectFields);
    }

    /**
     * 返回{@link IsDeleted#NO}的{@link ULong}形式.
     *
     * @return {@link IsDeleted#NO}的{@link ULong}形式.
     */
    default ULong notDeleted() {
        return ULong.valueOf(IsDeleted.NO.getValue());
    }

    /**
     * 更新field value不为<code>null</code>的字段.
     *
     * @param dataSource {@link DataSource}
     * @param record     record
     * @param <R>        记录的类型
     */
    default <R extends UpdatableRecord> void updateSelectiveByPrimaryKey(DataSource dataSource, R record) {
        changedIfNotNull(record);
        attach(dataSource, record);
        record.update();
    }

    /**
     * 遍历record,如果当field的value不为<code>null</code>,则认为已经changed.
     * 查看{@link Record#changed(Field, boolean)}的定义.
     *
     * @param record record
     * @param <R>    record的类型
     */
    default <R extends UpdatableRecord> void changedIfNotNull(R record) {
        int fieldSize = record.size();
        for (int i = 0; i < fieldSize; i++) {
            Field field = record.field(i);
            Object value = record.get(i);
            if (value != null) {
                record.changed(field, true);
            } else {
                // 有些字段调用了setter方法后,即使set的值为null,也会被标记为changed
                record.changed(field, false);
            }
        }
    }

    /**
     * 根据主键ID获取记录.
     *
     * @param id    id
     * @param table table
     * @param <R>   记录的类型
     * @return record
     */
    default <R extends Record> R getById(Long id, Table<R> table) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        TableField<R, ULong> idColumn = getPrimaryKeyField(table);
        return dslContext.selectFrom(table)
                .where(idColumn.eq(ULong.valueOf(id)))
                .fetchOne();
    }

}
