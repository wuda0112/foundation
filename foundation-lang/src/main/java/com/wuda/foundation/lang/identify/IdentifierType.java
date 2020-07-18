package com.wuda.foundation.lang.identify;

/**
 * {@link Identifier}的类型.推荐用枚举实现该类.
 * 系统已经内置了很多类型,在添加自定义的类型之前,可以先到
 * {@link BuiltinIdentifierType}中查看是否已经存在.
 *
 * @author wuda
 * @see BuiltinIdentifierType
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

    /**
     * 注册到{@link IdentifierTypeRegistry#defaultRegistry}.
     */
    default void register() {
        IdentifierTypeRegistry.defaultRegistry.register(this);
    }
}
