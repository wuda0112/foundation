package com.wuda.foundation.core.security.impl.menu;

import com.wuda.foundation.core.commons.DescribeMenuItemCategory;
import com.wuda.foundation.core.commons.Menu;
import com.wuda.foundation.core.commons.MenuItemCategoryManager;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.Action;
import com.wuda.foundation.core.security.menu.MenuItemAndCategoryComparator;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAndCategoryComparatorImpl implements MenuItemAndCategoryComparator {
    private MenuManager menuManager;
    private MenuItemCategoryManager menuItemCategoryManager;

    private List<Tree<Long, DescribeMenuItemCategory>> categoryTreeCache = new ArrayList<>();

    @Override
    public boolean support(IdentifierType type) {
        return false;
    }

    @Override
    public boolean comparable(Action first, Action second) {
        return true;
    }

    @Override
    public boolean implies(Action first, Action second) {
        Long firstValue = first.getValue();
        Long secondValue = second.getValue();
        if (isMenuItem(first)) {
            // 已经是原子的实体,不可能包含其他实体了
            // todo 是否支持virtual
            return false;
        } else if (isMenuItemCategory(first)) {
            Tree<Long, DescribeMenuItemCategory> categoryTree = findCategoryTree(firstValue);
            if (isMenuItem(second)) {
                long menuId = categoryTree.get(firstValue).getMenuId();
                Menu menu = menuManager.getMenu(menuId);
                Long categoryId = menu.getMenuItemCategoryId(secondValue);
                return categoryTree.isDescendant(firstValue, categoryId);
            } else if (isMenuItemCategory(second)) {
                return categoryTree.isDescendant(firstValue, secondValue);
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean equals(Action first, Action second) {
        return first.equals(second);
    }

    @Override
    public boolean impliesOrEquals(Action first, Action second) {
        return equals(first, second) || implies(first, second);
    }

    private boolean isMenuItem(Action action) {
        return action.getType().equals(BuiltinIdentifierType.MENU_ITEM);
    }

    private boolean isMenuItemCategory(Action action) {
        return action.getType().equals(BuiltinIdentifierType.MENU_ITEM_CATEGORY);
    }

    private Tree<Long, DescribeMenuItemCategory> findCategoryTreeFromCache(Long categoryId) {
        if (categoryTreeCache.isEmpty()) {
            return null;
        }
        for (Tree<Long, DescribeMenuItemCategory> categoryTree : categoryTreeCache) {
            DescribeMenuItemCategory menuItemCategory = categoryTree.get(categoryId);
            if (menuItemCategory != null) {
                return categoryTree;
            }
        }
        return null;
    }

    private Tree<Long, DescribeMenuItemCategory> findCategoryTree(Long categoryId) {
        Tree<Long, DescribeMenuItemCategory> categoryTree = findCategoryTreeFromCache(categoryId);
        if (categoryTree == null) {
            categoryTree = menuItemCategoryManager.getTree(categoryId);
            categoryTreeCache.add(categoryTree);
        }
        return categoryTree;
    }
}
