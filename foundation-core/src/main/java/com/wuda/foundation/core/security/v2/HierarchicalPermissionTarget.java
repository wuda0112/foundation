package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.lang.tree.TreeNode;

/**
 * 具有树形层级结构的Target.比如对于文件访问权限中的文件系统.
 *
 * @author wuda
 */
public abstract class HierarchicalPermissionTarget extends AbstractPermissionTarget implements TreeNode<Long> {

    /**
     * parent id.
     */
    private Long parentId;

    /**
     * 构造实例.
     *
     * @param identifier  target的唯一标记
     * @param parentId    父级的唯一标记
     * @param name        name
     * @param description description
     */
    public HierarchicalPermissionTarget(LongIdentifier identifier, Long parentId, String name, String description) {
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
