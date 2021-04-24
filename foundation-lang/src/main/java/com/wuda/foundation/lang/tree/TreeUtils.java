package com.wuda.foundation.lang.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

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
     * @param tree                 tree
     * @param startNodeIdInclusive 打印当前节点以及以下的所有节点
     */
    public static <T extends Comparable<T>> void print(Tree<T, ? extends TreeNode<T>> tree, T startNodeIdInclusive) {
        int depth = tree.getDepth(startNodeIdInclusive);
        StringBuilder tab = new StringBuilder("\t");
        for (int i = 0; i < depth; i++) {
            tab.append("\t");
        }
        TreeNode node = tree.get(startNodeIdInclusive);
        System.out.println(tab + node.getName());
        List<? extends TreeNode<T>> children = tree.getDirectChildren(startNodeIdInclusive);
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
     * @param tree               tree
     * @param startNodeInclusive 起始的节点,inclusive
     * @param <T>                节点的ID的类型
     * @param <N>                实际的节点的类型
     */
    public static <T extends Comparable<T>, N extends TreeNode<T>> void depthFirstTraverse(Tree<T, N> tree, TreeNode<T> startNodeInclusive, Consumer<N> consumer) {
        depthFirstTraverse(tree, startNodeInclusive.getId(), consumer);
    }

    /**
     * 从给定的节点开始,深度优先遍历.
     *
     * @param tree                 tree
     * @param startNodeIdInclusive 起始的节点ID,inclusive
     * @param <T>                  节点的ID的类型
     * @param <N>                  实际的节点的类型
     */
    public static <T extends Comparable<T>, N extends TreeNode<T>> void depthFirstTraverse(Tree<T, N> tree, T startNodeIdInclusive, Consumer<N> consumer) {
        N node = tree.get(startNodeIdInclusive);
        if (node == null) {
            return;
        }
        consumer.accept(node);
        List<N> children = tree.getDirectChildren(startNodeIdInclusive);
        if (children == null || children.size() == 0) {
            return;
        }
        for (N child : children) {
            depthFirstTraverse(tree, child.getId(), consumer);
        }
    }

    /**
     * 复制Tree.
     *
     * @param from   被复制的Tree
     * @param mapper 原始的树的节点和新的树的节点的映射器
     * @param <T>    树的节点的唯一标记的数据类型
     * @param <A>    原始的树的节点类型
     * @param <B>    新的树的节点类型
     * @return 新的Tree
     */
    public static <T extends Comparable<T>, A extends TreeNode<T>, B extends TreeNode<T>> Tree<T, B> copy(Tree<T, A> from, Function<A, B> mapper) {
        if (from == null) {
            return null;
        }
        A fromRoot = from.getRoot();
        B toRoot = mapper.apply(fromRoot);
        List<B> toNodes = new ArrayList<>();
        TreeUtils.depthFirstTraverse(from, fromRoot, fromNode -> {
            if (!CompareUtils.eq(fromNode.getId(), fromRoot.getId())) {
                B toNode = mapper.apply(fromNode);
                toNodes.add(toNode);
            }
        });
        IdPidEntryTreeBuilder<T, B> treeBuilder = new IdPidEntryTreeBuilder<>();
        Tree<T, B> toTree = new MappedTree<>(toRoot);
        treeBuilder.add(toTree, toNodes);
        return toTree;
    }
}
