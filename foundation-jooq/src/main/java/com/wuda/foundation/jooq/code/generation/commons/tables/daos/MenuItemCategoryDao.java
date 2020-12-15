/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables.daos;


import com.wuda.foundation.jooq.code.generation.commons.tables.MenuItemCategory;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.MenuItemCategoryRecord;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 物品分类
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MenuItemCategoryDao extends DAOImpl<MenuItemCategoryRecord, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory, ULong> {

    /**
     * Create a new MenuItemCategoryDao without any configuration
     */
    public MenuItemCategoryDao() {
        super(MenuItemCategory.MENU_ITEM_CATEGORY, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory.class);
    }

    /**
     * Create a new MenuItemCategoryDao with an attached configuration
     */
    public MenuItemCategoryDao(Configuration configuration) {
        super(MenuItemCategory.MENU_ITEM_CATEGORY, com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory.class, configuration);
    }

    @Override
    public ULong getId(com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory object) {
        return object.getMenuItemCategoryId();
    }

    /**
     * Fetch records that have <code>menu_item_category_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfMenuItemCategoryId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>menu_item_category_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByMenuItemCategoryId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID, values);
    }

    /**
     * Fetch a unique record that has <code>menu_item_category_id = value</code>
     */
    public com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory fetchOneByMenuItemCategoryId(ULong value) {
        return fetchOne(MenuItemCategory.MENU_ITEM_CATEGORY.MENU_ITEM_CATEGORY_ID, value);
    }

    /**
     * Fetch records that have <code>menu_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfMenuId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.MENU_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>menu_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByMenuId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.MENU_ID, values);
    }

    /**
     * Fetch records that have <code>parent_menu_item_category_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfParentMenuItemCategoryId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.PARENT_MENU_ITEM_CATEGORY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>parent_menu_item_category_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByParentMenuItemCategoryId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.PARENT_MENU_ITEM_CATEGORY_ID, values);
    }

    /**
     * Fetch records that have <code>root_menu_item_category_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfRootMenuItemCategoryId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.ROOT_MENU_ITEM_CATEGORY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>root_menu_item_category_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByRootMenuItemCategoryId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.ROOT_MENU_ITEM_CATEGORY_ID, values);
    }

    /**
     * Fetch records that have <code>depth BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfDepth(UByte lowerInclusive, UByte upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.DEPTH, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>depth IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByDepth(UByte... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.DEPTH, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByName(String... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.NAME, values);
    }

    /**
     * Fetch records that have <code>description BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfDescription(String lowerInclusive, String upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.DESCRIPTION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByDescription(String... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfCreateTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.CREATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByCreateTime(LocalDateTime... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfCreateUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.CREATE_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByCreateUserId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.CREATE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>last_modify_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfLastModifyTime(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.LAST_MODIFY_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_time IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByLastModifyTime(LocalDateTime... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.LAST_MODIFY_TIME, values);
    }

    /**
     * Fetch records that have <code>last_modify_user_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfLastModifyUserId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.LAST_MODIFY_USER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_modify_user_id IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByLastModifyUserId(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.LAST_MODIFY_USER_ID, values);
    }

    /**
     * Fetch records that have <code>is_deleted BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchRangeOfIsDeleted(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(MenuItemCategory.MENU_ITEM_CATEGORY.IS_DELETED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>is_deleted IN (values)</code>
     */
    public List<com.wuda.foundation.jooq.code.generation.commons.tables.pojos.MenuItemCategory> fetchByIsDeleted(ULong... values) {
        return fetch(MenuItemCategory.MENU_ITEM_CATEGORY.IS_DELETED, values);
    }
}
