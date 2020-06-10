package com.wuda.foundation.commons;

/**
 * phone type,比如是手机还是固话,现在还有电子手表.
 * 推荐使用枚举的方式实现.
 *
 * @author wuda
 */
public interface PhoneType {

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
