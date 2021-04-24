package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.lang.tree.TreeNode;

/**
 * 具有树形层级结构的Subject.比如权限用户组.
 *
 * @author wuda
 */
public class HierarchicalPermissionSubject extends AbstractPermissionSubject implements TreeNode<Long> {

    /**
     * parent id.
     */
    private Long parentId;

    /**
     * 构造实例.
     *
     * @param identifier  subject的唯一标记
     * @param parentId    父级的唯一标记
     * @param name        name
     * @param description description
     */
    public HierarchicalPermissionSubject(LongIdentifier identifier, Long parentId, String name, String description) {
        super(identifier, name, description);
        this.parentId = parentId;
    }

    @Override
    public Long getId() {
        return identifier.getValue();
    }

    @Override
    public Long getPid() {
        return parentId;
    }
}
