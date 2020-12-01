package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.Builder;
import lombok.Getter;

/**
 * for create tree node.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateTreeNode {

    /**
     * 节点的ID.
     */
    protected Long id;
    /**
     * 父节点的ID.
     */
    protected Long parentId;
    /**
     * root节点的ID.
     */
    private Long rootId;
    /**
     * 节点的深度.
     */
    private int depth;
    /**
     * 节点名称.
     */
    protected String name;
    /**
     * 节点描述.
     */
    protected String description;

    /**
     * 禁止实例化,使用{@link CreateTreeNodeBuilder}实例化.
     */
    protected CreateTreeNode() {

    }

    /**
     * 包访问权限,也就是说这个字段的值不能通过外部设置,只能通过程序内部设置.
     *
     * @param rootId root tree node id
     */
    void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    /**
     * get root tree node id;
     *
     * @return root tree node id
     */
    public Long getRootId() {
        return rootId;
    }

    /**
     * 包访问权限,也就是说这个字段的值不能通过外部设置,只能通过程序内部设置.
     *
     * @param depth depth
     */
    void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * get depth.
     *
     * @return depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * 用于创建{@link CreateTreeNode}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public abstract static class CreateTreeNodeBuilder<T extends CreateTreeNode, B extends CreateTreeNodeBuilder<T, B>> implements Builder<T> {
        /**
         * 节点的ID.
         */
        protected Long id;
        /**
         * 父节点的ID.
         */
        protected Long parentId;
        /**
         * 节点名称.
         */
        protected String name;
        /**
         * 节点描述.
         */
        protected String description;

        /**
         * 设置准备创建的节点的ID.
         *
         * @param id 节点ID
         * @return this
         */
        public abstract B setId(Long id);

        /**
         * 设置准备创建的节点的父节点的ID.
         *
         * @param parentId 父节点的ID
         * @return this
         */
        public abstract B setParentId(Long parentId);

        /**
         * 设置准备创建的节点的名称.
         *
         * @param name 节点名称
         * @return this
         */
        public abstract B setName(String name);

        /**
         * 设置准备创建的节点的描述.
         *
         * @param description 节点描述
         * @return this
         */
        public abstract B setDescription(String description);
    }
}
