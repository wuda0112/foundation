package com.wuda.foundation.security;

import java.util.Set;

/**
 * 为目{@link SubjectGroup}分配/取消permission时使用的参数.
 *
 * @author wuda
 * @since 1.0.0
 */
public class SubjectGroupPermissionGrantRequest extends PermissionGrantRequest<SubjectGroup> {

    /**
     * 构造请求实例.
     *
     * @param owner                 owner
     * @param permissionTargetId    permission target id
     * @param permissionActionIdSet permission action id set,可以为<code>null</code>,表示不分配action
     */
    public SubjectGroupPermissionGrantRequest(SubjectGroup owner, Long permissionTargetId, Set<Long> permissionActionIdSet) {
        super(owner, permissionTargetId, permissionActionIdSet);
    }

    /**
     * 构造请求实例.
     *
     * @param owner              owner
     * @param permissionTargetId permission target id
     */
    public SubjectGroupPermissionGrantRequest(SubjectGroup owner, Long permissionTargetId) {
        this(owner, permissionTargetId, null);
    }
}
