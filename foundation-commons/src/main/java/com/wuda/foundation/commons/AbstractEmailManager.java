package com.wuda.foundation.commons;

import com.wuda.foundation.lang.ExtObjects;
import com.wuda.foundation.lang.InsertMode;

import java.util.List;

public abstract class AbstractEmailManager implements EmailManager {

    @Override
    public Long createEmail(CreateEmail createEmail, InsertMode insertMode, Long opUserId) {
        return createEmailDbOp(createEmail, insertMode, opUserId);
    }

    /**
     * 作为{@link #createEmail(CreateEmail, InsertMode, Long)}方法的一部分,参数的校验已经在{@link #createEmail(CreateEmail, InsertMode, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createEmail email
     * @param opUserId    操作人用户ID
     * @return 记录ID
     */
    protected abstract Long createEmailDbOp(CreateEmail createEmail, InsertMode insertMode, Long opUserId);

    @Override
    public void createEmail(List<CreateEmail> emails, Long opUserId) {
        ExtObjects.requireNonNull(emails, opUserId);
        createEmailDbOp(emails, opUserId);
    }

    /**
     * 作为{@link #createEmail(List, Long)}方法的一部分,参数的校验已经在{@link #createEmail(List, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param emails   email
     * @param opUserId 操作人用户ID
     */
    protected abstract void createEmailDbOp(List<CreateEmail> emails, Long opUserId);

}
