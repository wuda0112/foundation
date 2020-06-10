package com.wuda.foundation.user;

import com.wuda.foundation.lang.BuiltinIdentifierType;
import com.wuda.foundation.lang.Identifier;
import com.wuda.foundation.lang.IdentifierType;

import java.util.Objects;

/**
 * 使用username作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class UsernameIdentifier implements Identifier<String> {

    /**
     * 用户名.
     */
    private String username;

    /**
     * 使用给定的用户名构造.
     *
     * @param username 用户名
     */
    public UsernameIdentifier(String username) {
        Objects.requireNonNull(username);
        this.username = username;
    }

    @Override
    public IdentifierType getType() {
        return BuiltinIdentifierType.USERNAME;
    }

    @Override
    public String getValue() {
        return username;
    }
}
