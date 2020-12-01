package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeTreeNode implements TreeNode<Long> {

    protected Long id;
    protected Long parentId;
    protected long rootId;
    protected int depth;
    protected String name;
    protected String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPid() {
        return parentId;
    }

    @Override
    public String getName() {
        return name;
    }
}
