package com.wuda.foundation.store;

/**
 * 店铺的状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface StoreState {

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
