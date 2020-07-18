package com.wuda.foundation.lang;

import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.Objects;

/**
 * 使用手机号码作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class MobilePhoneIdentifier implements Identifier<String> {

    /**
     * 手机号码.
     */
    private String mobilePhone;
    private PhoneState phoneState;

    /**
     * 构造实例
     *
     * @param mobilePhone 手机号码.
     * @param phoneState  phone state
     */
    public MobilePhoneIdentifier(String mobilePhone, PhoneState phoneState) {
        Objects.requireNonNull(mobilePhone);
        Objects.requireNonNull(phoneState);
        this.mobilePhone = mobilePhone;
        this.phoneState = phoneState;
    }

    @Override
    public IdentifierType getType() {
        return BuiltinIdentifierType.MOBILE_PHONE;
    }

    @Override
    public String getValue() {
        return mobilePhone;
    }

    /**
     * return phone state.
     *
     * @return phone state
     */
    public PhoneState getPhoneState() {
        return phoneState;
    }
}
