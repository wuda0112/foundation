package com.wuda.foundation.user;

import com.wuda.foundation.lang.UniqueCodeDescriptor;
import com.wuda.foundation.lang.UniqueCodeDescriptorSchema;

/**
 * user email state.每种用途的email的状态可能不同，
 * 比如用于登录的email，状态可能是禁止登录状态,其他用途的email就不会有这个状态.
 * 推荐使用枚举的方式实现.
 *
 * @author wuda
 */
public interface UserEmailState extends UniqueCodeDescriptor<Integer> {

    default Class<UserEmailStateSchema> getSchemaClass(){
        return UserEmailStateSchema.class;
    }

}
