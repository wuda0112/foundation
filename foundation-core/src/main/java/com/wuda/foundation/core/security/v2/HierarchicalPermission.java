package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.tree.TreeNode;

/**
 * 由于{@link HierarchicalPermissionTarget}没有层级结构,因此最终的权限也具有层级结构.
 *
 * @param <T> permission target的类型
 * @author wuda
 */
public class HierarchicalPermission<T> extends AbstractPermission<T> implements TreeNode<Long> {

    /**
     * parent id.
     */
    private Long parentId;

    /**
     * 构造方法.
     *
     * @param id       id
     * @param parentId parent id
     * @param name     name
     * @param target   target
     */
    public HierarchicalPermission(Long id, Long parentId, String name, T target) {
        super(id, name, target);
        this.parentId = parentId;
    }

    @Override
    public Long getPid() {
        return parentId;
    }

    /**
     * get parent id.
     *
     * @return parent id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * set parent id
     *
     * @param parentId parent id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
