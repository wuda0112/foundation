package com.wuda.foundation.commons;

import com.wuda.foundation.lang.PhoneState;

public abstract class AbstractPhoneManager implements PhoneManager {

    @Override
    public long addPhone(String phoneNumber, PhoneState phoneState, PhoneType phoneType, Long opUserId) {
        return addPhoneDbOp(phoneNumber, phoneState, phoneType, opUserId);
    }

    /**
     * 添加手机.
     *
     * @param phoneNumber phone number
     * @param phoneState  phone state
     * @param phoneType   phone type
     * @param opUserId    操作人用户ID,是谁正在添加这个新用户
     * @return phone id
     */
    protected abstract long addPhoneDbOp(String phoneNumber, PhoneState phoneState, PhoneType phoneType, Long opUserId);
}
