package com.wuda.foundation.commons;

import com.wuda.foundation.lang.identify.LongIdentifier;
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
    protected String name;
    protected String description;
    protected Long parentNodeId;
    private LongIdentifier owner;
    private TreeNodeUse use;

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
        protected String name;
        protected String description;
        protected Long parentNodeId;
        private LongIdentifier owner;
        private TreeNodeUse use;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setParentNodeId(Long parentNodeId) {
            this.parentNodeId = parentNodeId;
            return this;
        }

        public Builder setOwner(LongIdentifier owner) {
            this.owner = owner;
            return this;
        }

        public Builder setUse(TreeNodeUse use) {
            this.use = use;
            return this;
        }

        @Override
        public CreateTreeNode build() {
            CreateTreeNode createTreeNode = new CreateTreeNode();
            createTreeNode.id = Objects.requireNonNull(this.id);
            createTreeNode.name = Objects.requireNonNull(this.name);
            createTreeNode.description = this.description;
            createTreeNode.parentNodeId = Objects.requireNonNull(parentNodeId);
            createTreeNode.owner = Objects.requireNonNull(owner);
            createTreeNode.use = Objects.requireNonNull(use);
            return createTreeNode;
        }
    }
}
