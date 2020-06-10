package com.wuda.foundation.commons;

import com.wuda.foundation.lang.PhoneState;

/**
 * phone manager.
 *
 * @author wuda
 */
public interface PhoneManager {

    /**
     * 添加手机.
     *
     * @param phoneNumber    phone number
     * @param phoneState phone state
     * @param phoneType  phone type
     * @param opUserId 操作人用户ID,是谁正在添加这个新用户
     * @return phone id
     */
    long addPhone(String phoneNumber, PhoneState phoneState, PhoneType phoneType, Long opUserId);
}
