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
public class MappedTree<T extends Comparable<T>, E extends TreeNode<T>> implements Tree<T, E> {


    /**
     * 根节点.
     */
    private E root;
    /**
     * key - id ,value - parent id.
     */
    private HashMap<T, T> id2PidMap;
    /**
     * key - parent id , value - 所有的子节点ID的集合.
     */
    private HashMap<T, Set<T>> pid2ChildrenMap;
    /**
     * key - id , value - 此id对应的节点.
     */
    private HashMap<T, E> id2NodeMap;
    /**
     * key - id , value - 此id对应的节点的深度.
     */
    private HashMap<T, Integer> id2DepthMap;

    private int no_depth = -1;

    /**
     * 构造树.
     *
     * @param root 根节点
     */
    public MappedTree(E root) {
        validateNode(root);
        this.root = root;
        init();
        addNode(root);
        setDepth(root);
    }

    /**
     * init.
     */
    private void init() {
        id2PidMap = new HashMap<>();
        pid2ChildrenMap = new HashMap<>();
        id2NodeMap = new HashMap<>();
        id2DepthMap = new HashMap<>();
    }

    @Override
    public void createRelationship(E parent, E child) {
        validateNode(parent);
        validateNode(child);
        validateRelationship(parent, child);
        boolean hasRelationship = alreadyHasRelationship(parent, child);
        if (hasRelationship) {
            return;
        }
        T childId = child.getId();
        T parentId = parent.getId();

        id2PidMap.put(childId, parentId);
        Set<T> children = pid2ChildrenMap.computeIfAbsent(parentId, k -> new HashSet<>());
        children.add(childId);

        setDepth(child);

        addNode(parent);
        addNode(child);
    }

    /**
     * 设置节点在树中的深度.节点的深度等于它父节点深度加一.如果当前节点的深度发生变化,
     * 那么它的所有子节点的深度也会相应的更新.
     *
     * @param node node
     */
    private void setDepth(E node) {
        int depth = 0;
        T id = node.getId();
        if (!CompareUtils.eq(id, root.getId())) {
            E parent = getParent(id);
            if (parent != null) {
                T pid = parent.getId();
                int parentDepth = getDepth(pid);
                depth = parentDepth + 1;
            }
        }
        if (depth != getDepth(id)) {
            // 深度发生变化才更新
            id2DepthMap.put(id, depth);
            // 更新当前节点下的所有子节点的深度
            Set<T> children = getDirectChildrenIdSet(id);
            if (children != null && children.size() > 0) {
                for (T child : children) setDepth(get(child));
            }
        }
    }

    /**
     * 获取根节点.
     *
     * @return root
     */
    @Override
    public E getRoot() {
        return root;
    }

    /**
     * 获取给定id所代表的节点的所有子节点id,只是获取孩子节点,不包含孙子节点及以下.
     *
     * @param id 节点id
     * @return 这个节点id下的所有子节点, 如果没有, 则返回<code>null</code>
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
     * 获取给定ID对应的节点.
     *
     * @param id id
     * @return node
     */
    public E get(T id) {
        return id2NodeMap.get(id);
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
            T pid = parent.getId();
            E node = get(pid);
            ancestors.add(node);
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
     * 校验节点.
     *
     * @param node node
     */
    private void validateNode(E node) {
        if (node.getId() == null) {
            throw new IllegalStateException("节点的ID不能是:" + null);
        }
    }

    /**
     * 验证两个节点的关系.
     *
     * @param parent parent
     * @param child  child
     */
    private void validateRelationship(E parent, E child) {
        T childId = child.getId();
        if (CompareUtils.eq(childId, root.getId())) {
            throw new IllegalStateException("root不能有父节点");
        }
        T parentId = parent.getId();
        if (alreadyHasParent(child)) {
            T oldParentId = id2PidMap.get(childId);
            if (!CompareUtils.eq(oldParentId, parentId)) {
                throw new IllegalStateException("子节点[ ID = " + childId + " ],已经拥有父节点( ID=" + oldParentId + " )," +
                        "因此,不能将[ ID=" + parentId + " ]的节点设置成它的父节点." +
                        "子节点只能有一个父节点");
            }
        }
        if (alreadyHasRelationship(child, parent)) {
            throw new IllegalStateException("想建立[ parent:" + parentId + " -> child:" + childId + " ]的父子关系," +
                    "但是在树中已经存在[ parent:" + childId + " -> child:" + parentId + " ]的关系." +
                    "父子关系不能互换");
        }
    }

    /**
     * 判断该节点在树中是否已经拥有父节点.
     *
     * @param child child
     * @return <code>true</code>-如果已经拥有
     */
    private boolean alreadyHasParent(E child) {
        T childId = child.getId();
        T parentId = id2PidMap.get(childId);
        return parentId != null;
    }

    /**
     * 这两个节点,在树中是否已经拥有父子关系.
     *
     * @param parent parent
     * @param child  child
     * @return true-已经拥有正确的父子关系
     */
    private boolean alreadyHasRelationship(E parent, E child) {
        T oldParentId = id2PidMap.get(child.getId());
        return CompareUtils.eq(oldParentId, parent.getId());
    }

    private void addNode(E node) {
        validateNode(node);
        id2NodeMap.put(node.getId(), node);
    }
}
