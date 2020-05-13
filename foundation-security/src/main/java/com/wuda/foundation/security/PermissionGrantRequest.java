package com.wuda.foundation.security;

import lombok.ToString;

import java.util.*;

/**
 * 为目标对象分配/取消permission时使用的参数.
 *
 * @author wuda
 * @since 1.0.0
 */
@ToString
public class PermissionGrantRequest<T> {

    private T owner;
    private Long permissionTargetId;
    private Set<Long> permissionActionIdSet;

    public T getOwner(){
        return owner;
    }

    /**
     * 构造请求实例.
     *
     * @param owner                 owner
     * @param permissionTargetId    permission target id
     * @param permissionActionIdSet permission action id set,可以为<code>null</code>,表示不分配action
     */
    public PermissionGrantRequest(T owner, Long permissionTargetId, Set<Long> permissionActionIdSet) {
        Objects.requireNonNull(owner);
        Objects.requireNonNull(permissionTargetId);
        this.owner = owner;
        this.permissionTargetId = permissionTargetId;
        this.permissionActionIdSet = permissionActionIdSet;
    }

    /**
     * 构造请求实例.
     *
     * @param owner              owner
     * @param permissionTargetId permission target id
     */
    public PermissionGrantRequest(T owner, Long permissionTargetId) {
        this(owner, permissionTargetId, null);
    }

    /**
     * 合并.首先,相同{@link #owner}的{@link PermissionGrantRequest}先合并,然后同一个{@link #owner}下相同的{@link #permissionTargetId}进行合并,{@link #permissionActionIdSet}取并集.
     *
     * @param requestList 待合并的{@link PermissionGrantRequest}
     * @return 合并后的结果
     */
    public static <T> List<? extends PermissionGrantRequest> merge(final List<PermissionGrantRequest<T>> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return null;
        }
        Map<T, Map<Long, Set<Long>>> map = new HashMap<>();
        for (PermissionGrantRequest permissionGrantRequest : requestList) {
            T owner = (T) permissionGrantRequest.getOwner();
            Map<Long, Set<Long>> targetToActionMap;
            Set<Long> permissionActionIdSet;
            if ((targetToActionMap = map.get(owner)) == null) {
                targetToActionMap = new HashMap<>();
                permissionActionIdSet = new HashSet<>();
                targetToActionMap.put(permissionGrantRequest.permissionTargetId, permissionActionIdSet);
                map.put(owner, targetToActionMap);
            }
            if (permissionGrantRequest.permissionActionIdSet != null && !permissionGrantRequest.permissionActionIdSet.isEmpty()) {
                permissionActionIdSet = targetToActionMap.get(permissionGrantRequest.permissionTargetId);
                permissionActionIdSet.addAll(permissionGrantRequest.permissionActionIdSet);
            }
        }
        return from(map);
    }

    /**
     * 解析生成{@link PermissionGrantRequest}实例.
     *
     * @param map key: owner; value: permission target id to permission action ids map
     * @return list of {@link PermissionGrantRequest}
     */
    public static <T> List<PermissionGrantRequest> from(Map<T, Map<Long, Set<Long>>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Set<Map.Entry<T, Map<Long, Set<Long>>>> entrySet = map.entrySet();
        List<PermissionGrantRequest> list = new ArrayList<>();
        T owner;
        Long permissionTargetId;
        Set<Long> permissionActionIdSet;
        for (Map.Entry<T, Map<Long, Set<Long>>> entry : entrySet) {
            owner = entry.getKey();
            Map<Long, Set<Long>> targetToActionMap = entry.getValue();
            if (targetToActionMap == null || targetToActionMap.isEmpty()) {
                throw new IllegalStateException("owner = " + owner + ",没有指定permission信息");
            }
            Set<Map.Entry<Long, Set<Long>>> targetAndActionEntrySet = targetToActionMap.entrySet();
            for (Map.Entry<Long, Set<Long>> targetAndActionEntry : targetAndActionEntrySet) {
                permissionTargetId = targetAndActionEntry.getKey();
                permissionActionIdSet = targetAndActionEntry.getValue();
                PermissionGrantRequest<T> permissionGrantRequest = new PermissionGrantRequest<T>(owner, permissionTargetId, permissionActionIdSet);
                list.add(permissionGrantRequest);
            }
        }
        return list;
    }
}
