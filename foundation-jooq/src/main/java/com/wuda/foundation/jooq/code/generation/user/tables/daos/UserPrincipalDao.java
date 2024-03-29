/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables.daos;


import com.wuda.foundation.jooq.code.generation.user.tables.UserPrincipal;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserPrincipalRecord;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 用户的身份标记，比如用户名就是一种principal
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPrincipalDao extends DAOImpl<UserPrincipalRecord, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal, ULong> {

    /**
     * Create a new UserPrincipalDao without any configuration
     */
    public UserPrincipalDao() {
        super(UserPrincipal.USER_PRINCIPAL, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal.class);
    }

    /**
     * Create a new UserPrincipalDao with an attached configuration
     */
    public UserPrincipalDao(Configuration configuration) {
        super(UserPrincipal.USER_PRINCIPAL, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal.class, configuration);
    }

    @Override
    public ULong getId(com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal object) {
        return object.getUserPricinpalId();
    }

    /**
     * Fetch records that have <code>user_pricinpal_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfUserPricinpalId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.USER_PRICINPAL_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>user_pricinpal_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByUserPricinpalId(ULong... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.USER_PRICINPAL_ID, values);
    }

    /**
     * Fetch a unique record that has <code>user_pricinpal_id = value</code>
     */
    public com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal fetchOneByUserPricinpalId(ULong value) {
        return fetchOne(UserPrincipal.USER_PRINCIPAL.USER_PRICINPAL_ID, value);
    }

    /**
     * Fetch records that have <code>user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByUserId(ULong... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.USER_ID, values);
    }

    /**
     * Fetch records that have <code>type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfType(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.TYPE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByType(UByte... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.TYPE, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByName(String... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.NAME, values);
    }

    /**
     * Fetch records that have <code>description BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfDescription(String lowerInclusive, String upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.DESCRIPTION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByDescription(String... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfCreateTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.CREATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByCreateTime(LocalDateTime... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfCreateUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.CREATE_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByCreateUserId(ULong... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.CREATE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_modify_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfLastModifyTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.LAST_MODIFY_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByLastModifyTime(LocalDateTime... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.LAST_MODIFY_TIME, values);
    }

    /**
     * Fetch records that have <code>last_modify_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfLastModifyUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.LAST_MODIFY_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByLastModifyUserId(ULong... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.LAST_MODIFY_USER_ID, values);
    }

    /**
     * Fetch records that have <code>is_deleted BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchRangeOfIsDeleted(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserPrincipal.USER_PRINCIPAL.IS_DELETED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>is_deleted IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserPrincipal> fetchByIsDeleted(ULong... values) {
        return fetch(UserPrincipal.USER_PRINCIPAL.IS_DELETED, values);
    }
}
