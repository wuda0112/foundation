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
    protected Long parentNodeId;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    protected CreateTreeNode() {

    }

    /**
     * 用于创建{@link CreateTreeNode}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateTreeNode> {

        protected Long id;
        protected Long parentNodeId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setParentNodeId(Long parentNodeId) {
            this.parentNodeId = parentNodeId;
            return this;
        }


        @Override
        public CreateTreeNode build() {
            CreateTreeNode createTreeNode = new CreateTreeNode();
            createTreeNode.id = Objects.requireNonNull(this.id);
            createTreeNode.parentNodeId = Objects.requireNonNull(parentNodeId);
            return createTreeNode;
        }
    }
}
