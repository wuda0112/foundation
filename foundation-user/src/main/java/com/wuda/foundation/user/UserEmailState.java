package com.wuda.foundation.user;

/**
 * user email state.每种用途的email的状态可能不同，
 * 比如用于登录的email，状态可能是禁止登录状态.
 * 推荐使用枚举的方式实现.
 *
 * @author wuda
 */
public interface UserEmailState {

    /**
     * 获取code.
     *
     * @return code
     */
    int getCode();

    /**
     * 描述信息.
     *
     * @return 描述
     */
    String getDescription();

}
