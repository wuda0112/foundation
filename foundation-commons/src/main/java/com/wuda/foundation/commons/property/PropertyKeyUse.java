package com.wuda.foundation.commons.property;

/**
 * 用途。比如用于属性模板；比如用于某个具体的商品的属性；
 * 比如用于系统的环境变量；比如用于任意的key/value pair.
 *
 * @author wuda
 */
public interface PropertyKeyUse {

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
