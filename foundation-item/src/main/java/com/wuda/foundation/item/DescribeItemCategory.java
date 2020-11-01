package com.wuda.foundation.item;

import com.wuda.foundation.lang.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeItemCategory implements TreeNode<Long> {

    private Long id;
    private Long parentCategoryId;
    private String name;
    private Long storeId;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Long getPid() {
        return null;
    }
}
