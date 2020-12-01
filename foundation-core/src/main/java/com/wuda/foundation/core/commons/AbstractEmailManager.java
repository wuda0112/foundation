package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.ExtObjects;

import java.util.List;

public abstract class AbstractEmailManager implements EmailManager {

    @Override
    public Long createEmail(CreateEmail createEmail, Long opUserId) throws AlreadyExistsException {
        return createEmailDbOp(createEmail, opUserId);
    }

    protected abstract Long createEmailDbOp(CreateEmail createEmail, Long opUserId) throws AlreadyExistsException;

    @Override
    public void directBatchInsertEmail(List<CreateEmail> emails, Long opUserId) {
        ExtObjects.requireNonNull(emails, opUserId);
        directBatchInsertEmailDbOp(emails, opUserId);
    }

    /**
     * 作为{@link #directBatchInsertEmail(List, Long)}方法的一部分,参数的校验已经在{@link #directBatchInsertEmail(List, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param emails   email
     * @param opUserId 操作人用户ID
     */
    protected abstract void directBatchInsertEmailDbOp(List<CreateEmail> emails, Long opUserId);

}
