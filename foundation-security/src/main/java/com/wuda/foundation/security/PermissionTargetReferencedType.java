package com.wuda.foundation.security;

/**
 * permission target关联的外部对象的类型.实现类推荐使用枚举实现该接口.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionTargetReferencedType {

    /**
     * 获取类型的值.
     *
     * @return type value
     */
    String getValue();
}
