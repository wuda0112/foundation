/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 用户的电话
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPhone implements Serializable {

    private static final long serialVersionUID = 1268458379;

    private ULong         id;
    private ULong         userId;
    private ULong         phoneId;
    private UByte         usedFor;
    private UByte         state;
    private String        description;
    private LocalDateTime createTime;
    private ULong         createUserId;
    private LocalDateTime lastModifyTime;
    private ULong         lastModifyUserId;
    private ULong         isDeleted;

    public UserPhone() {}

    public UserPhone(UserPhone value) {
        this.id = value.id;
        this.userId = value.userId;
        this.phoneId = value.phoneId;
        this.usedFor = value.usedFor;
        this.state = value.state;
        this.description = value.description;
        this.createTime = value.createTime;
        this.createUserId = value.createUserId;
        this.lastModifyTime = value.lastModifyTime;
        this.lastModifyUserId = value.lastModifyUserId;
        this.isDeleted = value.isDeleted;
    }

    public UserPhone(
        ULong         id,
        ULong         userId,
        ULong         phoneId,
        UByte         usedFor,
        UByte         state,
        String        description,
        LocalDateTime createTime,
        ULong         createUserId,
        LocalDateTime lastModifyTime,
        ULong         lastModifyUserId,
        ULong         isDeleted
    ) {
        this.id = id;
        this.userId = userId;
        this.phoneId = phoneId;
        this.usedFor = usedFor;
        this.state = state;
        this.description = description;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.lastModifyTime = lastModifyTime;
        this.lastModifyUserId = lastModifyUserId;
        this.isDeleted = isDeleted;
    }

    public ULong getId() {
        return this.id;
    }

    public void setId(ULong id) {
        this.id = id;
    }

    public ULong getUserId() {
        return this.userId;
    }

    public void setUserId(ULong userId) {
        this.userId = userId;
    }

    public ULong getPhoneId() {
        return this.phoneId;
    }

    public void setPhoneId(ULong phoneId) {
        this.phoneId = phoneId;
    }

    public UByte getUsedFor() {
        return this.usedFor;
    }

    public void setUsedFor(UByte usedFor) {
        this.usedFor = usedFor;
    }

    public UByte getState() {
        return this.state;
    }

    public void setState(UByte state) {
        this.state = state;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(LocalDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public ULong getLastModifyUserId() {
        return this.lastModifyUserId;
    }

    public void setLastModifyUserId(ULong lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    public ULong getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(ULong isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserPhone (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(phoneId);
        sb.append(", ").append(usedFor);
        sb.append(", ").append(state);
        sb.append(", ").append(description);
        sb.append(", ").append(createTime);
        sb.append(", ").append(createUserId);
        sb.append(", ").append(lastModifyTime);
        sb.append(", ").append(lastModifyUserId);
        sb.append(", ").append(isDeleted);

        sb.append(")");
        return sb.toString();
    }
}
