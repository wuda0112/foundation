package com.wuda.foundation.lang.identify;

/**
 * 唯一标记符的数据类型是{@link Long}.
 *
 * @author wuda
 * @since 1.0.0
 */
public class LongIdentifier implements Identifier<Long> {

    private Long value;
    private IdentifierType type;

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public LongIdentifier(Long value, IdentifierType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public IdentifierType getType() {
        return type;
    }
}
