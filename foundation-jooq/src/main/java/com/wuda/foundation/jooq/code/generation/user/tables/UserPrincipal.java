/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables;


import com.wuda.foundation.jooq.code.generation.user.FoundationUser;
import com.wuda.foundation.jooq.code.generation.user.Indexes;
import com.wuda.foundation.jooq.code.generation.user.Keys;
import com.wuda.foundation.jooq.code.generation.user.tables.records.UserPrincipalRecord;

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
 * 用户的身份标记，比如用户名就是一种principal
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPrincipal extends TableImpl<UserPrincipalRecord> {

    private static final long serialVersionUID = -532780273;

    /**
     * The reference instance of <code>foundation_user.user_principal</code>
     */
    public static final UserPrincipal USER_PRINCIPAL = new UserPrincipal();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserPrincipalRecord> getRecordType() {
        return UserPrincipalRecord.class;
    }

    /**
     * The column <code>foundation_user.user_principal.user_pricinpal_id</code>.
     */
    public final TableField<UserPrincipalRecord, ULong> USER_PRICINPAL_ID = createField(DSL.name("user_pricinpal_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>foundation_user.user_principal.user_id</code>.
     */
    public final TableField<UserPrincipalRecord, ULong> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_user.user_principal.type</code>. principal type，比如该principal是username
     */
    public final TableField<UserPrincipalRecord, UByte> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "principal type，比如该principal是username");

    /**
     * The column <code>foundation_user.user_principal.name</code>. principal name
     */
    public final TableField<UserPrincipalRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(45).nullable(false), this, "principal name");

    /**
     * The column <code>foundation_user.user_principal.description</code>.
     */
    public final TableField<UserPrincipalRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(245).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>foundation_user.user_principal.create_time</code>.
     */
    public final TableField<UserPrincipalRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_user.user_principal.create_user_id</code>.
     */
    public final TableField<UserPrincipalRecord, ULong> CREATE_USER_ID = createField(DSL.name("create_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_user.user_principal.last_modify_time</code>.
     */
    public final TableField<UserPrincipalRecord, LocalDateTime> LAST_MODIFY_TIME = createField(DSL.name("last_modify_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_user.user_principal.last_modify_user_id</code>.
     */
    public final TableField<UserPrincipalRecord, ULong> LAST_MODIFY_USER_ID = createField(DSL.name("last_modify_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_user.user_principal.is_deleted</code>.
     */
    public final TableField<UserPrincipalRecord, ULong> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "");

    /**
     * Create a <code>foundation_user.user_principal</code> table reference
     */
    public UserPrincipal() {
        this(DSL.name("user_principal"), null);
    }

    /**
     * Create an aliased <code>foundation_user.user_principal</code> table reference
     */
    public UserPrincipal(String alias) {
        this(DSL.name(alias), USER_PRINCIPAL);
    }

    /**
     * Create an aliased <code>foundation_user.user_principal</code> table reference
     */
    public UserPrincipal(Name alias) {
        this(alias, USER_PRINCIPAL);
    }

    private UserPrincipal(Name alias, Table<UserPrincipalRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserPrincipal(Name alias, Table<UserPrincipalRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户的身份标记，比如用户名就是一种principal"), TableOptions.table());
    }

    public <O extends Record> UserPrincipal(Table<O> child, ForeignKey<O, UserPrincipalRecord> key) {
        super(child, key, USER_PRINCIPAL);
    }

    @Override
    public Schema getSchema() {
        return FoundationUser.FOUNDATION_USER;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_PRINCIPAL_USER_ID_IDX);
    }

    @Override
    public Identity<UserPrincipalRecord, ULong> getIdentity() {
        return Keys.IDENTITY_USER_PRINCIPAL;
    }

    @Override
    public UniqueKey<UserPrincipalRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRINCIPAL_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserPrincipalRecord>> getKeys() {
        return Arrays.<UniqueKey<UserPrincipalRecord>>asList(Keys.KEY_USER_PRINCIPAL_PRIMARY);
    }

    @Override
    public UserPrincipal as(String alias) {
        return new UserPrincipal(DSL.name(alias), this);
    }

    @Override
    public UserPrincipal as(Name alias) {
        return new UserPrincipal(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPrincipal rename(String name) {
        return new UserPrincipal(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPrincipal rename(Name name) {
        return new UserPrincipal(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<ULong, ULong, UByte, String, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
