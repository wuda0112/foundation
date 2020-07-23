package com.wuda.foundation.commons;

import com.wuda.foundation.lang.InsertMode;

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
     * @param insertMode  insert mode
     * @param opUserId    操作人用户ID
     * @return 记录的ID, 虽然设置了ID, 但是如果数据库中已经存在相同的phone,
     * 并且insert mode 是先检查,则返回的是已有记录的ID;如果数据库中不存在,则返回新增记录的ID
     */
    Long createPhone(CreatePhone createPhone, InsertMode insertMode, Long opUserId);

    /**
     * 批量添加phone.
     *
     * @param phones   phone
     * @param opUserId 操作人用户ID
     */
    void createPhone(List<CreatePhone> phones, Long opUserId);
}
