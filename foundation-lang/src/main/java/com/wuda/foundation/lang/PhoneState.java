package com.wuda.foundation.lang;

/**
 * phone state.推荐使用枚举的方式实现.
 *
 * @author wuda
 */
public interface PhoneState {

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
