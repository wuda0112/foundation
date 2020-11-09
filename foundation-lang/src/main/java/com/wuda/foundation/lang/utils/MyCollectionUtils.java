package com.wuda.foundation.lang.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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

    /**
     * 当集合中的元素具有唯一的key时,把元素的key和元素对应起来.
     *
     * @param collection   元素集合
     * @param keyExtractor 元素的key提取器
     * @param <T>          元素的类型
     * @param <R>          元素的key的类型
     * @return 元素的key与元素的映射
     */
    public static <T, R> Map<R, T> toMap(Collection<T> collection, Function<T, R> keyExtractor) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(1);
        }
        Map<R, T> map = new HashMap<>(collection.size());
        for (T t : collection) {
            R key = keyExtractor.apply(t);
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("key并不是唯一的,key = " + key + " duplicate");
            }
            map.put(key, t);
        }
        return map;
    }
}
