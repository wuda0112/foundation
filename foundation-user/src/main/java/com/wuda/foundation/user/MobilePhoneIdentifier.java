package com.wuda.foundation.user;

import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.lang.IdentifierType;

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

    /**
     * 构造实例
     *
     * @param mobilePhone 手机号码.
     */
    public MobilePhoneIdentifier(String mobilePhone) {
        Objects.requireNonNull(mobilePhone);
        this.mobilePhone = mobilePhone;
    }

    @Override
    public IdentifierType getType() {
        return BuiltinUserIdentifierType.MOBILE_PHONE;
    }

    @Override
    public String getValue() {
        return mobilePhone;
    }
}
