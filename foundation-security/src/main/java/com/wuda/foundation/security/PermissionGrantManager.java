package com.wuda.foundation.security;

import java.util.List;
import java.util.Set;

/**
 * 类似于MySQL的Grant语法,这里就是grant action on target to {@link Subject}.
 * 如果只为{@link Subject}分配{@link DescribePermissionTarget},而没有分配{@link DescribePermissionAction},那我们也
 * 就只保存{@link Subject}与{@link DescribePermissionTarget}的关系,而没有{@link Subject}与{@link DescribePermissionAction}的关联关系,
 * 如果为{@link Subject}分配了{@link DescribePermissionAction},则一定也分配了{@link DescribePermissionTarget},因为{@link DescribePermissionAction}
 * 唯一属于一个{@link DescribePermissionTarget},那么我们就保存{@link Subject}与{@link DescribePermissionTarget}和{@link DescribePermissionAction}
 * 的关系,即对于{@link Subject}来说,分配了什么就有什么,也就是
 * <ul>
 * <li>分配了目标对象,却没有分配该对象的动作</li>
 * <li>分配了目标对象的动作,自然就被分配了该目标对象</li>
 * </ul>
 * ,你可能会问,只保存{@link Subject}与{@link DescribePermissionTarget}的关系,
 * 而不保存与{@link DescribePermissionAction}的关系,就好像是用户拥有这个目标对象,却不能做任何事情的感觉,那有什么意义呢?
 * 只能说这是具体应用才知道的,具体应用才知道他这样保存的意义,比如只为用户分配目标对象,而不分配允许的动作,
 * 是否就代表拥有这个目标对象的所有操作权限呢?这个是具体应用决定的,我们这里只负责帮它保存数据,维护好数据关系即可.
 * 如果说一定要找出一个理由,那么就是:在一开始的业务中,目标对象可能确实就没有声明有哪些操作,这个时候,
 * 只需要保存{@link Subject}与目标对象的关系,但是随着业务变化,这个{@link DescribePermissionTarget}才慢慢开始有{@link DescribePermissionAction}
 * <p>
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionGrantManager {

    /**
     * grant permission on target to {@link Subject}.给{@link Subject}授权给定的target,
     * 该target上的所有action都授权给了{@link Subject}.
     *
     * @param subject     subject
     * @param targetIdSet target id set
     * @param opUserId    操作人用户ID
     */
    void grantTarget(Subject subject, Set<Long> targetIdSet, Long opUserId);

    /**
     * grant permission on action to {@link Subject}.给{@link Subject}授权给定的action.
     *
     * @param subject     subject
     * @param targetId    target id
     * @param actionIdSet action id set,这些action必须属于给定的target,不然会造成数据混乱
     * @param opUserId    操作人用户ID
     */
    void grantAction(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId);

    /**
     * 取消{@link Subject}对target的权限,该target上的所有action都会被取消.
     *
     * @param subject     subject
     * @param targetIdSet target id set
     * @param opUserId    操作人用户ID
     */
    void revokeTarget(Subject subject, Set<Long> targetIdSet, Long opUserId);

    /**
     * 取消{@link Subject}拥有的action的权限.
     *
     * @param subject     subject
     * @param targetId    target id
     * @param actionIdSet action id set,这些action必须属于给定的target,不然会造成数据混乱
     * @param opUserId    操作人用户ID
     */
    void revokeAction(Subject subject, Long targetId, Set<Long> actionIdSet, Long opUserId);

    /**
     * 获取{@link Subject}的所有分配的permission.
     *
     * @param subject subject
     * @return 该 {@link Subject} 的permission
     */
    List<DescribePermission> getPermissions(Subject subject);

    /**
     * 获取{@link Subject}的所有分配的permission.
     *
     * @param subjects list of subject
     * @return 这些 {@link Subject} 的permission
     */
    List<DescribePermission> getPermissions(List<Subject> subjects);
}
