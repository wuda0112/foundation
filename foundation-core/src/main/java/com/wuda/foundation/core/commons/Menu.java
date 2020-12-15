package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.tree.MappedTree;

import java.util.Objects;

/**
 * 代表一个菜单.
 *
 * @author wuda
 * @since 1.0.3
 */
public class Menu extends MappedTree<Long, DescribeMenuNode> {

    /**
     * menu id.
     */
    private Long id;

    /**
     * 构造树.
     *
     * @param id   menu id
     * @param root 根节点
     */
    public Menu(Long id, DescribeMenuNode root) {
        super(root);
        this.id = Objects.requireNonNull(id);
    }

    /**
     * 获取menu id.
     *
     * @return menu id
     */
    public Long getId() {
        return id;
    }
}
