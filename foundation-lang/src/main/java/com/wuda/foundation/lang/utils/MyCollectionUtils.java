package com.wuda.foundation.lang.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * collection utils.
 *
 * @author wuda
 * @since 1.0.0
 */
public class MyCollectionUtils {

    /**
     * 并集.
     *
     * @param a   one set
     * @param b   another set
     * @param <T> 集合中的元素的类型
     * @return 取交集后新的set
     */
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        if (a == null || a.isEmpty()) {
            return b;
        } else if (b == null || b.isEmpty()) {
            return a;
        }
        Set<T> set = new HashSet<>(a);
        set.addAll(b);
        return set;
    }
}
