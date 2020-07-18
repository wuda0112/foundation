package com.wuda.foundation.lang;

/**
 * 用于描述Code,这些Code在同一个{@link UniqueCodeDescriptorSchema}下必须唯一.
 * 每个子类如果想被查找到,必须调用{@link #register()}方法把自己注册到{@link UniqueCodeDescriptorRegistry}
 * 中,这样就可以使用{@link UniqueCodeDescriptorRegistry#lookup(Class, Class, Object)}方法
 * 查找了.
 *
 * @param <T> code的数据类型
 * @author wuda
 * @since 1.0.0
 */
public interface UniqueCodeDescriptor<T> {

    /**
     * 获取该code所在的schema,不需要{@link UniqueCodeDescriptorSchema}的实例,
     * 只需要schema的{@link Class}即可,表示是这一种的{@link UniqueCodeDescriptor}.
     *
     * @return class of schema
     */
    Class<? extends UniqueCodeDescriptorSchema> getSchemaClass();

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

    /**
     * 注册到{@link UniqueCodeDescriptorRegistry}.
     */
    default void register() {
        UniqueCodeDescriptorRegistry.defaultRegistry.register(this);
    }
}
