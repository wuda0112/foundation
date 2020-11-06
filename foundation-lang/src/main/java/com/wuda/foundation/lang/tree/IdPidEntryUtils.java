package com.wuda.foundation.lang.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link IdPidEntry}类的工具类.
 *
 * @author wuda
 */
public class IdPidEntryUtils {

    /**
     * 根据ID分组,由于ID是唯一的,因此一个ID只对应一个节点.
     *
     * @param entryList list of entry
     * @return key - id , value - entry
     */

    public static <T extends Comparable<T>, E extends IdPidEntry<T>> Map<T, E> groupById(Collection<E> entryList) {
        int size = entryList.size();
        Map<T, E> map = new HashMap<>(size);
        for (E entry : entryList) {
            T id = entry.getId();
            if (map.containsKey(id)) {
                throw new IllegalArgumentException("IdPidEntry id = " + id + ",duplicate!");
            }
            map.put(entry.getId(), entry);
        }
        return map;
    }

    public static <T extends Comparable<T>, E extends IdPidEntry<T>> Set<T> getIdSet(Collection<E> entryList) {
        if (entryList == null || entryList.isEmpty()) {
            return null;
        }
        return entryList.stream().map(E::getId).collect(Collectors.toSet());
    }

    /**
     * 根据PID分组,也是为parent找寻children(注意只是直接children,不包含孙子及更后续的节点)的过程.
     *
     * @param entryList list of entry
     * @param <T>       节点的ID的数据类型
     * @param <E>       节点的类型
     * @return key - pid , value - 该父节点的所有子节点
     */
    public static <T extends Comparable<T>, E extends IdPidEntry<T>> Map<T, List<E>> groupByPid(Collection<E> entryList) {
        Map<T, List<E>> byPidMap = new HashMap<>();
        for (E entry : entryList) {
            T pid = entry.getPid();
            if (byPidMap.containsKey(pid)) {
                byPidMap.get(pid).add(entry);
            } else {
                List<E> children = new ArrayList<>();
                children.add(entry);
                byPidMap.put(pid, children);
            }

        }
        return byPidMap;
    }

    /**
     * 递归的为给定的ID寻找所有后裔.
     *
     * @param startId    给定的ID
     * @param candidates 候选实体
     * @param descendant 用于接收寻找到的后裔,不能为<code>null</code>
     * @param <T>        ID的类型
     * @param <E>        具有id-pid关系的实体的类型
     */
    public static <T extends Comparable<T>, E extends IdPidEntry<T>> void getDescendant(T startId, Collection<E> candidates, List<E> descendant) {
        if (descendant == null) {
            throw new NullPointerException("用于接收后裔的集合不能为null");
        }
        if (startId == null || candidates == null || candidates.isEmpty()) {
            return;
        }
        Map<T, List<E>> childrenByPidMap = groupByPid(candidates);
        List<E> children = childrenByPidMap.get(startId);
        if (children == null || children.isEmpty()) {
            return;
        }
        descendant.addAll(children);
        for (E child : children) {
            T id = child.getId();
            childrenByPidMap.clear();
            getDescendant(id, candidates, descendant);
        }
    }

    /**
     * 递归的为给定的ID寻找所有祖先.
     *
     * @param startId    给定的ID
     * @param candidates 候选实体
     * @param ancestor   用于接收寻找到的祖先,不能为<code>null</code>
     * @param <T>        ID的类型
     * @param <E>        具有id-pid关系的实体的类型
     */
    public static <T extends Comparable<T>, E extends IdPidEntry<T>> void getAncestor(T startId, Collection<E> candidates, List<E> ancestor) {
        if (ancestor == null) {
            throw new NullPointerException("用于接收祖先的集合不能为null");
        }
        if (startId == null || candidates == null || candidates.isEmpty()) {
            return;
        }
        Map<T, E> entryByIdMap = groupById(candidates);
        E idPidEntry = entryByIdMap.get(startId);
        if (idPidEntry == null) {
            return;
        }
        T pid = idPidEntry.getPid();
        E parent = entryByIdMap.get(pid);
        if (parent == null) {
            return;
        }
        ancestor.add(parent);
        entryByIdMap.clear();
        getAncestor(pid, candidates, ancestor);
    }


    /**
     * 在连续的具有id-pid关系的节点中,获取处于最上层的节点.比如有如下的树形结构
     * <pre>
     *     中国
     *      - 湖南省
     *              - 长沙市
     *                      - 长沙县
     *                              - 泉塘
     *                      - 天心区
     *                      - 岳麓区
     *                      - 芙蓉区
     *              - 张家界市
     *                         - 桑植县
     *                                  - 澧源镇
     *                                          - 岩娅村
     *                                  - 瑞塔铺
     *                         - 慈利县
     *      - 广东省
     *              - 广州市
     *                      - 天河区
     *                      - 越秀区
     * </pre>
     * 对照上面的树形结构,我们考虑下每种场景下的top
     * <ul>
     * <li>传入的候选节点是这棵完整的树,则top set只有一个,即：【中国】那个节点</li>
     * <li>删除【中国】那个节点,则top set有【湖南省,广东省】</li>
     * <li>删除【中国】,【广州市】这个两个节点,则top set有【湖南省,广东省,天河区,越秀区】.【湖南省,广东省】作为top没什么异议,
     * 但是为什么【天河区,越秀区】也会在top set中呢?因为【广州市】被删除后,【天河区,越秀区】这两个节点在这棵树中已经"断开了",
     * 即使从人的角度来看,它们的上级是【广东省】,但是作为程序,已经无法知道【天河区,越秀区】这两个节点的上级了,没有上级,则它们
     * 肯定是top.
     * </li>
     * </ul>
     *
     * @param candidates 候选节点
     * @param topSet     top set
     * @param <T>        节点的ID的数据类型
     * @param <E>        节点的类型
     * @return top set
     */
    public static <T extends Comparable<T>, E extends IdPidEntry<T>> Set<T> getTop(Collection<E> candidates, Set<T> topSet) {
        if (topSet == null) {
            throw new NullPointerException("用于接收top的集合不能为null");
        }
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        Map<T, E> entryByIdMap = groupById(candidates);
        Map<T, List<E>> childrenByPidMap = groupByPid(candidates);
        // 如果想要成为top,首先需要是其他节点的parent,因此top只会在parent中产生
        Set<T> candidatePidSet = childrenByPidMap.keySet();
        Set<T> exclusivePidSet = new HashSet<>();
        for (T candidatePid : candidatePidSet) {
            E parentEntry = entryByIdMap.get(candidatePid);
            if (parentEntry == null) {
                // 节点的父级不存在,这这些节点已经是top了
                List<E> children = childrenByPidMap.get(candidatePid);
                if (children != null && !children.isEmpty()) {
                    Set<T> idSet = getIdSet(children);
                    topSet.addAll(idSet);
                }
                exclusivePidSet.add(candidatePid);
            }
        }
        candidatePidSet.removeAll(exclusivePidSet);
        if (candidatePidSet.isEmpty()) {
            return topSet;
        }
        List<E> nextCandidates = new ArrayList<>(candidatePidSet.size());
        for (T candidatePid : candidatePidSet) {
            E parentEntry = entryByIdMap.get(candidatePid);
            nextCandidates.add(parentEntry);
        }
        return getTop(nextCandidates, topSet);
    }
}
