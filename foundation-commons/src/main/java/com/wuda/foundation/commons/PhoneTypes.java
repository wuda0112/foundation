package com.wuda.foundation.commons;

/**
 * 作为{@link PhoneType}的工具类
 *
 * @author wuda
 */
public class PhoneTypes {

    /**
     * 根据电话号码推断电话类型.
     *
     * @param phoneNumber 电话号码
     * @return 类型
     */
    public static PhoneType getByPhoneNumber(String phoneNumber) {
        return BuiltinPhoneType.ZERO;
    }
}
