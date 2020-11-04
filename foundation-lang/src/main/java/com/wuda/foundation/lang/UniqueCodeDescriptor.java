package com.wuda.foundation.lang;

/**
 * 用于描述Code.
 *
 * @param <T> code的数据类型
 * @author wuda
 * @since 1.0.0
 */
public interface UniqueCodeDescriptor<T> {

    /**
     * 获取code值.
     *
     * @return code值
     */
    T getCode();

    /**
     * 获取该code的描述信息.
     *
     * @return 描述信息
     */
    String getDescription();
}
