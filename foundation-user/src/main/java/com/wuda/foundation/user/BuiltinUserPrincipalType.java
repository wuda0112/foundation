package com.wuda.foundation.user;

public enum BuiltinUserPrincipalType implements UserPrincipalType {
    /**
     * 使用用户名作为用户唯一标记.
     */
    USERNAME,
    /**
     * 使用手机号码作为用户唯一标记.
     */
    MOBILE_PHONE,
    /**
     * 使用邮箱作为用户唯一标记.
     */
    EMAIL;
}
