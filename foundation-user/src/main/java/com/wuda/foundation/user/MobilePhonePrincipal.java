package com.wuda.foundation.user;

import java.util.Objects;

/**
 * 使用手机号码作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class MobilePhonePrincipal implements UserPrincipal {

    /**
     * 手机号码.
     */
    private String mobilePhone;

    /**
     * 构造实例
     *
     * @param mobilePhone 手机号码.
     */
    public MobilePhonePrincipal(String mobilePhone) {
        Objects.requireNonNull(mobilePhone);
        this.mobilePhone = mobilePhone;
    }

    @Override
    public UserPrincipalType getType() {
        return BuiltinUserPrincipalType.MOBILE_PHONE;
    }

    @Override
    public String getIdentifier() {
        return mobilePhone;
    }
}
