package com.wuda.foundation.lang.tree;

/**
 * {@link Tree}的节点.
 *
 * @author wuda
 *
 * @param <T> 该节点的唯一标记的数据类型
 */
public interface TreeNode<T extends Comparable<T>> extends IdPidEntry<T> {

    /**
     * 获取该节点的名称.
     *
     * @return 名称
     */
    String getName();
}
