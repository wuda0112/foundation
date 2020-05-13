package com.wuda.foundation.user;

import java.util.Objects;

/**
 * 使用username作为用户的唯一标记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class UsernamePrincipal implements UserPrincipal {

    /**
     * 用户名.
     */
    private String username;

    /**
     * 使用给定的用户名构造.
     *
     * @param username 用户名
     */
    public UsernamePrincipal(String username) {
        Objects.requireNonNull(username);
        this.username = username;
    }

    @Override
    public UserPrincipalType getType() {
        return BuiltinUserPrincipalType.USERNAME;
    }

    @Override
    public String getIdentifier() {
        return username;
    }
}
