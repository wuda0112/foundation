/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.security.tables.daos;


import com.wuda.foundation.jooq.code.generation.security.tables.PermissionTarget;
import com.wuda.foundation.jooq.code.generation.security.tables.records.PermissionTargetRecord;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * permission作用的对象，分为两类，1，关联外部对象，使用type字段表明外部对象的类型，referenced_id表明外部对象的唯一标记，比如在web系统中，已经拥有了菜单表，如果要对菜单权限控制，使用referenced_id关联菜单表的主键ID，就可以将permission与菜单数据建立联系，而不需要把菜单相关的逻辑引入到权限体系中；2，不关联外部对象，当前表中的信息就已经描述了作用对象的信息。对于permission体系来说，permission 
 * target是主体（一等公民），permiss
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PermissionTargetDao extends DAOImpl<PermissionTargetRecord, com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget, ULong> {

    /**
     * Create a new PermissionTargetDao without any configuration
     */
    public PermissionTargetDao() {
        super(PermissionTarget.PERMISSION_TARGET, com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget.class);
    }

    /**
     * Create a new PermissionTargetDao with an attached configuration
     */
    public PermissionTargetDao(Configuration configuration) {
        super(PermissionTarget.PERMISSION_TARGET, com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget.class, configuration);
    }

    @Override
    public ULong getId(com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget object) {
        return object.getPermissionTargetId();
    }

    /**
     * Fetch records that have <code>permission_target_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfPermissionTargetId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.PERMISSION_TARGET_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>permission_target_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByPermissionTargetId(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.PERMISSION_TARGET_ID, values);
    }

    /**
     * Fetch a unique record that has <code>permission_target_id = value</code>
     */
    public com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget fetchOneByPermissionTargetId(ULong value) {
        return fetchOne(PermissionTarget.PERMISSION_TARGET.PERMISSION_TARGET_ID, value);
    }

    /**
     * Fetch records that have <code>permission_category_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfPermissionCategoryId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.PERMISSION_CATEGORY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>permission_category_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByPermissionCategoryId(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.PERMISSION_CATEGORY_ID, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByName(String... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.NAME, values);
    }

    /**
     * Fetch records that have <code>type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfType(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.TYPE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByType(UByte... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.TYPE, values);
    }

    /**
     * Fetch records that have <code>referenced_type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfReferencedType(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.REFERENCED_TYPE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>referenced_type IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByReferencedType(UByte... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.REFERENCED_TYPE, values);
    }

    /**
     * Fetch records that have <code>referenced_identifier BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfReferencedIdentifier(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.REFERENCED_IDENTIFIER, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>referenced_identifier IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByReferencedIdentifier(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.REFERENCED_IDENTIFIER, values);
    }

    /**
     * Fetch records that have <code>description BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfDescription(String lowerInclusive, String upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.DESCRIPTION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByDescription(String... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfCreateTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.CREATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByCreateTime(LocalDateTime... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfCreateUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.CREATE_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByCreateUserId(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.CREATE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_modify_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfLastModifyTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.LAST_MODIFY_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByLastModifyTime(LocalDateTime... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.LAST_MODIFY_TIME, values);
    }

    /**
     * Fetch records that have <code>last_modify_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfLastModifyUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.LAST_MODIFY_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByLastModifyUserId(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.LAST_MODIFY_USER_ID, values);
    }

    /**
     * Fetch records that have <code>is_deleted BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchRangeOfIsDeleted(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PermissionTarget.PERMISSION_TARGET.IS_DELETED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>is_deleted IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.security.tables.pojos.PermissionTarget> fetchByIsDeleted(ULong... values) {
        return fetch(PermissionTarget.PERMISSION_TARGET.IS_DELETED, values);
    }
}
