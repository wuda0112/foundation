package com.wuda.foundation.core.commons;

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
    private Type type;

    /**
     * 节点的类型.
     *
     * @author wuda
     * @since 1.0.3
     */
    public enum Type {
        /**
         * menu item.
         */
        MENU_ITEM,
        /**
         * menu item category.
         */
        MENU_ITEM_CATEGORY;
    }

    /**
     * 新建menu item节点.
     *
     * @param describeMenuItemCategory menu item category
     * @param describeMenuItem         menu item
     * @return item节点
     */
    public static DescribeMenuNode newMenuItemNode(DescribeMenuItemCategory describeMenuItemCategory, DescribeMenuItem describeMenuItem) {
        DescribeMenuNode describeMenuNode = new DescribeMenuNode();
        describeMenuNode.setType(Type.MENU_ITEM);
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
        describeMenuNode.setType(Type.MENU_ITEM_CATEGORY);
        describeMenuNode.setId(describeMenuItemCategory.id);
        describeMenuNode.setParentId(describeMenuItemCategory.parentId);
        describeMenuNode.setRootId(describeMenuItemCategory.rootId);
        describeMenuNode.setDepth(describeMenuItemCategory.depth);
        describeMenuNode.setName(describeMenuItemCategory.name);
        describeMenuNode.setDescription(describeMenuItemCategory.description);
        return describeMenuNode;
    }
}
