package com.wuda.foundation.core.user;

/**
 * 代表一个用户的唯一标识符,比如username,登录使用的email,有些应用也可以用手机登录,即
 * 手机唯一代表一个用户.和其他框架中对于Principal的定义一样.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface UserPrincipal {

    /**
     * 获取该principal的类型.
     *
     * @return 类型
     */
    UserPrincipalType getType();

    /**
     * 用户的唯一标记,比如username,email address.
     *
     * @return identifier
     */
    String getIdentifier();
}
