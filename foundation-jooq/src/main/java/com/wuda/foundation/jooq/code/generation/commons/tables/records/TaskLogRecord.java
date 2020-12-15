/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables.records;


import com.wuda.foundation.jooq.code.generation.commons.tables.TaskLog;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;


/**
 * 任务的日志
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TaskLogRecord extends UpdatableRecordImpl<TaskLogRecord> implements Record9<ULong, ULong, ULong, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> {

    private static final long serialVersionUID = -1846244183;

    /**
     * Setter for <code>foundation_commons.task_log.task_log_id</code>.
     */
    public void setTaskLogId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.task_log_id</code>.
     */
    public ULong getTaskLogId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>foundation_commons.task_log.task_id</code>. 所属的任务
     */
    public void setTaskId(ULong value) {
        set(1, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.task_id</code>. 所属的任务
     */
    public ULong getTaskId() {
        return (ULong) get(1);
    }

    /**
     * Setter for <code>foundation_commons.task_log.task_phase_id</code>. 所属任务的阶段，如果为0，表示日志只属于主体的task，不属于任何phase
     */
    public void setTaskPhaseId(ULong value) {
        set(2, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.task_phase_id</code>. 所属任务的阶段，如果为0，表示日志只属于主体的task，不属于任何phase
     */
    public ULong getTaskPhaseId() {
        return (ULong) get(2);
    }

    /**
     * Setter for <code>foundation_commons.task_log.content</code>.
     */
    public void setContent(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.content</code>.
     */
    public String getContent() {
        return (String) get(3);
    }

    /**
     * Setter for <code>foundation_commons.task_log.create_time</code>.
     */
    public void setCreateTime(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.create_time</code>.
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>foundation_commons.task_log.create_user_id</code>.
     */
    public void setCreateUserId(ULong value) {
        set(5, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.create_user_id</code>.
     */
    public ULong getCreateUserId() {
        return (ULong) get(5);
    }

    /**
     * Setter for <code>foundation_commons.task_log.last_modify_time</code>.
     */
    public void setLastModifyTime(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.last_modify_time</code>.
     */
    public LocalDateTime getLastModifyTime() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>foundation_commons.task_log.last_modify_user_id</code>.
     */
    public void setLastModifyUserId(ULong value) {
        set(7, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.last_modify_user_id</code>.
     */
    public ULong getLastModifyUserId() {
        return (ULong) get(7);
    }

    /**
     * Setter for <code>foundation_commons.task_log.is_deleted</code>.
     */
    public void setIsDeleted(ULong value) {
        set(8, value);
    }

    /**
     * Getter for <code>foundation_commons.task_log.is_deleted</code>.
     */
    public ULong getIsDeleted() {
        return (ULong) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<ULong, ULong, ULong, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<ULong, ULong, ULong, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<ULong> field1() {
        return TaskLog.TASK_LOG.TASK_LOG_ID;
    }

    @Override
    public Field<ULong> field2() {
        return TaskLog.TASK_LOG.TASK_ID;
    }

    @Override
    public Field<ULong> field3() {
        return TaskLog.TASK_LOG.TASK_PHASE_ID;
    }

    @Override
    public Field<String> field4() {
        return TaskLog.TASK_LOG.CONTENT;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return TaskLog.TASK_LOG.CREATE_TIME;
    }

    @Override
    public Field<ULong> field6() {
        return TaskLog.TASK_LOG.CREATE_USER_ID;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return TaskLog.TASK_LOG.LAST_MODIFY_TIME;
    }

    @Override
    public Field<ULong> field8() {
        return TaskLog.TASK_LOG.LAST_MODIFY_USER_ID;
    }

    @Override
    public Field<ULong> field9() {
        return TaskLog.TASK_LOG.IS_DELETED;
    }

    @Override
    public ULong component1() {
        return getTaskLogId();
    }

    @Override
    public ULong component2() {
        return getTaskId();
    }

    @Override
    public ULong component3() {
        return getTaskPhaseId();
    }

    @Override
    public String component4() {
        return getContent();
    }

    @Override
    public LocalDateTime component5() {
        return getCreateTime();
    }

    @Override
    public ULong component6() {
        return getCreateUserId();
    }

    @Override
    public LocalDateTime component7() {
        return getLastModifyTime();
    }

    @Override
    public ULong component8() {
        return getLastModifyUserId();
    }

    @Override
    public ULong component9() {
        return getIsDeleted();
    }

    @Override
    public ULong value1() {
        return getTaskLogId();
    }

    @Override
    public ULong value2() {
        return getTaskId();
    }

    @Override
    public ULong value3() {
        return getTaskPhaseId();
    }

    @Override
    public String value4() {
        return getContent();
    }

    @Override
    public LocalDateTime value5() {
        return getCreateTime();
    }

    @Override
    public ULong value6() {
        return getCreateUserId();
    }

    @Override
    public LocalDateTime value7() {
        return getLastModifyTime();
    }

    @Override
    public ULong value8() {
        return getLastModifyUserId();
    }

    @Override
    public ULong value9() {
        return getIsDeleted();
    }

    @Override
    public TaskLogRecord value1(ULong value) {
        setTaskLogId(value);
        return this;
    }

    @Override
    public TaskLogRecord value2(ULong value) {
        setTaskId(value);
        return this;
    }

    @Override
    public TaskLogRecord value3(ULong value) {
        setTaskPhaseId(value);
        return this;
    }

    @Override
    public TaskLogRecord value4(String value) {
        setContent(value);
        return this;
    }

    @Override
    public TaskLogRecord value5(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public TaskLogRecord value6(ULong value) {
        setCreateUserId(value);
        return this;
    }

    @Override
    public TaskLogRecord value7(LocalDateTime value) {
        setLastModifyTime(value);
        return this;
    }

    @Override
    public TaskLogRecord value8(ULong value) {
        setLastModifyUserId(value);
        return this;
    }

    @Override
    public TaskLogRecord value9(ULong value) {
        setIsDeleted(value);
        return this;
    }

    @Override
    public TaskLogRecord values(ULong value1, ULong value2, ULong value3, String value4, LocalDateTime value5, ULong value6, LocalDateTime value7, ULong value8, ULong value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TaskLogRecord
     */
    public TaskLogRecord() {
        super(TaskLog.TASK_LOG);
    }

    /**
     * Create a detached, initialised TaskLogRecord
     */
    public TaskLogRecord(ULong taskLogId, ULong taskId, ULong taskPhaseId, String content, LocalDateTime createTime, ULong createUserId, LocalDateTime lastModifyTime, ULong lastModifyUserId, ULong isDeleted) {
        super(TaskLog.TASK_LOG);

        set(0, taskLogId);
        set(1, taskId);
        set(2, taskPhaseId);
        set(3, content);
        set(4, createTime);
        set(5, createUserId);
        set(6, lastModifyTime);
        set(7, lastModifyUserId);
        set(8, isDeleted);
    }
}
