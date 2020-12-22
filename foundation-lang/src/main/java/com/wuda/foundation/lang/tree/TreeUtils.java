package com.wuda.foundation.lang.tree;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 工具类.
 *
 * @author wuda
 * @since 1.0.3
 */
public class TreeUtils {

    /**
     * 打印,从给定的ID所代表的节点开始,打印该节点及以下的所有节点.
     *
     * @param tree tree
     * @param id   node id
     */
    public static <T extends Comparable<T>> void print(Tree<T, ? extends TreeNode<T>> tree, T id) {
        int depth = tree.getDepth(id);
        StringBuilder tab = new StringBuilder("\t");
        for (int i = 0; i < depth; i++) {
            tab.append("\t");
        }
        TreeNode node = tree.get(id);
        System.out.println(tab + node.getName());
        Set<? extends TreeNode<T>> children = tree.getDirectChildren(id);
        if (children == null || children.size() == 0) {
            return;
        }
        Set<T> childrenIdSet = new HashSet<>(children.size());
        for (TreeNode<T> child : children) {
            childrenIdSet.add(child.getId());
        }
        for (T childId : childrenIdSet) {
            print(tree, childId);
        }
    }

    /**
     * 从给定的节点开始,深度优先遍历.
     *
     * @param tree                 tree
     * @param startNodeIdInclusive 起点的节点ID,inclusive
     * @param <T>                  节点的ID的类型
     * @param <N>                  实际的节点的类型
     */
    public static <T extends Comparable<T>, N extends TreeNode<T>> void depthFirstTraverse(Tree<T, N> tree, T startNodeIdInclusive, Consumer<N> consumer) {
        N node = tree.get(startNodeIdInclusive);
        if (node == null) {
            return;
        }
        consumer.accept(node);
        Set<N> children = tree.getDirectChildren(startNodeIdInclusive);
        if (children == null || children.size() == 0) {
            return;
        }
        for (N child : children) {
            depthFirstTraverse(tree, child.getId(), consumer);
        }
    }
}
