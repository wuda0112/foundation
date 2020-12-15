/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables;


import com.wuda.foundation.jooq.code.generation.commons.FoundationCommons;
import com.wuda.foundation.jooq.code.generation.commons.Keys;
import com.wuda.foundation.jooq.code.generation.commons.tables.records.AdministrativeUnitRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.jooq.types.UShort;


/**
 * 行政单元，行政区域划分。也就是省市区等数据，不按省市区建表，因为不一定是按那样划分，比如北京，深圳，但是他们有一个共同特点，那就是有上下级关系，因此用ID/PID的方式组织。
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AdministrativeUnit extends TableImpl<AdministrativeUnitRecord> {

    private static final long serialVersionUID = -43037878;

    /**
     * The reference instance of <code>foundation_commons.administrative_unit</code>
     */
    public static final AdministrativeUnit ADMINISTRATIVE_UNIT = new AdministrativeUnit();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AdministrativeUnitRecord> getRecordType() {
        return AdministrativeUnitRecord.class;
    }

    /**
     * The column <code>foundation_commons.administrative_unit.administrative_unit_id</code>. 必须显示指定，如果用自增，会导致parent_id找不到正确的父级
     */
    public final TableField<AdministrativeUnitRecord, UShort> ADMINISTRATIVE_UNIT_ID = createField(DSL.name("administrative_unit_id"), org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "必须显示指定，如果用自增，会导致parent_id找不到正确的父级");

    /**
     * The column <code>foundation_commons.administrative_unit.parent_id</code>.
     */
    public final TableField<AdministrativeUnitRecord, UShort> PARENT_ID = createField(DSL.name("parent_id"), org.jooq.impl.SQLDataType.SMALLINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_commons.administrative_unit.full_name</code>. 全名，比如：湖南省
     */
    public final TableField<AdministrativeUnitRecord, String> FULL_NAME = createField(DSL.name("full_name"), org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "全名，比如：湖南省");

    /**
     * The column <code>foundation_commons.administrative_unit.short_name</code>. 简写，比如：湖南省的简写是湖南
     */
    public final TableField<AdministrativeUnitRecord, String> SHORT_NAME = createField(DSL.name("short_name"), org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "简写，比如：湖南省的简写是湖南");

    /**
     * The column <code>foundation_commons.administrative_unit.alias</code>. 比如：湖南的alias是湘，广州的alias是羊城
     */
    public final TableField<AdministrativeUnitRecord, String> ALIAS = createField(DSL.name("alias"), org.jooq.impl.SQLDataType.VARCHAR(5).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "比如：湖南的alias是湘，广州的alias是羊城");

    /**
     * The column <code>foundation_commons.administrative_unit.level</code>. 级别，比如湖南省处于第一级，北京市也是第一级
     */
    public final TableField<AdministrativeUnitRecord, UByte> LEVEL = createField(DSL.name("level"), org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "级别，比如湖南省处于第一级，北京市也是第一级");

    /**
     * The column <code>foundation_commons.administrative_unit.level_name</code>. 比如湖南省，level_name=省，北京市的level_name=市
     */
    public final TableField<AdministrativeUnitRecord, String> LEVEL_NAME = createField(DSL.name("level_name"), org.jooq.impl.SQLDataType.VARCHAR(5).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "比如湖南省，level_name=省，北京市的level_name=市");

    /**
     * The column <code>foundation_commons.administrative_unit.create_time</code>.
     */
    public final TableField<AdministrativeUnitRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_commons.administrative_unit.create_user_id</code>.
     */
    public final TableField<AdministrativeUnitRecord, ULong> CREATE_USER_ID = createField(DSL.name("create_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_commons.administrative_unit.last_modify_time</code>.
     */
    public final TableField<AdministrativeUnitRecord, LocalDateTime> LAST_MODIFY_TIME = createField(DSL.name("last_modify_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>foundation_commons.administrative_unit.last_modify_user_id</code>.
     */
    public final TableField<AdministrativeUnitRecord, ULong> LAST_MODIFY_USER_ID = createField(DSL.name("last_modify_user_id"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>foundation_commons.administrative_unit.is_deleted</code>.
     */
    public final TableField<AdministrativeUnitRecord, ULong> IS_DELETED = createField(DSL.name("is_deleted"), org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), this, "");

    /**
     * Create a <code>foundation_commons.administrative_unit</code> table reference
     */
    public AdministrativeUnit() {
        this(DSL.name("administrative_unit"), null);
    }

    /**
     * Create an aliased <code>foundation_commons.administrative_unit</code> table reference
     */
    public AdministrativeUnit(String alias) {
        this(DSL.name(alias), ADMINISTRATIVE_UNIT);
    }

    /**
     * Create an aliased <code>foundation_commons.administrative_unit</code> table reference
     */
    public AdministrativeUnit(Name alias) {
        this(alias, ADMINISTRATIVE_UNIT);
    }

    private AdministrativeUnit(Name alias, Table<AdministrativeUnitRecord> aliased) {
        this(alias, aliased, null);
    }

    private AdministrativeUnit(Name alias, Table<AdministrativeUnitRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("行政单元，行政区域划分。也就是省市区等数据，不按省市区建表，因为不一定是按那样划分，比如北京，深圳，但是他们有一个共同特点，那就是有上下级关系，因此用ID/PID的方式组织。"), TableOptions.table());
    }

    public <O extends Record> AdministrativeUnit(Table<O> child, ForeignKey<O, AdministrativeUnitRecord> key) {
        super(child, key, ADMINISTRATIVE_UNIT);
    }

    @Override
    public Schema getSchema() {
        return FoundationCommons.FOUNDATION_COMMONS;
    }

    @Override
    public UniqueKey<AdministrativeUnitRecord> getPrimaryKey() {
        return Keys.KEY_ADMINISTRATIVE_UNIT_PRIMARY;
    }

    @Override
    public List<UniqueKey<AdministrativeUnitRecord>> getKeys() {
        return Arrays.<UniqueKey<AdministrativeUnitRecord>>asList(Keys.KEY_ADMINISTRATIVE_UNIT_PRIMARY);
    }

    @Override
    public AdministrativeUnit as(String alias) {
        return new AdministrativeUnit(DSL.name(alias), this);
    }

    @Override
    public AdministrativeUnit as(Name alias) {
        return new AdministrativeUnit(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AdministrativeUnit rename(String name) {
        return new AdministrativeUnit(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AdministrativeUnit rename(Name name) {
        return new AdministrativeUnit(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<UShort, UShort, String, String, String, UByte, String, LocalDateTime, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
