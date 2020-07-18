package com.wuda.foundation.lang.identify;

/**
 * 唯一标记符的数据类型是{@link Integer}.
 *
 * @author wuda
 * @since 1.0.0
 */
public class IntegerIdentifier implements Identifier<Integer> {

    private Integer value;
    private IdentifierType type;

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public IntegerIdentifier(Integer value, IdentifierType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public IdentifierType getType() {
        return type;
    }
}
