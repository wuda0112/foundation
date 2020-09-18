package com.wuda.foundation.lang.identify;

/**
 * 默认实现类.
 *
 * @author wuda
 * @since 1.0.3
 */
public class IdentifierTypeImpl extends AbstractIdentifierType {
    /**
     * 构造实例,并且将自己注册到{@link IdentifierTypeRegistry}中.
     *
     * @param code        the unique code
     * @param description description
     */
    public IdentifierTypeImpl(int code, String description) {
        super(code, description);
    }
}
