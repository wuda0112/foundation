package com.wuda.foundation.lang.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 将具有ID/PID模式的数据生成{@link Tree}结构.
 * 因为ID/PID描述了上下级关系,因此根据这个特点可以很容易的生成树形结构.
 *
 * @param <T> {@link IdPidEntry}中id和pid的类型,同时也是{@link TreeNode#getId()} 的数据类型
 * @param <Z> {@link TreeNode}的实现类
 * @author wuda
 * @since 1.0.0
 */
public class IdPidEntryTreeBuilder<T extends Comparable<T>, Z extends TreeNode<T>> {

    /**
     * logger.
     */
    private static Logger logger = Logger.getLogger(IdPidEntryTreeBuilder.class.getName());

    /**
     * 向已经存在的{@link Tree}中添加新的节点.
     *
     * @param tree  已经存在的{@link Tree}
     * @param nodes 新的节点
     */
    public void add(Tree<T, Z> tree, List<Z> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        Map<T, Z> map = groupingById(nodes);
        for (Z node : nodes) {
            T id = node.getId();
            if (CompareUtils.eq(id, tree.getRoot().getId())) {
                continue;
            }
            T pid = node.getPid();
            Z child = getFrom(map, tree, id);
            Z parent = getFrom(map, tree, pid);
            if (parent != null && child != null) {
                tree.createRelationship(parent, child);
            } else {
                logger.warning("id = " + id + ", pid = " + pid + ", 至少有一个找不到对应的节点");
            }
        }
    }

    /**
     * 从给定的两个容器中查找ID对应的节点.
     *
     * @param map  map
     * @param tree tree
     * @param id   节点ID
     * @return node
     */
    private Z getFrom(Map<T, Z> map, Tree<T, Z> tree, T id) {
        Z treeNode = map.get(id);
        if (treeNode == null) {
            treeNode = tree.get(id);
        }
        return treeNode;
    }

    /**
     * 根据ID分组,由于ID是唯一的,因此一个ID只对应一个节点.
     *
     * @param nodes list of node
     * @return key - id , value - node
     */
    private Map<T, Z> groupingById(List<Z> nodes) {
        int size = nodes.size();
        Map<T, Z> map = new HashMap<>(size);
        for (Z treeNode : nodes) {
            map.put(treeNode.getId(), treeNode);
        }
        return map;
    }
}
