/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.commons.tables.records;


import com.wuda.foundation.jooq.code.generation.commons.tables.PropertyMount;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 属性模板的挂载点，比如对于商品来说，相同分类下的商品属性很擂台，因此可以把属性模板挂载到商品分类上，在新建商品时就可以推荐相关的属性模板了
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PropertyMountRecord extends UpdatableRecordImpl<PropertyMountRecord> implements Record7<ULong, ULong, UByte, ULong, LocalDateTime, ULong, ULong> {

    private static final long serialVersionUID = -1355469097;

    /**
     * Setter for <code>foundation_commons.property_mount.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.id</code>.
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.property_key_id</code>.
     */
    public void setPropertyKeyId(ULong value) {
        set(1, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.property_key_id</code>.
     */
    public ULong getPropertyKeyId() {
        return (ULong) get(1);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.mount_point_type</code>. 挂载点类型
     */
    public void setMountPointType(UByte value) {
        set(2, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.mount_point_type</code>. 挂载点类型
     */
    public UByte getMountPointType() {
        return (UByte) get(2);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.mount_point_idenfier</code>. 挂载点唯一标记
     */
    public void setMountPointIdenfier(ULong value) {
        set(3, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.mount_point_idenfier</code>. 挂载点唯一标记
     */
    public ULong getMountPointIdenfier() {
        return (ULong) get(3);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.create_time</code>.
     */
    public void setCreateTime(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.create_time</code>.
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.create_user_id</code>.
     */
    public void setCreateUserId(ULong value) {
        set(5, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.create_user_id</code>.
     */
    public ULong getCreateUserId() {
        return (ULong) get(5);
    }

    /**
     * Setter for <code>foundation_commons.property_mount.is_deleted</code>.
     */
    public void setIsDeleted(ULong value) {
        set(6, value);
    }

    /**
     * Getter for <code>foundation_commons.property_mount.is_deleted</code>.
     */
    public ULong getIsDeleted() {
        return (ULong) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<ULong, ULong, UByte, ULong, LocalDateTime, ULong, ULong> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<ULong, ULong, UByte, ULong, LocalDateTime, ULong, ULong> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<ULong> field1() {
        return PropertyMount.PROPERTY_MOUNT.ID;
    }

    @Override
    public Field<ULong> field2() {
        return PropertyMount.PROPERTY_MOUNT.PROPERTY_KEY_ID;
    }

    @Override
    public Field<UByte> field3() {
        return PropertyMount.PROPERTY_MOUNT.MOUNT_POINT_TYPE;
    }

    @Override
    public Field<ULong> field4() {
        return PropertyMount.PROPERTY_MOUNT.MOUNT_POINT_IDENFIER;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return PropertyMount.PROPERTY_MOUNT.CREATE_TIME;
    }

    @Override
    public Field<ULong> field6() {
        return PropertyMount.PROPERTY_MOUNT.CREATE_USER_ID;
    }

    @Override
    public Field<ULong> field7() {
        return PropertyMount.PROPERTY_MOUNT.IS_DELETED;
    }

    @Override
    public ULong component1() {
        return getId();
    }

    @Override
    public ULong component2() {
        return getPropertyKeyId();
    }

    @Override
    public UByte component3() {
        return getMountPointType();
    }

    @Override
    public ULong component4() {
        return getMountPointIdenfier();
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
    public ULong component7() {
        return getIsDeleted();
    }

    @Override
    public ULong value1() {
        return getId();
    }

    @Override
    public ULong value2() {
        return getPropertyKeyId();
    }

    @Override
    public UByte value3() {
        return getMountPointType();
    }

    @Override
    public ULong value4() {
        return getMountPointIdenfier();
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
    public ULong value7() {
        return getIsDeleted();
    }

    @Override
    public PropertyMountRecord value1(ULong value) {
        setId(value);
        return this;
    }

    @Override
    public PropertyMountRecord value2(ULong value) {
        setPropertyKeyId(value);
        return this;
    }

    @Override
    public PropertyMountRecord value3(UByte value) {
        setMountPointType(value);
        return this;
    }

    @Override
    public PropertyMountRecord value4(ULong value) {
        setMountPointIdenfier(value);
        return this;
    }

    @Override
    public PropertyMountRecord value5(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public PropertyMountRecord value6(ULong value) {
        setCreateUserId(value);
        return this;
    }

    @Override
    public PropertyMountRecord value7(ULong value) {
        setIsDeleted(value);
        return this;
    }

    @Override
    public PropertyMountRecord values(ULong value1, ULong value2, UByte value3, ULong value4, LocalDateTime value5, ULong value6, ULong value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PropertyMountRecord
     */
    public PropertyMountRecord() {
        super(PropertyMount.PROPERTY_MOUNT);
    }

    /**
     * Create a detached, initialised PropertyMountRecord
     */
    public PropertyMountRecord(ULong id, ULong propertyKeyId, UByte mountPointType, ULong mountPointIdenfier, LocalDateTime createTime, ULong createUserId, ULong isDeleted) {
        super(PropertyMount.PROPERTY_MOUNT);

        set(0, id);
        set(1, propertyKeyId);
        set(2, mountPointType);
        set(3, mountPointIdenfier);
        set(4, createTime);
        set(5, createUserId);
        set(6, isDeleted);
    }
}
