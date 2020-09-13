package com.wuda.foundation.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for update tree node.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateTreeNode {

    protected Long id;
    protected String name;
    protected String description;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    protected UpdateTreeNode() {

    }

    /**
     * create or update时很有用.
     *
     * @param id             tree node id
     * @param createTreeNode 创建时的参数
     * @return 更新的参数
     */
    public static UpdateTreeNode from(Long id, CreateTreeNode createTreeNode) {
        return new Builder()
                .setId(id)
                .setDescription(createTreeNode.getDescription())
                .build();
    }

    /**
     * 用于创建{@link UpdateTreeNode}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateTreeNode> {

        protected Long id;
        protected String name;
        protected String description;

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


        @Override
        public UpdateTreeNode build() {
            UpdateTreeNode createTreeNode = new UpdateTreeNode();
            createTreeNode.id = Objects.requireNonNull(this.id);
            createTreeNode.name = this.name;
            createTreeNode.description = this.description;
            return createTreeNode;
        }
    }
}
