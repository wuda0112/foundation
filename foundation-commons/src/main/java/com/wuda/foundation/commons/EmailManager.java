package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;

import java.util.List;

/**
 * email manager.
 *
 * @author wuda
 */
public interface EmailManager {

    /**
     * 添加email.
     *
     * @param createEmail email
     * @param opUserId    操作人用户ID
     * @return 新增的记录的ID
     * @throws AlreadyExistsException 如果email已经存在
     */
    Long createEmail(CreateEmail createEmail, Long opUserId) throws AlreadyExistsException;

    /**
     * 批量添加email.
     *
     * @param emails   email
     * @param opUserId 操作人用户ID
     */
    void directBatchInsertEmail(List<CreateEmail> emails, Long opUserId);
}
