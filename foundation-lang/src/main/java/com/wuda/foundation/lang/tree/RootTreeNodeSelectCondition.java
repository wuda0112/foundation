package com.wuda.foundation.lang.tree;

/**
 * 选择根节点的条件.
 *
 * @param <T> 节点的ID的类型
 * @param <N> 节点的类型
 * @author wuda
 * @since 1.0.3
 */
@FunctionalInterface
public interface RootTreeNodeSelectCondition<T extends Comparable<T>, N extends TreeNode<T>> {

    /**
     * 判断给定的节点是否根节点.
     *
     * @param treeNode tree node
     * @return <code>true</code>-如果是
     */
    boolean isRoot(N treeNode);
}
