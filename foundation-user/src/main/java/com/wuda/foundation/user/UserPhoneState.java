package com.wuda.foundation.user;

/**
 * user phone state.每种用途的phone的状态可能不同，
 * 比如用于登录的phone，状态可能是禁止登录状态,其他用途的phone就不会有这个状态.
 * 推荐使用枚举的方式实现.
 *
 * @author wuda
 */
public interface UserPhoneState {

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
