package com.wuda.foundation.item;

import com.wuda.foundation.commons.DescribeTreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeItemCategory extends DescribeTreeNode {

    private Long storeId;
}
