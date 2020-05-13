package com.wuda.foundation.security;

import com.wuda.foundation.lang.utils.OrderBy;
import com.wuda.foundation.lang.utils.Pagination;

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
     * 新增一个permission.
     *
     * <strong>注意</strong>该方法没有事物,如果需要事物,调用者必须自己提供事物支持.
     *
     * @param target  该permission作用的对象
     * @param actions 该permission允许的动作,可以为空
     * @return permission target id
     */
    long addPermission(PermissionTarget target, Set<PermissionAction> actions);

    /**
     * 为给定的permission target追加action,如果action已经存在,则不重复添加.
     *
     * @param target  permission target
     * @param actions action set
     */
    void appendAction(PermissionTarget target, Set<PermissionAction> actions);

    /**
     * 为给定的permission target追加action.更多描述查看{@link #appendAction(PermissionTarget, Set)}.
     *
     * @param permissionTargetId permission target id
     * @param actions            action set
     */
    void appendAction(Integer permissionTargetId, Set<PermissionAction> actions);

    /**
     * 删除指定的action.
     *
     * @param permissionActionId permission action id
     */
    void deleteAction(Integer permissionActionId);

    /**
     * 删除permission target,属于该permission target的action也必须被删除.
     *
     * @param permissionTargetId permission target id
     */
    void deleteTarget(Integer permissionTargetId);

    /**
     * 更新permission target.
     *
     * @param permissionTargetId permission target id
     * @param name               permission target name
     * @param description        描述信息
     */
    void updatePermissionTarget(Integer permissionTargetId, String name, String description);

    /**
     * 更新permission action.
     *
     * @param permissionActionId permission action id
     * @param action             permission action
     * @param description        描述信息
     */
    void updatePermissionAction(Integer permissionActionId, String action, String description);

    /**
     * 获取permission.
     *
     * @param permissionTargetId permission target id
     * @return a permission
     */
    Permission getPermission(Integer permissionTargetId);

    /**
     * 获取permission.
     *
     * @param permissionTargetIds list of permission target id
     * @return list of permission
     */
    List<Permission> getPermission(List<Integer> permissionTargetIds);

    /**
     * 获取permission.
     *
     * @param targetType     permission target关联的外部对象的类型
     * @param pagination     分页
     * @param orderByColumns 排序
     * @return list of permission
     */
    List<Permission> getPermission(PermissionTargetType targetType, Pagination pagination, List<OrderBy> orderByColumns);

    /**
     * 获取permission.
     *
     * @param categoryId     permission target所属分类的ID
     * @param pagination     分页
     * @param orderByColumns 排序
     * @return list of permission
     */
    List<Permission> getPermission(Integer categoryId, Pagination pagination, List<OrderBy> orderByColumns);

}
