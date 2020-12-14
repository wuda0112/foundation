package com.wuda.foundation.lang.tree;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 树形结构.树中的每一个节点{@link TreeNode}都必须拥有一个唯一标记,这个才能在树中找到对应的节点.
 *
 * @param <T> 树中节点的唯一标记符的类型
 * @param <E> the type of nodes held in this tree
 * @author wuda
 * @since 1.0.0
 */
public interface Tree<T extends Comparable<T>, E extends TreeNode<T>> {

    /**
     * Returns the root node in this tree.
     *
     * @return the root node in this tree
     * @throws NoSuchElementException if this tree is empty
     */
    E getRoot();

    /**
     * 获取直接父节点.
     *
     * @param id node id
     * @return 父节点, 如果不存在则返回<code>null</code>
     */
    E getParent(T id);

    /**
     * 获取给定ID对应的节点.
     *
     * @param id id
     * @return node
     */
    E get(T id);

    /**
     * 获取给定节点的深度.
     *
     * @param id 节点id
     * @return 在树中深度
     */
    int getDepth(T id);

    /**
     * 获取给定id所代表的节点的所有子节点,只获取孩子节点,不包含孙子节点及以下.
     *
     * @param id 节点id
     * @return 这个节点id下的所有子节点, 如果没有, 则返回<code>null</code>
     */
    Set<E> getDirectChildren(T id);

    /**
     * 为两个节点建立父子关系.
     *
     * @param parent 作为父节点
     * @param child  作为子节点
     */
    void createRelationship(E parent, E child);

    /**
     * 获取祖先.
     *
     * @param id    node id
     * @param count 查找祖先的个数
     * @return {@link LinkedList}下标0处的节点是直接父亲, 下标1处的节点是父亲的父亲, 依次类推.
     */
    LinkedList<E> getAncestor(T id, int count);

    /**
     * 获取给定节点的后裔.
     *
     * @param id node id
     * @return 所有后裔
     */
    LinkedList<E> getDescendant(T id);


    /**
     * 获取给定节点的{@link Treeable}.
     *
     * @param nodeId 节点ID
     * @return {@link Treeable}.
     */
    Treeable<T, E> getTreeable(T nodeId);

    /**
     * 检查第二个是否第一个的"后裔".
     *
     * @param first  第一个节点的ID
     * @param second 第二个节点的ID
     * @return <code>true</code>-如果是
     */
    boolean isDescendant(T first, T second);
}
