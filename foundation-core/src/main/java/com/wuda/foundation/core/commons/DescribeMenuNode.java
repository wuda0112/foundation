package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescribeMenuNode extends DescribeTreeNode {

    /**
     * 该节点的类型,是menu item还是menu item category.
     */
    private IdentifierType type;

    /**
     * 是否可见.
     */
    private boolean visibility = true;

    /**
     * 新建menu item节点.
     *
     * @param describeMenuItemCategory menu item category
     * @param describeMenuItem         menu item
     * @return item节点
     */
    public static DescribeMenuNode newMenuItemNode(DescribeMenuItemCategory describeMenuItemCategory, DescribeMenuItem describeMenuItem) {
        DescribeMenuNode describeMenuNode = new DescribeMenuNode();
        describeMenuNode.setType(BuiltinIdentifierType.MENU_ITEM);
        describeMenuNode.setId(describeMenuItem.getId());
        describeMenuNode.setParentId(describeMenuItemCategory.getId());
        describeMenuNode.setRootId(describeMenuItemCategory.getRootId());
        describeMenuNode.setDepth(describeMenuItemCategory.getDepth() + 1);
        describeMenuNode.setName(describeMenuItem.getName());
        describeMenuNode.setDescription(describeMenuItem.getDescription());
        return describeMenuNode;
    }

    /**
     * 新建menu item category节点.
     *
     * @param describeMenuItemCategory menu item category
     * @return 分类节点
     */
    public static DescribeMenuNode newMenuItemCategoryNode(DescribeMenuItemCategory describeMenuItemCategory) {
        DescribeMenuNode describeMenuNode = new DescribeMenuNode();
        describeMenuNode.setType(BuiltinIdentifierType.MENU_ITEM_CATEGORY);
        describeMenuNode.setId(describeMenuItemCategory.id);
        describeMenuNode.setParentId(describeMenuItemCategory.parentId);
        describeMenuNode.setRootId(describeMenuItemCategory.rootId);
        describeMenuNode.setDepth(describeMenuItemCategory.depth);
        describeMenuNode.setName(describeMenuItemCategory.name);
        describeMenuNode.setDescription(describeMenuItemCategory.description);
        return describeMenuNode;
    }
}
