package com.wuda.foundation.core.user;

/**
 * 用户账户校验器.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface UserAccountValidator {

    /**
     * 测试是否有效的密码.
     *
     * @param password password
     * @return <code>true</code>-如果是有效的
     */
    boolean validPassword(String password);

    /**
     * 测试是否有效的principal.
     *
     * @param principal principal
     * @return <code>true</code>-如果是有效的
     */
    boolean validPrincipal(UserPrincipal principal);
}
