package com.wuda.foundation.lang.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * 将具有ID/PID模式的数据生成{@link Tree}结构.
 * 因为ID/PID描述了上下级关系,因此根据这个特点可以很容易的生成树形结构.
 *
 * @param <T> {@link IdPidEntry}中id和pid的类型,同时也是{@link TreeElement#getIdentifier()}的数据类型
 * @param <G> {@link IdPidEntry}的实现类
 * @param <Z> {@link TreeElement}的实现类
 * @author wuda
 * @since 1.0.0
 */
public class IdPidEntryTreeBuilder<T extends Comparable<T>, G extends IdPidEntry<T>, Z extends TreeElement<T>> {

    /**
     * logger.
     */
    private static Logger logger = Logger.getLogger(IdPidEntryTreeBuilder.class.getName());

    /**
     * 向已经存在的{@link Tree}中添加新的元素.
     *
     * @param tree      已经存在的{@link Tree}
     * @param entryList 新的元素
     * @param mapper    从{@link IdPidEntry}生成{@link TreeElement}
     */
    public void add(Tree<T, Z> tree, List<G> entryList, Function<G, Z> mapper) {
        if (entryList == null || entryList.isEmpty()) {
            return;
        }
        List<Z> treeElements = from(entryList, mapper);
        Map<T, Z> map = groupingById(treeElements);
        for (G entry : entryList) {
            T id = entry.getId();
            if (CompareUtils.eq(id, tree.getRoot().getIdentifier())) {
                continue;
            }
            T pid = entry.getPid();
            Z child = getFrom(map, tree, id);
            Z parent = getFrom(map, tree, pid);
            if (parent != null && child != null) {
                tree.createRelationship(parent, child);
            } else {
                logger.warning("id = " + id + ", pid = " + pid + ", 至少有一个找不到对应的元素");
            }
        }
    }

    private List<Z> from(List<G> entryList, Function<G, Z> mapper) {
        List<Z> treeElements = new ArrayList<>(entryList.size());
        for (G entry : entryList) {
            Z treeElement = mapper.apply(entry);
            treeElements.add(treeElement);
        }
        return treeElements;
    }

    /**
     * 从给定的两个容器中查找ID对应的元素.
     *
     * @param map  map
     * @param tree tree
     * @param id   元素ID
     * @return element
     */
    private Z getFrom(Map<T, Z> map, Tree<T, Z> tree, T id) {
        Z treeElement = map.get(id);
        if (treeElement == null) {
            treeElement = tree.get(id);
        }
        return treeElement;
    }

    /**
     * 根据ID分组,由于ID是唯一的,因此一个ID只对应一个元素.
     *
     * @param treeElements list of element
     * @return key - id , value - element
     */
    private Map<T, Z> groupingById(List<Z> treeElements) {
        int size = treeElements.size();
        Map<T, Z> map = new HashMap<>(size);
        for (Z treeElement : treeElements) {
            map.put(treeElement.getIdentifier(), treeElement);
        }
        return map;
    }
}
