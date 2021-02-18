/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables;


import com.wuda.foundation.jooq.code.generation.user.FoundationUser;
import com.wuda.foundation.jooq.code.generation.user.Indexes;
import com.wuda.foundation.jooq.code.generation.user.Keys;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserAccountRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
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
 * 用户账号信息，适用各种类型的用户
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAccount extends TableImpl<UserAccountRecord> {

    private static final long serialVersionUID = -767094037;

    /**
     * The reference instance of <code>foundation_user.user_account</code>
     */
    public static final UserAccount USER_ACCOUNT = new UserAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserAccountRecord> getRecordType() {
        return UserAccountRecord.class;
    }

    /**
     * The column <code>foundation_user.user_account.user_account_id</code>.
     */
    public final TableField<UserAccountRecord, ULong> USER_ACCOUNT_ID = createField(DSL.name("user_account_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>foundation_user.user_account.user_id</code>. 用户ID
     */
    public final TableField<UserAccountRecord, ULong> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "用户ID");

    /**
     * The column <code>foundation_user.user_account.username</code>. 只能是英文模式下的字母，数字，下划线，中划线，必须明确检查保证不是邮箱。设置以后不能修改(github可以修改)，可用作用户主页URL的一部分，参考github。注意和昵称的区别
     */
    public final TableField<UserAccountRecord, String> USERNAME = createField(DSL.name("username"), org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "只能是英文模式下的字母，数字，下划线，中划线，必须明确检查保证不是邮箱。设置以后不能修改(github可以修改)，可用作用户主页URL的一部分，参考github。注意和昵称的区别");

    /**
     * The column <code>foundation_user.user_account.password</code>. 只能是ASCII表中的可打印字符
     */
    public final TableField<UserAccountRecord, String> PASSWORD = createField(DSL.name("password"), org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "只能是ASCII表中的可打印字符");

    /**
     * The column <code>foundation_user.user_account.state</code>. 账号的状态
     */
    public final TableField<UserAccountRecord, UByte> STATE = createField(DSL.name("state"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "账号的状态");

    /**
     * The column <code>foundation_user.user_account.create_time</code>.
     */
    public final TableField<UserAccountRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_user.user_account.create_user_id</code>.
     */
    public final TableField<UserAccountRecord, ULong> CREATE_USER_ID = createField(DSL.name("create_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_user.user_account.last_modify_time</code>.
     */
    public final TableField<UserAccountRecord, LocalDateTime> LAST_MODIFY_TIME = createField(DSL.name("last_modify_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_user.user_account.last_modify_user_id</code>.
     */
    public final TableField<UserAccountRecord, ULong> LAST_MODIFY_USER_ID = createField(DSL.name("last_modify_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_user.user_account.is_deleted</code>.
     */
    public final TableField<UserAccountRecord, ULong> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "");

    /**
     * Create a <code>foundation_user.user_account</code> table reference
     */
    public UserAccount() {
        this(DSL.name("user_account"), null);
    }

    /**
     * Create an aliased <code>foundation_user.user_account</code> table reference
     */
    public UserAccount(String alias) {
        this(DSL.name(alias), USER_ACCOUNT);
    }

    /**
     * Create an aliased <code>foundation_user.user_account</code> table reference
     */
    public UserAccount(Name alias) {
        this(alias, USER_ACCOUNT);
    }

    private UserAccount(Name alias, Table<UserAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserAccount(Name alias, Table<UserAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户账号信息，适用各种类型的用户"), TableOptions.table());
    }

    public <O extends Record> UserAccount(Table<O> child, ForeignKey<O, UserAccountRecord> key) {
        super(child, key, USER_ACCOUNT);
    }

    @Override
    public Schema getSchema() {
        return FoundationUser.FOUNDATION_USER;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_ACCOUNT_IDX_USER_ID);
    }

    @Override
    public Identity<UserAccountRecord, ULong> getIdentity() {
        return Keys.IDENTITY_USER_ACCOUNT;
    }

    @Override
    public UniqueKey<UserAccountRecord> getPrimaryKey() {
        return Keys.KEY_USER_ACCOUNT_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<UserAccountRecord>>asList(Keys.KEY_USER_ACCOUNT_PRIMARY, Keys.KEY_USER_ACCOUNT_IDX_USERNAME);
    }

    @Override
    public UserAccount as(String alias) {
        return new UserAccount(DSL.name(alias), this);
    }

    @Override
    public UserAccount as(Name alias) {
        return new UserAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAccount rename(String name) {
        return new UserAccount(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAccount rename(Name name) {
        return new UserAccount(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<ULong, ULong, String, String, UByte, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
