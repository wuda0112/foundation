/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables;


import com.wuda.foundation.jooq.code.generation.commons.FoundationCommons;
import com.wuda.foundation.jooq.code.generation.commons.Keys;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.TaskRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 任务
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Task extends TableImpl<TaskRecord> {

    private static final long serialVersionUID = -673324653;

    /**
     * The reference instance of <code>foundation_commons.task</code>
     */
    public static final Task TASK = new Task();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TaskRecord> getRecordType() {
        return TaskRecord.class;
    }

    /**
     * The column <code>foundation_commons.task.task_id</code>.
     */
    public final TableField<TaskRecord, ULong> TASK_ID = createField(DSL.name("task_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>foundation_commons.task.type</code>. 任务类型
     */
    public final TableField<TaskRecord, UByte> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "任务类型");

    /**
     * The column <code>foundation_commons.task.create_time</code>.
     */
    public final TableField<TaskRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_commons.task.create_user_id</code>.
     */
    public final TableField<TaskRecord, ULong> CREATE_USER_ID = createField(DSL.name("create_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_commons.task.last_modify_time</code>.
     */
    public final TableField<TaskRecord, LocalDateTime> LAST_MODIFY_TIME = createField(DSL.name("last_modify_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_commons.task.last_modify_user_id</code>.
     */
    public final TableField<TaskRecord, ULong> LAST_MODIFY_USER_ID = createField(DSL.name("last_modify_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_commons.task.is_deleted</code>.
     */
    public final TableField<TaskRecord, ULong> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "");

    /**
     * Create a <code>foundation_commons.task</code> table reference
     */
    public Task() {
        this(DSL.name("task"), null);
    }

    /**
     * Create an aliased <code>foundation_commons.task</code> table reference
     */
    public Task(String alias) {
        this(DSL.name(alias), TASK);
    }

    /**
     * Create an aliased <code>foundation_commons.task</code> table reference
     */
    public Task(Name alias) {
        this(alias, TASK);
    }

    private Task(Name alias, Table<TaskRecord> aliased) {
        this(alias, aliased, null);
    }

    private Task(Name alias, Table<TaskRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("任务"), TableOptions.table());
    }

    public <O extends Record> Task(Table<O> child, ForeignKey<O, TaskRecord> key) {
        super(child, key, TASK);
    }

    @Override
    public Schema getSchema() {
        return FoundationCommons.FOUNDATION_COMMONS;
    }

    @Override
    public Identity<TaskRecord, ULong> getIdentity() {
        return Keys.IDENTITY_TASK;
    }

    @Override
    public UniqueKey<TaskRecord> getPrimaryKey() {
        return Keys.KEY_TASK_PRIMARY;
    }

    @Override
    public List<UniqueKey<TaskRecord>> getKeys() {
        return Arrays.<UniqueKey<TaskRecord>>asList(Keys.KEY_TASK_PRIMARY);
    }

    @Override
    public Task as(String alias) {
        return new Task(DSL.name(alias), this);
    }

    @Override
    public Task as(Name alias) {
        return new Task(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Task rename(String name) {
        return new Task(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Task rename(Name name) {
        return new Task(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<ULong, UByte, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
