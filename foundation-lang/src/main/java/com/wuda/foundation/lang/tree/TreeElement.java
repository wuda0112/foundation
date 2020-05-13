package com.wuda.foundation.lang.tree;

/**
 * {@link Tree}的元素.
 *
 * @param <T> 该元素的唯一标记的数据类型
 */
public interface TreeElement<T extends Comparable<T>> {
    /**
     * 获取这个element的唯一标记.每个element都有一个唯一标记,
     * 就好像数据库中的每条记录都有一个主键一样.
     *
     * @return 该element的唯一标记
     */
    T getIdentifier();
}
