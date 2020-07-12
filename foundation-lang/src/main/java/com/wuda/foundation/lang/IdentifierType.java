package com.wuda.foundation.lang;

/**
 * {@link Identifier}的类型.推荐用枚举实现该类.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface IdentifierType {

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
