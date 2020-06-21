package com.wuda.foundation.item;

/**
 * item的状态.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface ItemState {

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
