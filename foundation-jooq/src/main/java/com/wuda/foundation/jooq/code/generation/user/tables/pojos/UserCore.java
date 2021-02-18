/*
 * This file is generated by jOOQ.
 */
package com.wuda.foundation.jooq.code.generation.user.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 用户核心信息。用户有很多类型，比如一种分类方法是把用户分成个人用户和企业用户，而不同类型的用户需要的字段不一样，但是他们都是用户，即 is-a 
 * user。这个表属于所有用户的基本信息，其他不同类型的用户有自己专属的表，然后用用户ID关联回这个表。这样做还有一个好处，那就是其他表中的用户ID都统一关联回这个表，这样用户ID就不会有歧义了。
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCore implements Serializable {

    private static final long serialVersionUID = -1070168464;

    private ULong         userCoreId;
    private ULong         userId;
    private UByte         type;
    private UByte         state;
    private LocalDateTime createTime;
    private ULong         createUserId;
    private LocalDateTime lastModifyTime;
    private ULong         lastModifyUserId;
    private ULong         isDeleted;

    public UserCore() {}

    public UserCore(UserCore value) {
        this.userCoreId = value.userCoreId;
        this.userId = value.userId;
        this.type = value.type;
        this.state = value.state;
        this.createTime = value.createTime;
        this.createUserId = value.createUserId;
        this.lastModifyTime = value.lastModifyTime;
        this.lastModifyUserId = value.lastModifyUserId;
        this.isDeleted = value.isDeleted;
    }

    public UserCore(
        ULong         userCoreId,
        ULong         userId,
        UByte         type,
        UByte         state,
        LocalDateTime createTime,
        ULong         createUserId,
        LocalDateTime lastModifyTime,
        ULong         lastModifyUserId,
        ULong         isDeleted
    ) {
        this.userCoreId = userCoreId;
        this.userId = userId;
        this.type = type;
        this.state = state;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.lastModifyTime = lastModifyTime;
        this.lastModifyUserId = lastModifyUserId;
        this.isDeleted = isDeleted;
    }

    public ULong getUserCoreId() {
        return this.userCoreId;
    }

    public void setUserCoreId(ULong userCoreId) {
        this.userCoreId = userCoreId;
    }

    public ULong getUserId() {
        return this.userId;
    }

    public void setUserId(ULong userId) {
        this.userId = userId;
    }

    public UByte getType() {
        return this.type;
    }

    public void setType(UByte type) {
        this.type = type;
    }

    public UByte getState() {
        return this.state;
    }

    public void setState(UByte state) {
        this.state = state;
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
        StringBuilder sb = new StringBuilder("UserCore (");

        sb.append(userCoreId);
        sb.append(", ").append(userId);
        sb.append(", ").append(type);
        sb.append(", ").append(state);
        sb.append(", ").append(createTime);
        sb.append(", ").append(createUserId);
        sb.append(", ").append(lastModifyTime);
        sb.append(", ").append(lastModifyUserId);
        sb.append(", ").append(isDeleted);

        sb.append(")");
        return sb.toString();
    }
}
