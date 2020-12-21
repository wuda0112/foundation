package com.wuda.foundation.core.commons;

import java.util.List;

public abstract class AbstractMenuItemCategoryManager extends AbstractTreeManager<CreateMenuItemCategory, UpdateMenuItemCategory, DescribeMenuItemCategory> implements MenuItemCategoryManager {

    @Override
    public int itemCountInCategory(Long categoryId) {
        return itemCountInCategoryDbOp(categoryId);
    }

    protected abstract int itemCountInCategoryDbOp(Long categoryId);

    @Override
    public int childCount(Long nodeId) {
        return childCountDbOp(nodeId);
    }

    protected abstract int childCountDbOp(Long nodeId);

    @Override
    public List<DescribeMenuItemCategory> getChildren(Long nodeId) {
        return getChildrenDbOp(nodeId);
    }

    protected abstract List<DescribeMenuItemCategory> getChildrenDbOp(Long nodeId);

    @Override
    public DescribeMenuItemCategory getTreeNode(Long nodeId) {
        return getTreeNodeDbOp(nodeId);
    }

    protected abstract DescribeMenuItemCategory getTreeNodeDbOp(Long nodeId);

    @Override
    public List<DescribeMenuItemCategory> getDescendantOfRoot(Long rootId) {
        return getDescendantOfRootDbOp(rootId);
    }

    protected abstract List<DescribeMenuItemCategory> getDescendantOfRootDbOp(Long rootId);

    @Override
    public boolean checkNameExists(Long parentId, String childName) {
        return checkNameExistsDbOp(parentId, childName);
    }

    protected abstract boolean checkNameExistsDbOp(Long parentId, String childName);
}
