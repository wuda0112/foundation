package com.wuda.foundation.lang.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 使用{@link java.util.Map}实现树形结构.
 *
 * @param <T> 查看{@link Tree}中的解释
 * @param <E> 查看{@link Tree}中的解释
 * @author wuda
 * @since 1.0.0
 */
public class MappedTree<T extends Comparable<T>, E extends TreeElement<T>> implements Tree<T, E> {


    /**
     * 根元素.
     */
    private E root;
    /**
     * key - id ,value - parent id.
     */
    private HashMap<T, T> id2PidMap;
    /**
     * key - parent id , value - 所有的子元素ID的集合.
     */
    private HashMap<T, Set<T>> pid2ChildrenMap;
    /**
     * key - id , value - 此id对应的元素.
     */
    private HashMap<T, E> id2ElementMap;
    /**
     * key - id , value - 此id对应的元素的深度.
     */
    private HashMap<T, Integer> id2DepthMap;

    private int no_depth = -1;

    /**
     * 构造树.
     *
     * @param root 根元素
     */
    public MappedTree(E root) {
        validateElement(root);
        this.root = root;
        init();
        addElement(root);
        setDepth(root);
    }

    /**
     * init.
     */
    private void init() {
        id2PidMap = new HashMap<>();
        pid2ChildrenMap = new HashMap<>();
        id2ElementMap = new HashMap<>();
        id2DepthMap = new HashMap<>();
    }

    @Override
    public void createRelationship(E parent, E child) {
        validateElement(parent);
        validateElement(child);
        validateRelationship(parent, child);
        boolean hasRelationship = alreadyHasRelationship(parent, child);
        if (hasRelationship) {
            return;
        }
        T childId = child.getIdentifier();
        T parentId = parent.getIdentifier();

        id2PidMap.put(childId, parentId);
        Set<T> children = pid2ChildrenMap.computeIfAbsent(parentId, k -> new HashSet<>());
        children.add(childId);

        setDepth(child);

        addElement(parent);
        addElement(child);
    }

    /**
     * 设置元素在树中的深度.元素的深度等于它父元素深度加一.如果当前元素的深度发生变化,
     * 那么它的所有子元素的深度也会相应的更新.
     *
     * @param element element
     */
    private void setDepth(E element) {
        int depth = 0;
        T id = element.getIdentifier();
        if (!CompareUtils.eq(id, root.getIdentifier())) {
            E parent = getParent(id);
            if (parent != null) {
                T pid = parent.getIdentifier();
                int parentDepth = getDepth(pid);
                depth = parentDepth + 1;
            }
        }
        if (depth != getDepth(id)) {
            // 深度发生变化才更新
            id2DepthMap.put(id, depth);
            // 更新当前元素下的所有子元素的深度
            Set<T> children = getDirectChildrenIdSet(id);
            if (children != null && children.size() > 0) {
                for (T child : children) setDepth(get(child));
            }
        }
    }

    /**
     * 获取根元素.
     *
     * @return root
     */
    @Override
    public E getRoot() {
        return root;
    }

    /**
     * 获取给定id所代表的元素的所有子元素id,只是获取孩子元素,不包含孙子元素及以下.
     *
     * @param id 元素id
     * @return 这个元素id下的所有子元素, 如果没有, 则返回<code>null</code>
     */
    private Set<T> getDirectChildrenIdSet(T id) {
        return pid2ChildrenMap.get(id);
    }

    @Override
    public Set<E> getDirectChildren(T id) {
        Set<T> childIdSet = pid2ChildrenMap.get(id);
        if (childIdSet != null && !childIdSet.isEmpty()) {
            Set<E> childSet = new HashSet<>(childIdSet.size());
            for (T childId : childIdSet) {
                E child = get(childId);
                childSet.add(child);
            }
            return childSet;
        }
        return null;
    }

    @Override
    public int getDepth(T id) {
        return id2DepthMap.getOrDefault(id, no_depth);
    }

    /**
     * 获取给定ID对应的元素.
     *
     * @param id id
     * @return element
     */
    public E get(T id) {
        return id2ElementMap.get(id);
    }

    @Override
    public LinkedList<E> getAncestor(T id, int count) {
        LinkedList<E> ancestors = new LinkedList<>();
        int index = 0;
        while (index < count) {
            E parent = getParent(id);
            if (parent == null) {
                break;
            }
            T pid = parent.getIdentifier();
            E element = get(pid);
            ancestors.add(element);
            id = pid; // 向上推进
            index++;
        }
        return ancestors;
    }

    @Override
    public E getParent(T id) {
        T parentId = id2PidMap.get(id);
        if (parentId == null) {
            return null;
        }
        return get(parentId);
    }

    /**
     * 校验元素.
     *
     * @param element element
     */
    private void validateElement(E element) {
        if (element.getIdentifier() == null) {
            throw new IllegalStateException("元素的ID不能是:" + null);
        }
    }

    /**
     * 验证两个元素的关系.
     *
     * @param parent parent
     * @param child  child
     */
    private void validateRelationship(E parent, E child) {
        T childId = child.getIdentifier();
        if (CompareUtils.eq(childId, root.getIdentifier())) {
            throw new IllegalStateException("root不能有父元素");
        }
        T parentId = parent.getIdentifier();
        if (alreadyHasParent(child)) {
            T oldParentId = id2PidMap.get(childId);
            if (!CompareUtils.eq(oldParentId, parentId)) {
                throw new IllegalStateException("子元素[ ID = " + childId + " ],已经拥有父元素( ID=" + oldParentId + " )," +
                        "因此,不能将[ ID=" + parentId + " ]的元素设置成它的父元素." +
                        "子元素只能有一个父元素");
            }
        }
        if (alreadyHasRelationship(child, parent)) {
            throw new IllegalStateException("想建立[ parent:" + parentId + " -> child:" + childId + " ]的父子关系," +
                    "但是在树中已经存在[ parent:" + childId + " -> child:" + parentId + " ]的关系." +
                    "父子关系不能互换");
        }
    }

    /**
     * 判断该元素在树中是否已经拥有父元素.
     *
     * @param child child
     * @return <code>true</code>-如果已经拥有
     */
    private boolean alreadyHasParent(E child) {
        T childId = child.getIdentifier();
        T parentId = id2PidMap.get(childId);
        return parentId != null;
    }

    /**
     * 这两个元素,在树中是否已经拥有父子关系.
     *
     * @param parent parent
     * @param child  child
     * @return true-已经拥有正确的父子关系
     */
    private boolean alreadyHasRelationship(E parent, E child) {
        T oldParentId = id2PidMap.get(child.getIdentifier());
        return CompareUtils.eq(oldParentId, parent.getIdentifier());
    }

    private void addElement(E element) {
        validateElement(element);
        id2ElementMap.put(element.getIdentifier(), element);
    }
}
