package com.wuda.foundation.lang.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 根节点选择器.
 *
 * @author wuda
 * @since 1.0.3
 */
public class RootTreeNodeSelector {

    /**
     * 选出root节点.给定的候选节点可能是多棵树的节点的混合,因此,返回的是集合,表示选择出了多棵树的根节点.
     *
     * @param candidateTreeNodes 待选节点
     * @param <N>                待选择的节点的类型
     * @param <T>                节点的ID的数据类型
     * @return <code>null</code>或者空集合-如果没有找到
     */
    public <T extends Comparable<T>, N extends TreeNode<T>> List<N> select(RootTreeNodeSelectCondition<T, N> condition, List<N> candidateTreeNodes) {
        if (candidateTreeNodes == null || candidateTreeNodes.isEmpty()) {
            return null;
        }
        List<N> roots = new ArrayList<>();
        for (N treeNode : candidateTreeNodes) {
            if (condition.isRoot(treeNode)) {
                roots.add(treeNode);
            }
        }
        return roots;
    }
}
