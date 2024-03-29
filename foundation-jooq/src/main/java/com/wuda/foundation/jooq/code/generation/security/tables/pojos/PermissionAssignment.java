/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.security.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.jooq.types.UShort;


/**
 * 权限分配。subject可以代表任何主体，比如用户，或者想要访问其他资源的应用，因此我们可以说user 【IS A】 subject 。target可以代表任何对象，比如file，因此我们可以说file 
 * 【IS A】 target。action可以代表任何操作，比如read/write。subject , target , action这三个实体，不一定是某个具体的单个实体，也可以是一类实体，比如target如果是文件夹，那么可以代表subject对这个文件夹下的所有文件以及子文件夹（递归）都拥有权限；同样
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PermissionAssignment implements Serializable {

    private static final long serialVersionUID = 1313554342;

    private ULong         id;
    private UByte         subjectType;
    private ULong         subjectIdentifier;
    private UShort        targetType;
    private ULong         targetIdentifier;
    private UShort        actionType;
    private ULong         actionIdentifier;
    private Boolean       effect;
    private ULong         version;
    private LocalDateTime createTime;
    private ULong         createUserId;
    private ULong         isDeleted;

    public PermissionAssignment() {}

    public PermissionAssignment(PermissionAssignment value) {
        this.id = value.id;
        this.subjectType = value.subjectType;
        this.subjectIdentifier = value.subjectIdentifier;
        this.targetType = value.targetType;
        this.targetIdentifier = value.targetIdentifier;
        this.actionType = value.actionType;
        this.actionIdentifier = value.actionIdentifier;
        this.effect = value.effect;
        this.version = value.version;
        this.createTime = value.createTime;
        this.createUserId = value.createUserId;
        this.isDeleted = value.isDeleted;
    }

    public PermissionAssignment(
        ULong         id,
        UByte         subjectType,
        ULong         subjectIdentifier,
        UShort        targetType,
        ULong         targetIdentifier,
        UShort        actionType,
        ULong         actionIdentifier,
        Boolean       effect,
        ULong         version,
        LocalDateTime createTime,
        ULong         createUserId,
        ULong         isDeleted
    ) {
        this.id = id;
        this.subjectType = subjectType;
        this.subjectIdentifier = subjectIdentifier;
        this.targetType = targetType;
        this.targetIdentifier = targetIdentifier;
        this.actionType = actionType;
        this.actionIdentifier = actionIdentifier;
        this.effect = effect;
        this.version = version;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.isDeleted = isDeleted;
    }

    public ULong getId() {
        return this.id;
    }

    public void setId(ULong id) {
        this.id = id;
    }

    public UByte getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(UByte subjectType) {
        this.subjectType = subjectType;
    }

    public ULong getSubjectIdentifier() {
        return this.subjectIdentifier;
    }

    public void setSubjectIdentifier(ULong subjectIdentifier) {
        this.subjectIdentifier = subjectIdentifier;
    }

    public UShort getTargetType() {
        return this.targetType;
    }

    public void setTargetType(UShort targetType) {
        this.targetType = targetType;
    }

    public ULong getTargetIdentifier() {
        return this.targetIdentifier;
    }

    public void setTargetIdentifier(ULong targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public UShort getActionType() {
        return this.actionType;
    }

    public void setActionType(UShort actionType) {
        this.actionType = actionType;
    }

    public ULong getActionIdentifier() {
        return this.actionIdentifier;
    }

    public void setActionIdentifier(ULong actionIdentifier) {
        this.actionIdentifier = actionIdentifier;
    }

    public Boolean getEffect() {
        return this.effect;
    }

    public void setEffect(Boolean effect) {
        this.effect = effect;
    }

    public ULong getVersion() {
        return this.version;
    }

    public void setVersion(ULong version) {
        this.version = version;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public ULong getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(ULong createUserId) {
        this.createUserId = createUserId;
    }

    public ULong getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(ULong isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PermissionAssignment (");

        sb.append(id);
        sb.append(", ").append(subjectType);
        sb.append(", ").append(subjectIdentifier);
        sb.append(", ").append(targetType);
        sb.append(", ").append(targetIdentifier);
        sb.append(", ").append(actionType);
        sb.append(", ").append(actionIdentifier);
        sb.append(", ").append(effect);
        sb.append(", ").append(version);
        sb.append(", ").append(createTime);
        sb.append(", ").append(createUserId);
        sb.append(", ").append(isDeleted);

        sb.append(")");
        return sb.toString();
    }
}
