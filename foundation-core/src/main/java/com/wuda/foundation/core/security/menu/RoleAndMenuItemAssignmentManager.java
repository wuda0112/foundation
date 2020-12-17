package com.wuda.foundation.core.security.menu;

import com.wuda.foundation.core.commons.DescribeMenuItemCore;
import com.wuda.foundation.core.commons.Menu;
import com.wuda.foundation.core.security.*;

import java.util.List;

/**
 * 一种具体的权限分配,为{@link DescribePermissionRole role}分配{@link DescribeMenuItemCore menu item},
 * 即{@link DescribePermissionRole role}作为{@link Subject},至于{@link DescribeMenuItemCore menu item}
 * 是作为{@link Target}还是{@link Action},则由具体实现类决定.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface RoleAndMenuItemAssignmentManager {

    /**
     * 分配menu item category给role.
     *
     * @param permissionRoleId   permission role id
     * @param menuId             menu id
     * @param menuItemCategoryId menu item category id
     * @param allowOrDeny        该role对menu item category的权限是允许还是拒绝.
     * @param opUserId           操作人用户ID
     */
    void assignMenuItemCategoryToRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, AllowOrDeny allowOrDeny, Long opUserId);

    /**
     * 清除分配给role的menu item category.清理后,两个实体之间就没有了任何联系.
     *
     * @param permissionRoleId   permission role id
     * @param menuId             menu id
     * @param menuItemCategoryId menu item category id
     * @param opUserId           操作人用户ID
     */
    void clearAssignmentBetweenMenuItemCategoryAndRole(Long permissionRoleId, Long menuId, Long menuItemCategoryId, Long opUserId);

    /**
     * 获取role对给定的menu item category的permission.
     *
     * @param permissionRoleId   permission role id
     * @param menuId             menu id
     * @param menuItemCategoryId menu item category id
     * @return permission assignment
     */
    DescribePermissionAssignment getMenuItemCategoryPermission(Long permissionRoleId, Long menuId, Long menuItemCategoryId);

    /**
     * 分配menu item给role.
     *
     * @param permissionRoleId permission role id
     * @param menuId           menu id
     * @param menuItemId       menu item  id
     * @param allowOrDeny      该role对menu item的权限是允许还是拒绝.
     * @param opUserId         操作人用户ID
     */
    void assignMenuItemToRole(Long permissionRoleId, Long menuId, Long menuItemId, AllowOrDeny allowOrDeny, Long opUserId);

    /**
     * 清除分配给role的menu item.清理后,两个实体之间就没有了任何联系.
     *
     * @param permissionRoleId permission role id
     * @param menuItemId       menu item  id
     * @param opUserId         操作人用户ID
     */
    void clearAssignmentBetweenMenuItemAndRole(Long permissionRoleId, Long menuId, Long menuItemId, Long opUserId);

    /**
     * 获取role对给定的menu item的permission.
     *
     * @param permissionRoleId permission role id
     * @param menuId           menu id
     * @param menuItemId       menu item  id
     * @return permission assignment
     */
    DescribePermissionAssignment getMenuItemPermission(Long permissionRoleId, Long menuId, Long menuItemId);

    /**
     * 获取为role分配的菜单的permission.
     *
     * @param permissionRoleId permission role id
     * @param menuId           menu id
     * @return permission assignment
     */
    List<MergedPermissionAssignment> getMenuPermissionAssignments(Long permissionRoleId, Long menuId);

    /**
     * 获取菜单,但是不一定是完整的,只返回许可的菜单项.
     *
     * @param permissionRoleId permission role id
     * @param menuId           menu id
     * @return 有权限访问的菜单项
     */
    Menu getPermittedMenu(Long permissionRoleId, Long menuId);

}
