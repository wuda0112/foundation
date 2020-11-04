package com.wuda.foundation.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeTreeNode {

    private Long treeNodeId;
    private Long parentTreeNodeId;
    private long rootTreeNodeId;
    private int depth;
}
