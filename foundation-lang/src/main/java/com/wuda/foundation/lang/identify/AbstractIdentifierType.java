package com.wuda.foundation.lang.identify;

/**
 * 抽象实现类,主要是完成了自动注册到{@link IdentifierTypeRegistry}的操作.
 *
 * @author wuda
 * @version 1.0.3
 */
public abstract class AbstractIdentifierType implements IdentifierType {

    protected int code;
    protected String description;

    /**
     * 构造实例,并且将自己注册到{@link IdentifierTypeRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    protected AbstractIdentifierType(int code, String description) {
        this.code = code;
        this.description = description;
        IdentifierTypeRegistry.defaultRegistry.register(this);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
