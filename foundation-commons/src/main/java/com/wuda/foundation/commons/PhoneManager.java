package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;

import java.util.List;

/**
 * phone manager.
 *
 * @author wuda
 */
public interface PhoneManager {

    /**
     * 添加phone.
     *
     * @param createPhone phone
     * @param opUserId    操作人用户ID
     * @return 新增的记录的ID
     * @throws AlreadyExistsException 如果phone已经存在
     */
    Long createPhone(CreatePhone createPhone, Long opUserId) throws AlreadyExistsException;

    /**
     * 批量添加phone.
     *
     * @param phones   phone
     * @param opUserId 操作人用户ID
     */
    void directBatchInsertPhone(List<CreatePhone> phones, Long opUserId);
}
