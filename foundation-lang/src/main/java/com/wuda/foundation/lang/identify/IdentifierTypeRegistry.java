package com.wuda.foundation.lang.identify;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link IdentifierType}的注册中心.
 *
 * @author wuda
 * @since 1.0.0
 */
public class IdentifierTypeRegistry {

    private List<IdentifierType> identifierTypes = new ArrayList<>();

    public static IdentifierTypeRegistry defaultRegistry = new IdentifierTypeRegistry();

    /**
     * 注册{@link IdentifierType}.
     *
     * @param identifierType {@link IdentifierType}
     */
    public void register(IdentifierType identifierType) {
        identifierTypes.add(identifierType);
    }

    /**
     * 查询指定的code值对应的{@link IdentifierType},前提是
     * {@link IdentifierType}必须首先调用{@link IdentifierType#register()}
     * 将自己注册进来.
     *
     * @param typeCode identifier type code
     * @return 该code对应的identifier type, <code>null</code>-如果没有找到
     */
    public <U extends IdentifierType> U lookup(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (IdentifierType identifierType : identifierTypes) {
            if (identifierType.getCode() == typeCode) {
                return (U) identifierType;
            }
        }
        return null;
    }
}
