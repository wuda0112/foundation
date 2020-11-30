package com.wuda.foundation.user;

import com.wuda.foundation.commons.DescribeMenuItem;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.security.BuiltinRole;
import com.wuda.foundation.security.DescribePermission;
import com.wuda.foundation.security.DescribePermissionRole;

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
     * 创建用户与组关联的核心信息.
     *
     * @param request    请求参数
     * @param createMode 创建记录的模式
     * @param opUserId   操作人用户ID
     * @return 创建结果
     */
    CreateResult createUserBelongsToGroupCore(CreateUserBelongsToGroupCoreRequest request, CreateMode createMode, Long opUserId);

    /**
     * 把用户从组中移除.
     *
     * @param request  request
     * @param opUserId 操作人用户ID
     */
    void removeUserFromGroup(RemoveUserFromGroupRequest request, Long opUserId);

    /**
     * 获取用户与组关联的核心信息.
     *
     * @param id user belongs to group core id
     * @return 用户与组关联的核心信息.
     */
    DescribeUserBelongsToGroupCore getUserBelongsToGroupCore(Long id);

    /**
     * 什么是<strong>user belongs to group id</strong>呢?用户在组中的信息会分散到多个表中,
     * 比如用户ID与组ID关联的的信息肯定保存在用户与组的核心表中,即用{@link CreateUserBelongsToGroupCoreRequest}
     * 创建的记录,比如用户在组中的基本信息(比如用户在组中的昵称)保存在用户与组的基本信息的表中,
     * 即用{@link CreateUserBelongsToGroupGeneralRequest}创建的记录,等等.这些表的目的就是为了保存用户在组中的多种信息,
     * 那么这些表的信息如何关联起来呢,就是使用<strong>user belongs to group id</strong>这个column关联,
     * 也就是说所有处理用户在组中的信息的那些表中都有<strong>user belongs to group id</strong>这个字段.
     * 这就好比订单的信息会分开保存到多个表中,但是这些表中都会有个<strong>订单ID</strong>的字段,通过这个字段就可以把同一个订单的信息联系起来.
     *
     * @param userId user id
     * @param group  group
     * @return user belongs to group id
     */
    Long getUserBelongsToGroupId(Long userId, LongIdentifier group);

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
     * @param id user belongs to group general id
     * @return 用户在组中的基本信息
     */
    DescribeUserBelongsToGroupGeneral getUserBelongsToGroupGeneral(Long id);

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
     * @param id user belongs to group role id
     * @return 用户在组中的角色信息
     */
    DescribeUserBelongsToGroupRole getUserBelongsToGroupRole(Long id);

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
    List<DescribePermission> getPermissionsFromRole(Long userId, LongIdentifier group);

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
    List<DescribeMenuItem> getMenuItemsFromRole(Long userId, LongIdentifier group);
}
