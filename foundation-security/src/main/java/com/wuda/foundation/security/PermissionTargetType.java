package com.wuda.foundation.security;

/**
 * permission target的类型.实现类推荐使用枚举实现该接口.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionTargetType {

    /**
     * 获取类型的值.
     *
     * @return type value
     */
    String getValue();
}
