package com.wuda.foundation.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for create tree node.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateTreeNode {

    protected Long id;
    protected Long parentTreeNodeId;

    /**
     * root节点的ID.
     */
    private Long rootTreeNodeId;
    /**
     * 节点的深度.
     */
    private int depth;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    protected CreateTreeNode() {

    }

    /**
     * 包访问权限,也就是说这个字段的值不能通过外部设置,只能通过程序内部设置.
     *
     * @param rootTreeNodeId root tree node id
     */
    void setRootTreeNodeId(Long rootTreeNodeId) {
        this.rootTreeNodeId = rootTreeNodeId;
    }

    /**
     * get root tree node id;
     *
     * @return root tree node id
     */
    public Long getRootTreeNodeId() {
        return rootTreeNodeId;
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
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateTreeNode> {

        protected Long id;
        protected Long parentTreeNodeId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setParentTreeNodeId(Long parentTreeNodeId) {
            this.parentTreeNodeId = parentTreeNodeId;
            return this;
        }


        @Override
        public CreateTreeNode build() {
            CreateTreeNode createTreeNode = new CreateTreeNode();
            createTreeNode.id = Objects.requireNonNull(this.id);
            createTreeNode.parentTreeNodeId = Objects.requireNonNull(parentTreeNodeId);
            return createTreeNode;
        }
    }
}
