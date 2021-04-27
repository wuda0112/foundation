package com.wuda.foundation.core.security.impl.menu;

import com.wuda.foundation.core.commons.DescribeMenuNode;
import com.wuda.foundation.core.commons.Menu;
import com.wuda.foundation.core.commons.MenuManager;
import com.wuda.foundation.core.security.Action;
import com.wuda.foundation.core.security.menu.MenuItemAndCategoryComparator;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAndCategoryComparatorImpl implements MenuItemAndCategoryComparator {

    private MenuManager menuManager;

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    private List<Menu> menusCache = new ArrayList<>();

    @Override
    public boolean support(IdentifierType type) {
        return type.equals(BuiltinIdentifierType.TABLE_MENU_ITEM_CATEGORY)
                || type.equals(BuiltinIdentifierType.FOUNDATION_MENU_ITEM);
    }

    @Override
    public boolean comparable(Action first, Action second) {
        return true;
    }

    @Override
    public boolean implies(Action first, Action second) {
        if (!isMenuItem(first) && !isMenuItemCategory(first)
                && !isMenuItem(second) && !isMenuItemCategory(second)) {
            throw new UnsupportedOperationException();
        }
        Long firstValue = first.getValue();
        Long secondValue = second.getValue();
        if (isMenuItem(first)) {
            // 已经是原子的实体,不可能包含其他实体了
            // todo 是否支持virtual
            return false;
        } else if (isMenuItemCategory(first)) {
            Menu menu = findMenuByCategory(firstValue);
            return menu.isDescendant(firstValue, secondValue);
        }
        throw new UnsupportedOperationException();
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
        return action.getType().equals(BuiltinIdentifierType.FOUNDATION_MENU_ITEM);
    }

    private boolean isMenuItemCategory(Action action) {
        return action.getType().equals(BuiltinIdentifierType.TABLE_MENU_ITEM_CATEGORY);
    }

    private Menu findMenuByCategoryFromCache(Long categoryId) {
        if (menusCache.isEmpty()) {
            return null;
        }
        for (Menu menu : menusCache) {
            DescribeMenuNode describeMenuNode = menu.get(categoryId);
            if (describeMenuNode != null) {
                return menu;
            }
        }
        return null;
    }

    private Menu findMenuByCategory(Long categoryId) {
        Menu menu = findMenuByCategoryFromCache(categoryId);
        if (menu == null) {
            long menuId = menuManager.getMenuId(categoryId);
            menu = menuManager.getMenu(menuId);
            menusCache.add(menu);
        }
        return menu;
    }
}
