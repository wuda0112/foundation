package com.wuda.foundation.security;

import com.wuda.foundation.lang.UniqueCodeDescriptor;

/**
 * permission target的类型.实现类推荐使用枚举实现该接口.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionTargetType extends UniqueCodeDescriptor<Integer> {

    default Class<? extends PermissionTargetTypeSchema> getSchemaClass(){
        return PermissionTargetTypeSchema.class;
    }

}
