package com.wuda.foundation.commons;

import com.wuda.foundation.lang.InsertMode;

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
     * @param insertMode  insert mode
     * @param opUserId    操作人用户ID
     * @return 记录的ID, 虽然设置了ID, 但是如果数据库中已经存在相同的email,
     * 并且insert mode 是先检查,则返回的是已有记录的ID;如果数据库中不存在,则返回新增记录的ID
     */
    Long createEmail(CreateEmail createEmail, InsertMode insertMode, Long opUserId);

    /**
     * 批量添加email.
     *
     * @param emails   email
     * @param opUserId 操作人用户ID
     */
    void directBatchInsertEmail(List<CreateEmail> emails, Long opUserId);
}
