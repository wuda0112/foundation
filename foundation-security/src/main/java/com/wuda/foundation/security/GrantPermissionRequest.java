package com.wuda.foundation.security;

import lombok.ToString;

import java.util.*;

/**
 * 为目标对象分配/取消{@link Permission}时使用的参数.
 *
 * @author wuda
 * @since 1.0.0
 */
@ToString
public class GrantPermissionRequest {

    private Subject subject;
    private Long permissionTargetId;
    private Set<Long> permissionActionIdSet;

    public Subject getSubject() {
        return subject;
    }

    /**
     * 构造请求实例.
     *
     * @param subject               subject
     * @param permissionTargetId    permission target id
     * @param permissionActionIdSet permission action id set,可以为<code>null</code>,表示不分配action
     */
    public GrantPermissionRequest(Subject subject, Long permissionTargetId, Set<Long> permissionActionIdSet) {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(permissionTargetId);
        this.subject = subject;
        this.permissionTargetId = permissionTargetId;
        this.permissionActionIdSet = permissionActionIdSet;
    }

    /**
     * 构造请求实例.
     *
     * @param subject            subject
     * @param permissionTargetId permission target id
     */
    public GrantPermissionRequest(Subject subject, Long permissionTargetId) {
        this(subject, permissionTargetId, null);
    }

    /**
     * 合并.首先,相同{@link #subject}的{@link GrantPermissionRequest}先合并,然后同一个{@link #subject}下相同的{@link #permissionTargetId}进行合并,{@link #permissionActionIdSet}取并集.
     *
     * @param requestList 待合并的{@link GrantPermissionRequest}
     * @return 合并后的结果
     */
    public static <T> List<? extends GrantPermissionRequest> merge(final List<GrantPermissionRequest> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return null;
        }
        Map<Subject, Map<Long, Set<Long>>> map = new HashMap<>();
        for (GrantPermissionRequest grantPermissionRequest : requestList) {
            Subject subject = grantPermissionRequest.getSubject();
            Map<Long, Set<Long>> targetToActionMap;
            Set<Long> permissionActionIdSet;
            if ((targetToActionMap = map.get(subject)) == null) {
                targetToActionMap = new HashMap<>();
                permissionActionIdSet = new HashSet<>();
                targetToActionMap.put(grantPermissionRequest.permissionTargetId, permissionActionIdSet);
                map.put(subject, targetToActionMap);
            }
            if (grantPermissionRequest.permissionActionIdSet != null && !grantPermissionRequest.permissionActionIdSet.isEmpty()) {
                permissionActionIdSet = targetToActionMap.get(grantPermissionRequest.permissionTargetId);
                permissionActionIdSet.addAll(grantPermissionRequest.permissionActionIdSet);
            }
        }
        return from(map);
    }

    /**
     * 解析生成{@link GrantPermissionRequest}实例.
     *
     * @param map key: subject; value: permission target id to permission action ids map
     * @return list of {@link GrantPermissionRequest}
     */
    public static <T> List<GrantPermissionRequest> from(Map<Subject, Map<Long, Set<Long>>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Set<Map.Entry<Subject, Map<Long, Set<Long>>>> entrySet = map.entrySet();
        List<GrantPermissionRequest> list = new ArrayList<>();
        Subject subject;
        Long permissionTargetId;
        Set<Long> permissionActionIdSet;
        for (Map.Entry<Subject, Map<Long, Set<Long>>> entry : entrySet) {
            subject = entry.getKey();
            Map<Long, Set<Long>> targetToActionMap = entry.getValue();
            if (targetToActionMap == null || targetToActionMap.isEmpty()) {
                throw new IllegalStateException("subject = " + subject + ",没有指定permission信息");
            }
            Set<Map.Entry<Long, Set<Long>>> targetAndActionEntrySet = targetToActionMap.entrySet();
            for (Map.Entry<Long, Set<Long>> targetAndActionEntry : targetAndActionEntrySet) {
                permissionTargetId = targetAndActionEntry.getKey();
                permissionActionIdSet = targetAndActionEntry.getValue();
                GrantPermissionRequest grantPermissionRequest = new GrantPermissionRequest(subject, permissionTargetId, permissionActionIdSet);
                list.add(grantPermissionRequest);
            }
        }
        return list;
    }
}
