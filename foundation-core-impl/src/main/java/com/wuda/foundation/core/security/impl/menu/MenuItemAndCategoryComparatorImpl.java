package com.wuda.foundation.core.security.impl.menu;

import com.wuda.foundation.core.commons.MenuItemCategoryManager;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.Action;
import com.wuda.foundation.core.security.menu.MenuItemAndCategoryComparator;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.tree.Tree;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemAndCategoryComparatorImpl implements MenuItemAndCategoryComparator {
    private MenuManager menuManager;
    private MenuItemCategoryManager menuItemCategoryManager;

    private List<Tree> categoryTrees;

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
            Tree categoryTree = findCategoryTree(categoryTrees, firstValue);
            if (isMenuItem(second)) {
                List<Triple<Long, Long, Long>> menuAndCategoryList = menuManager.getMenuAndCategory(Collections.singletonList(secondValue));
                List<Long> categoryIds = getCategoryIds(menuAndCategoryList);
                return hasDescendant(categoryTree, firstValue, categoryIds);
            } else if (isMenuItemCategory(second)) {
                List<Long> categoryIds = Collections.singletonList(secondValue);
                return hasDescendant(categoryTree, firstValue, categoryIds);
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

    private Tree findCategoryTree(List<Tree> categoryTrees, Long categoryId) {
        return null;
    }

    private boolean isDescendant(Tree categoryTree, Long up, Long down) {
        return false;
    }

    private boolean hasDescendant(Tree categoryTree, Long up, List<Long> downList) {
        return false;
    }

    private List<Long> getCategoryIds(List<Triple<Long, Long, Long>> menuAndCategoryList) {
        if (menuAndCategoryList == null || menuAndCategoryList.isEmpty()) {
            return new ArrayList<>(1);
        }
        return menuAndCategoryList.stream().map(Triple::getRight).collect(Collectors.toList());
    }
}
