package com.wuda.foundation.core.user;

import com.wuda.foundation.core.commons.DescribeMenuItemCore;
import com.wuda.foundation.core.security.BuiltinRole;
import com.wuda.foundation.core.security.DescribePermissionAssignment;
import com.wuda.foundation.core.security.DescribePermissionRole;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.List;

/**
 * 用户与组的关系的管理.
 * 在组中, you can use roles to assign permissions to members of your group.
 * There are 3 default roles for every group, and those roles can’t be removed.
 * You can also create custom roles.
 * You can modify the permissions for default and custom roles for every group.
 * Permissions determine who can view, post, and moderate content and manage members
 * and settings in the group.
 * <p>
 * 三种默认的角色是
 * <ul>
 * <li>Owner,拥有Owner角色的成员,可以完全管理这个组,使用{@link BuiltinRole#USER_BELONGS_TO_GROUP_STORE_OWNER}表示</li>
 * <li>Manager,By default, managers can do everything that owners can do except:
 * <ul>
 * <li>Delete the group.</li>
 * <li>Make another member an owner.</li>
 * <li>Change an owner’s role to manager or member.使用{@link BuiltinRole#USER_BELONGS_TO_GROUP_STORE_MANAGER}表示</li>
 * </ul>
 * </li>
 * <li>Member,Everyone in a group has the member role.
 * Any permissions that are set for the member role are automatically given to managers and owners.
 * 使用{@link BuiltinRole#USER_BELONGS_TO_GROUP_STORE_MEMBER}表示
 * </li>
 * </ul>
 *
 * @author wuda
 * @since 1.0.3
 */
public interface UserBelongsToGroupManager {

    /**
     * 把用户从组中移除.
     *
     * @param request  request
     * @param opUserId 操作人用户ID
     */
    void removeUserFromGroup(RemoveUserFromGroupRequest request, Long opUserId);

    /**
     * 创建用户在组中的基本信息.
     *
     * @param request    请求参数
     * @param createMode 创建记录的模式
     * @param opUserId   操作人用户ID
     * @return 创建结果
     */
    CreateResult createUserBelongsToGroupGeneral(CreateUserBelongsToGroupGeneralRequest request, CreateMode createMode, Long opUserId);

    /**
     * 创建用户在组中的基本信息.
     *
     * @param request  请求参数
     * @param opUserId 操作人用户ID
     */
    void updateUserBelongsToGroupGeneral(UpdateUserBelongsToGroupGeneralRequest request, Long opUserId);

    /**
     * 获取用户在组中的基本信息
     *
     * @param userId user id
     * @param group  group
     * @return 用户在组中的基本信息
     */
    DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneral(Long userId, LongIdentifier group);

    /**
     * 创建用户在组中的角色.
     *
     * @param request    请求参数
     * @param createMode 创建记录的模式
     * @param opUserId   操作人用户ID
     * @return 创建结果
     */
    CreateResult createUserBelongsToGroupRole(CreateUserBelongsToGroupRoleRequest request, CreateMode createMode, Long opUserId);

    /**
     * 移除用户在组中的角色.
     *
     * @param request  request
     * @param opUserId 操作人用户ID
     */
    void removeUsersRoleFromGroup(RemoveUsersRoleFromGroupRequest request, Long opUserId);

    /**
     * 获取用户在组中的角色信息
     *
     * @param userId user id
     * @param group  group
     * @return 用户在组中的角色信息
     */
    DescribeUserBelongsToGroupRole getUserBelongsToGroupRole(Long userId, LongIdentifier group);

    /**
     * 获取用户所属的所有组.
     *
     * @param userId user id
     * @return 该用户所在的所有的组
     */
    List<LongIdentifier> getGroups(Long userId);

    /**
     * 获取组中的所有成员.
     *
     * @param group group
     * @return 组中的所有成员
     */
    List<Long> getMembers(LongIdentifier group);

    /**
     * 获取用户在给定组中通过角色获得的所有权限.
     *
     * @param userId 用户ID
     * @param group  组
     * @return 权限的集合
     */
    List<DescribePermissionAssignment> getPermissionsFromRole(Long userId, LongIdentifier group);

    /**
     * 获取用户在给定组中的所有角色.
     *
     * @param userId user id
     * @param group  group
     * @return 所有角色
     */
    List<DescribePermissionRole> getRoles(Long userId, LongIdentifier group);

    /**
     * 获取用户在给定组中通过角色获得的所有操作.
     *
     * @param userId user id
     * @param group  group
     * @return 操作的集合
     */
    List<DescribeMenuItemCore> getMenuItemsFromRole(Long userId, LongIdentifier group);
}
