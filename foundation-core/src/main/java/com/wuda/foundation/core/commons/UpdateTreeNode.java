package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.Builder;
import lombok.Getter;

/**
 * for update tree node.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateTreeNode {

    /**
     * 准备更新的节点的ID.
     */
    protected Long id;
    /**
     * 节点名称.
     */
    protected String name;
    /**
     * 节点描述.
     */
    protected String description;

    /**
     * 禁止实例化,使用{@link UpdateTreeNodeBuilder}实例化.
     */
    protected UpdateTreeNode() {

    }

    /**
     * 用于创建{@link UpdateTreeNode}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public abstract static class UpdateTreeNodeBuilder<T extends UpdateTreeNode, B extends UpdateTreeNodeBuilder<T, B>> implements Builder<T> {
        /**
         * 准备更新的节点的ID.
         */
        protected Long id;
        /**
         * 节点名称.
         */
        protected String name;
        /**
         * 节点描述.
         */
        protected String description;

        /**
         * 设置准备更新的节点的ID.
         *
         * @param id 节点的ID
         * @return this
         */
        public abstract B setId(Long id);

        /**
         * 设置准备更新的节点的名称.
         *
         * @param name 节点名称
         * @return this
         */
        public abstract B setName(String name);

        /**
         * 设置准备更新的节点的描述.
         *
         * @param description 节点描述
         * @return this
         */
        public abstract B setDescription(String description);
    }
}
