package com.wuda.foundation.commons;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.InsertMode;

import java.util.List;

public abstract class AbstractPhoneManager implements PhoneManager {

    @Override
    public Long createPhone(CreatePhone createPhone, InsertMode insertMode, Long opUserId) {
        return createPhoneDbOp(createPhone, insertMode, opUserId);
    }

    /**
     * 作为{@link #createPhone(CreatePhone, InsertMode, Long)}方法的一部分,参数的校验已经在{@link #createPhone(CreatePhone, InsertMode, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createPhone phone
     * @param opUserId    操作人用户ID
     * @return 记录ID
     */
    protected abstract Long createPhoneDbOp(CreatePhone createPhone, InsertMode insertMode, Long opUserId);

    @Override
    public void createPhone(List<CreatePhone> phones, Long opUserId) {
        ExtObjects.requireNonNull(phones, opUserId);
        createPhoneDbOp(phones, opUserId);
    }

    /**
     * 作为{@link #createPhone(List, Long)}方法的一部分,参数的校验已经在{@link #createPhone(List, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param phones   phone
     * @param opUserId 操作人用户ID
     */
    protected abstract void createPhoneDbOp(List<CreatePhone> phones, Long opUserId);
}
