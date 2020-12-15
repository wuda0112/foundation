/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables.daos;


import com.wuda.foundation.jooq.code.generation.commons.tables.TaskPhase;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.TaskPhaseRecord;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 任务的某个阶段
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TaskPhaseDao extends DAOImpl<TaskPhaseRecord, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase, ULong> {

    /**
     * Create a new TaskPhaseDao without any configuration
     */
    public TaskPhaseDao() {
        super(TaskPhase.TASK_PHASE, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase.class);
    }

    /**
     * Create a new TaskPhaseDao with an attached configuration
     */
    public TaskPhaseDao(Configuration configuration) {
        super(TaskPhase.TASK_PHASE, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase.class, configuration);
    }

    @Override
    public ULong getId(com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase object) {
        return object.getTaskPhaseId();
    }

    /**
     * Fetch records that have <code>task_phase_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfTaskPhaseId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.TASK_PHASE_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>task_phase_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByTaskPhaseId(ULong... values) {
        return fetch(TaskPhase.TASK_PHASE.TASK_PHASE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>task_phase_id = value</code>
     */
    public com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase fetchOneByTaskPhaseId(ULong value) {
        return fetchOne(TaskPhase.TASK_PHASE.TASK_PHASE_ID, value);
    }

    /**
     * Fetch records that have <code>task_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfTaskId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.TASK_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>task_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByTaskId(ULong... values) {
        return fetch(TaskPhase.TASK_PHASE.TASK_ID, values);
    }

    /**
     * Fetch records that have <code>type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfType(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.TYPE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByType(UByte... values) {
        return fetch(TaskPhase.TASK_PHASE.TYPE, values);
    }

    /**
     * Fetch records that have <code>execute_status BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfExecuteStatus(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.EXECUTE_STATUS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>execute_status IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByExecuteStatus(UByte... values) {
        return fetch(TaskPhase.TASK_PHASE.EXECUTE_STATUS, values);
    }

    /**
     * Fetch records that have <code>sequence_number BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfSequenceNumber(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.SEQUENCE_NUMBER, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>sequence_number IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchBySequenceNumber(UByte... values) {
        return fetch(TaskPhase.TASK_PHASE.SEQUENCE_NUMBER, values);
    }

    /**
     * Fetch records that have <code>create_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfCreateTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.CREATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByCreateTime(LocalDateTime... values) {
        return fetch(TaskPhase.TASK_PHASE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfCreateUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.CREATE_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByCreateUserId(ULong... values) {
        return fetch(TaskPhase.TASK_PHASE.CREATE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_modify_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfLastModifyTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.LAST_MODIFY_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByLastModifyTime(LocalDateTime... values) {
        return fetch(TaskPhase.TASK_PHASE.LAST_MODIFY_TIME, values);
    }

    /**
     * Fetch records that have <code>last_modify_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfLastModifyUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.LAST_MODIFY_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByLastModifyUserId(ULong... values) {
        return fetch(TaskPhase.TASK_PHASE.LAST_MODIFY_USER_ID, values);
    }

    /**
     * Fetch records that have <code>is_deleted BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchRangeOfIsDeleted(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(TaskPhase.TASK_PHASE.IS_DELETED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>is_deleted IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.TaskPhase> fetchByIsDeleted(ULong... values) {
        return fetch(TaskPhase.TASK_PHASE.IS_DELETED, values);
    }
}
