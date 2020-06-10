package com.wuda.foundation.lang;

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
    private EmailState emailState;

    /**
     * 构造实例
     *
     * @param emailAddress 邮箱.
     * @param emailState   email state
     */
    public EmailIdentifier(String emailAddress, EmailState emailState) {
        Objects.requireNonNull(emailAddress);
        Objects.requireNonNull(emailState);
        this.emailAddress = emailAddress;
        this.emailState = emailState;
    }

    @Override
    public IdentifierType getType() {
        return BuiltinIdentifierType.EMAIL;
    }

    @Override
    public String getValue() {
        return emailAddress;
    }

    /**
     * return email state.
     *
     * @return email state
     */
    public EmailState getEmailState() {
        return emailState;
    }
}
