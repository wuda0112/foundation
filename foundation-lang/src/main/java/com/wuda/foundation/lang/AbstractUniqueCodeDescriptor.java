package com.wuda.foundation.lang;

/**
 * 抽象实现类,主要完成了自动注册.
 *
 * @param <T> code的数据类型
 * @author wuda
 */
public abstract class AbstractUniqueCodeDescriptor<T> implements UniqueCodeDescriptor<T> {

    protected T code;
    protected String description;

    /**
     * 构造实例,并且将自己注册到{@link UniqueCodeDescriptorRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    protected AbstractUniqueCodeDescriptor(T code, String description) {
        this.code = code;
        this.description = description;
        UniqueCodeDescriptorRegistry.defaultRegistry.register(this);
    }

    @Override
    public T getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
