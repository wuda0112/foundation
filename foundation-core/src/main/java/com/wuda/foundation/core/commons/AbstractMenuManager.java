package com.wuda.foundation.core.commons;

import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

import java.util.List;

public abstract class AbstractMenuManager implements MenuManager {

    @Override
    public CreateResult createMenuItemCore(CreateMenuItemCore createMenuItemCore, CreateMode createMode) {
        return createMenuItemCoreDbOp(createMenuItemCore, createMode);
    }

    protected abstract CreateResult createMenuItemCoreDbOp(CreateMenuItemCore createMenuItemCore, CreateMode createMode);

    @Override
    public void updateMenuItemCore(UpdateMenuItemCore updateMenuItemCore) {
        updateMenuItemCoreDbOp(updateMenuItemCore);
    }

    protected abstract void updateMenuItemCoreDbOp(UpdateMenuItemCore updateMenuItemCore);

    @Override
    public void addItemToCategory(Long menuItemId, Long menuItemCategoryId, Long opUserId) {
        addItemToCategoryDbOp(menuItemId, menuItemCategoryId, opUserId);
    }

    protected abstract void addItemToCategoryDbOp(Long menuItemId, Long menuItemCategoryId, Long opUserId);

    @Override
    public void removeItemFromCategory(Long menuItemId, Long menuItemCategoryId, Long opUserId) {
        removeItemFromCategoryDbOp(menuItemId, menuItemCategoryId, opUserId);
    }

    protected abstract void removeItemFromCategoryDbOp(Long menuItemId, Long menuItemCategoryId, Long opUserId);

    @Override
    public CreateResult createMenuCore(CreateMenuCore createMenuCore, CreateMode createMode) {
        return createMenuCoreDbOp(createMenuCore, createMode);
    }

    protected abstract CreateResult createMenuCoreDbOp(CreateMenuCore createMenuCore, CreateMode createMode);

    @Override
    public List<DescribeMenuItemCore> getMenuItemsFromRole(List<Long> roleIds) {
        return getMenuItemsFromRoleDbOp(roleIds);
    }

    protected abstract List<DescribeMenuItemCore> getMenuItemsFromRoleDbOp(List<Long> roleIds);

    @Override
    public List<DescribeMenuItemCore> getMenuItemsById(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return getMenuItemsByIdDbOp(ids);
    }

    protected abstract List<DescribeMenuItemCore> getMenuItemsByIdDbOp(List<Long> ids);

    @Override
    public Menu getMenu(Long menuId) {
        return getMenuDbOp(menuId);
    }

    protected abstract Menu getMenuDbOp(Long menuId);

    @Override
    public DescribeMenuCore getMenuCore(Long menuId) {
        return getMenuCoreDbOp(menuId);
    }

    protected abstract DescribeMenuCore getMenuCoreDbOp(Long menuId);

    @Override
    public List<DescribeMenuItemCore> getMenuItemsByCategoryId(List<Long> menuItemCategoryIds) {
        return getMenuItemsByCategoryIdDbOp(menuItemCategoryIds);
    }

    protected abstract List<DescribeMenuItemCore> getMenuItemsByCategoryIdDbOp(List<Long> menuItemCategoryIds);

    @Override
    public List<DescribeMenuItemCategory> getMenuItemCategory(Long menuId) {
        return getMenuItemCategoryDbOp(menuId);
    }

    protected abstract List<DescribeMenuItemCategory> getMenuItemCategoryDbOp(Long menuId);

    @Override
    public Long getMenuId(Long menuItemCategoryId) {
        return getMenuIdDbOp(menuItemCategoryId);
    }

    protected abstract Long getMenuIdDbOp(Long menuItemCategoryId);

}
