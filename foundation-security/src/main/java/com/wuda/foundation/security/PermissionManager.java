package com.wuda.foundation.security;

import java.util.List;
import java.util.Set;

/**
 * permission 相关的数据访问对象.一些规则
 * <ul>
 * <li>如果permission target 没有permission,则表示该target拥有所有action,数据库中可以用一条特殊的action表示,也可以没有action记录</li>
 * </ul>
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionManager {

    /**
     * 新增permission target.
     *
     * @param target   target
     * @param opUserId 操作人用户ID
     * @return 新增的permission target id
     */
    long createPermissionTarget(CreatePermissionTarget target, Long opUserId);

    /**
     * 新增permission action.
     *
     * @param action   action
     * @param opUserId 操作人用户ID
     * @return 新增的permission action id
     */
    long createPermissionAction(CreatePermissionAction action, Long opUserId);

    /**
     * 新增一个permission.
     *
     * <strong>注意</strong>该方法没有事物,如果需要事物,调用者必须自己提供事物支持.
     *
     * @param target   该permission作用的对象
     * @param actions  该permission允许的动作,可以为空
     * @param opUserId 操作人用户ID
     * @return permission target id
     */
    long createPermission(CreatePermissionTarget target, Set<CreatePermissionAction> actions, Long opUserId);

    /**
     * 为给定的permission target追加action.为给定的permission target追加action,如果action已经存在,则不重复添加.
     *
     * @param opUserId 操作人用户ID
     * @param actions  action set
     */
    void createPermissionAction(Set<CreatePermissionAction> actions, Long opUserId);

    /**
     * 删除指定的action.
     *
     * @param opUserId           操作人用户ID
     * @param permissionActionId permission action id
     */
    void deleteAction(Long permissionActionId, Long opUserId);

    /**
     * 删除指定target的所有action.
     *
     * @param opUserId           操作人用户ID
     * @param permissionTargetId permission target id
     */
    void deleteActionByTarget(Long permissionTargetId, Long opUserId);

    /**
     * 删除permission target,属于该permission target的action也必须被删除.
     *
     * @param opUserId           操作人用户ID
     * @param permissionTargetId permission target id
     */
    void deleteTarget(Long permissionTargetId, Long opUserId);

    /**
     * 更新permission target.
     *
     * @param permissionTargetId permission target id
     * @param name               permission target name
     * @param description        描述信息
     * @param opUserId           操作人用户ID
     */
    void updatePermissionTarget(Long permissionTargetId, String name, String description, Long opUserId);

    /**
     * 更新permission action.
     *
     * @param permissionActionId permission action id
     * @param action             permission action
     * @param opUserId           操作人用户ID
     * @param description        描述信息
     */
    void updatePermissionAction(Long permissionActionId, String action, String description, Long opUserId);

    /**
     * 获取permission target.
     *
     * @param permissionTargetId permission target id
     * @return permission target
     */
    DescribePermissionTarget getPermissionTargetById(Long permissionTargetId);

    /**
     * 获取permission action.
     *
     * @param permissionActionId permission action id
     * @return permission action
     */
    DescribePermissionAction getPermissionActionById(Long permissionActionId);

    /**
     * 获取permission action.
     *
     * @param permissionTargetId permission target id
     * @return permission action
     */
    List<DescribePermissionAction> getPermissionActionByTarget(Long permissionTargetId);

    /**
     * 获取permission.
     *
     * @param permissionTargetId permission target id
     * @return a permission
     */
    Permission getPermission(Long permissionTargetId);

}
