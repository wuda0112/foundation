package com.wuda.foundation.commons;

import com.wuda.foundation.lang.identify.Identifier;

/**
 * {@link Identifier}的类型.推荐用枚举实现该类.
 * 系统已经内置了很多类型,在添加自定义的类型之前,可以先到
 * {@link BuiltinTreeNodeUse}中查看是否已经存在.
 *
 * @author wuda
 * @see BuiltinTreeNodeUse
 * @since 1.0.0
 */
public interface TreeNodeUse {

    /**
     * 获取code.
     *
     * @return code
     */
    Byte getCode();

    /**
     * 描述信息.
     *
     * @return 描述
     */
    String getDescription();
}
