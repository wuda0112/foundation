package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.ExtObjects;

import java.util.List;

public abstract class AbstractPhoneManager implements PhoneManager {

    @Override
    public Long createPhone(CreatePhone createPhone, Long opUserId) throws AlreadyExistsException {
        return createPhoneDbOp(createPhone, opUserId);
    }

    protected abstract Long createPhoneDbOp(CreatePhone createPhone, Long opUserId) throws AlreadyExistsException;

    @Override
    public void directBatchInsertPhone(List<CreatePhone> phones, Long opUserId) {
        ExtObjects.requireNonNull(phones, opUserId);
        directBatchInsertPhoneDbOp(phones, opUserId);
    }

    /**
     * 作为{@link #directBatchInsertPhone(List, Long)}方法的一部分,参数的校验已经在{@link #directBatchInsertPhone(List, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param phones   phone
     * @param opUserId 操作人用户ID
     */
    protected abstract void directBatchInsertPhoneDbOp(List<CreatePhone> phones, Long opUserId);
}
