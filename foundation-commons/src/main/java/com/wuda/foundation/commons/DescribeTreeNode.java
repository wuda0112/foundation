package com.wuda.foundation.commons;

import com.wuda.foundation.lang.Constant;
import com.wuda.foundation.lang.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeTreeNode implements TreeNode<Long> {

    private Long id;
    private Long pid;
    private String name;
    private String description;

    /**
     * 默认的根节点.
     */
    public final static DescribeTreeNode root = new DescribeTreeNode(Constant.NOT_EXISTS_ID, Constant.NOT_EXISTS_ID, "root", "root node");

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getPid() {
        return pid;
    }

    @Override
    public String getName() {
        return name;
    }
}
