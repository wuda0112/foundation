package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.tree.Tree;

import java.util.List;
import java.util.Map;

/**
 * 代表一个菜单.
 *
 * @author wuda
 * @since 1.0.3
 */
public class Menu {
    /**
     * menu id.
     */
    private Long id;
    /**
     * 分类树.
     */
    private Tree<Long, DescribeMenuItemCategory> categoryTree;
    /**
     * key: 分类ID; value: 分类下的item
     */
    private Map<Long, List<DescribeMenuItem>> menuItemByCategoryMap;

    public Long getMenuItemCategoryId(Long menuItemId) {
        return null;
    }
}
