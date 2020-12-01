package com.wuda.foundation.core.item;

import com.wuda.foundation.core.commons.DescribeTreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeItemCategory extends DescribeTreeNode {

    private Long storeId;
}
