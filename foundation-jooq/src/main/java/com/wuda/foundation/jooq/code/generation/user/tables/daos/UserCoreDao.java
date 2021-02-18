/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables.daos;


import com.wuda.foundation.jooq.code.generation.user.tables.UserCore;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserCoreRecord;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 用户核心信息。用户有很多类型，比如一种分类方法是把用户分成个人用户和企业用户，而不同类型的用户需要的字段不一样，但是他们都是用户，即 is-a 
 * user。这个表属于所有用户的基本信息，其他不同类型的用户有自己专属的表，然后用用户ID关联回这个表。这样做还有一个好处，那就是其他表中的用户ID都统一关联回这个表，这样用户ID就不会有歧义了。
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCoreDao extends DAOImpl<UserCoreRecord, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore, ULong> {

    /**
     * Create a new UserCoreDao without any configuration
     */
    public UserCoreDao() {
        super(UserCore.USER_CORE, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore.class);
    }

    /**
     * Create a new UserCoreDao with an attached configuration
     */
    public UserCoreDao(Configuration configuration) {
        super(UserCore.USER_CORE, com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore.class, configuration);
    }

    @Override
    public ULong getId(com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore object) {
        return object.getUserCoreId();
    }

    /**
     * Fetch records that have <code>user_core_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfUserCoreId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserCore.USER_CORE.USER_CORE_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>user_core_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByUserCoreId(ULong... values) {
        return fetch(UserCore.USER_CORE.USER_CORE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>user_core_id = value</code>
     */
    public com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore fetchOneByUserCoreId(ULong value) {
        return fetchOne(UserCore.USER_CORE.USER_CORE_ID, value);
    }

    /**
     * Fetch records that have <code>user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserCore.USER_CORE.USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByUserId(ULong... values) {
        return fetch(UserCore.USER_CORE.USER_ID, values);
    }

    /**
     * Fetch records that have <code>type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfType(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(UserCore.USER_CORE.TYPE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByType(UByte... values) {
        return fetch(UserCore.USER_CORE.TYPE, values);
    }

    /**
     * Fetch records that have <code>state BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfState(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(UserCore.USER_CORE.STATE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>state IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByState(UByte... values) {
        return fetch(UserCore.USER_CORE.STATE, values);
    }

    /**
     * Fetch records that have <code>create_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfCreateTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(UserCore.USER_CORE.CREATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByCreateTime(LocalDateTime... values) {
        return fetch(UserCore.USER_CORE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfCreateUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserCore.USER_CORE.CREATE_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByCreateUserId(ULong... values) {
        return fetch(UserCore.USER_CORE.CREATE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_modify_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfLastModifyTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(UserCore.USER_CORE.LAST_MODIFY_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByLastModifyTime(LocalDateTime... values) {
        return fetch(UserCore.USER_CORE.LAST_MODIFY_TIME, values);
    }

    /**
     * Fetch records that have <code>last_modify_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfLastModifyUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserCore.USER_CORE.LAST_MODIFY_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByLastModifyUserId(ULong... values) {
        return fetch(UserCore.USER_CORE.LAST_MODIFY_USER_ID, values);
    }

    /**
     * Fetch records that have <code>is_deleted BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchRangeOfIsDeleted(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(UserCore.USER_CORE.IS_DELETED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>is_deleted IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.user.tables.pojos.UserCore> fetchByIsDeleted(ULong... values) {
        return fetch(UserCore.USER_CORE.IS_DELETED, values);
    }
}
