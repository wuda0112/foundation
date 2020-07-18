package com.wuda.foundation.lang.identify;

/**
 * 唯一标记符的数据类型是字符串.
 *
 * @author wuda
 * @since 1.0.0
 */
public class StringIdentifier implements Identifier<String> {

    private String value;
    private IdentifierType type;

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public StringIdentifier(String value, IdentifierType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public IdentifierType getType() {
        return type;
    }
}
