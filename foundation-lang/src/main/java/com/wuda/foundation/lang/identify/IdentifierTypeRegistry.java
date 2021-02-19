package com.wuda.foundation.lang.identify;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link IdentifierType}的注册中心.
 *
 * @author wuda
 * @since 1.0.0
 */
public class IdentifierTypeRegistry {

    private Map<Integer, IdentifierType> byCodeMap = new ConcurrentHashMap<>();

    public final static IdentifierTypeRegistry defaultRegistry = new IdentifierTypeRegistry();

    /**
     * 使用{@link #defaultRegistry}作为全局注册中心.
     */
    private IdentifierTypeRegistry() {

    }

    /**
     * 注册{@link IdentifierType}.
     *
     * @param identifierType {@link IdentifierType}
     */
    public void register(IdentifierType identifierType) {
        IdentifierType exists = byCodeMap.get(identifierType.getCode());
        if (exists != null && !exists.getClass().equals(identifierType.getClass())) {
            throw new IllegalStateException("identifier type code= " + identifierType.getCode() + ",Duplicate !");
        }
        byCodeMap.put(identifierType.getCode(), identifierType);
    }

    /**
     * 查询指定的code值对应的{@link IdentifierType},前提是
     * {@link IdentifierType}必须首先调用{@link IdentifierTypeRegistry#register(IdentifierType)}
     * 方法将自己注册进来.如果没有找到会抛出{@link IllegalStateException}异常.
     * 在系统启动时,所有的{@link IdentifierType}实例必须先注册到{@link IdentifierTypeRegistry}.
     *
     * @param typeCode identifier type code
     * @return 该code对应的identifier type
     * @throws IllegalStateException 如果没有找到指定的code. 抛出异常而不是返回<code>null</code>的原因是显性的指出问题,方便问题查找.
     */
    public IdentifierType lookup(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        IdentifierType identifierType = byCodeMap.get(typeCode);
        if (identifierType == null) {
            throw new IllegalStateException("identifier type code = " + typeCode + ",没有找到,可能是没有注册");
        }
        return identifierType;
    }
}
