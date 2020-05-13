package com.wuda.foundation.user;

import java.util.Objects;

/**
 * 使用邮箱作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class EmailPrincipal implements UserPrincipal {

    /**
     * 邮箱.
     */
    private String emailAddress;

    /**
     * 构造实例
     *
     * @param emailAddress 邮箱.
     */
    public EmailPrincipal(String emailAddress) {
        Objects.requireNonNull(emailAddress);
        this.emailAddress = emailAddress;
    }

    @Override
    public UserPrincipalType getType() {
        return BuiltinUserPrincipalType.EMAIL;
    }

    @Override
    public String getIdentifier() {
        return emailAddress;
    }
}
