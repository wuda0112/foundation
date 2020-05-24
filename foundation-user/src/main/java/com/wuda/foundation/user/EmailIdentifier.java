package com.wuda.foundation.user;

import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.lang.IdentifierType;

import java.util.Objects;

/**
 * 使用邮箱作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class EmailIdentifier implements Identifier<String> {

    /**
     * 邮箱.
     */
    private String emailAddress;

    /**
     * 构造实例
     *
     * @param emailAddress 邮箱.
     */
    public EmailIdentifier(String emailAddress) {
        Objects.requireNonNull(emailAddress);
        this.emailAddress = emailAddress;
    }

    @Override
    public IdentifierType getType() {
        return BuiltinUserIdentifierType.EMAIL;
    }

    @Override
    public String getValue() {
        return emailAddress;
    }
}
