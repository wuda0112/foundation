package com.wuda.foundation.lang.tree;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 树形结构.树中的每一个元素{@link TreeElement}都必须拥有一个唯一标记,这个才能在树中找到对应的元素.
 *
 * @param <T> 树中元素的唯一标记符的类型
 * @param <E> the type of elements held in this tree
 * @author wuda
 * @since 1.0.0
 */
public interface Tree<T extends Comparable<T>, E extends TreeElement<T>> {

    /**
     * Returns the root element in this tree.
     *
     * @return the root element in this tree
     * @throws NoSuchElementException if this tree is empty
     */
    E getRoot();

    /**
     * 获取直接父元素.
     *
     * @param id element id
     * @return 父元素, 如果不存在则返回<code>null</code>
     */
    E getParent(T id);

    /**
     * 获取给定ID对应的元素.
     *
     * @param id id
     * @return element
     */
    E get(T id);

    /**
     * 获取给定元素的深度.
     *
     * @param id 元素id
     * @return 在树中深度
     */
    int getDepth(T id);

    /**
     * 获取给定id所代表的元素的所有子元素,只获取孩子元素,不包含孙子元素及以下.
     *
     * @param id 节点id
     * @return 这个节点id下的所有子元素, 如果没有, 则返回<code>null</code>
     */
    Set<E> getDirectChildren(T id);

    /**
     * 为两个元素建立父子关系.
     *
     * @param parent 作为父元素
     * @param child  作为子元素
     */
    void createRelationship(E parent, E child);

    /**
     * 获取祖先.
     *
     * @param id    element id
     * @param count 查找祖先的个数
     * @return {@link LinkedList}下标0处的元素是直接父亲, 下标1处的元素是父亲的父亲, 依次类推.
     */
    LinkedList<E> getAncestor(T id, int count);
}
