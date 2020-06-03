package com.wuda.foundation.lang;

/**
 * {@link ThreadLocal}subclass that exposes a specified name.
 *
 * @param <T> the {@link ThreadLocal}'s value type
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    /**
     * {@link ThreadLocal}'s name.
     */
    private String name;

    /**
     * Create a new {@link NamedThreadLocal} with the given name.
     *
     * @param name 名称
     */
    public NamedThreadLocal(String name) {
        this.name = name;
    }

    /**
     * 获取当前{@link ThreadLocal}的名称.
     *
     * @return name
     */
    public String getName() {
        return name;
    }
}
