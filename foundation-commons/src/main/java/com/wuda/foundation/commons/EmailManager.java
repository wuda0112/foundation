package com.wuda.foundation.commons;

import com.wuda.foundation.lang.EmailState;

/**
 * email manager.
 *
 * @author wuda
 */
public interface EmailManager {

    /**
     * 添加email.
     *
     * @param emailAddress email address
     * @param opUserId     操作人用户ID,是谁正在添加这个新用户
     * @param emailState   状态
     * @return email id
     */
    long addEmail(String emailAddress, EmailState emailState, Long opUserId);
}
