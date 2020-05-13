package com.wuda.foundation.lang.tree;

class CompareUtils {

    static <T extends Comparable<T>> boolean eq(T one, T another) {
        return one != null && another != null && one.compareTo(another) == 0;
    }
}
