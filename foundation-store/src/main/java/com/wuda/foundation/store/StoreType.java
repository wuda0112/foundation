package com.wuda.foundation.store;

/**
 * 店铺的类型.推荐使用枚举实现.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface StoreType {

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
