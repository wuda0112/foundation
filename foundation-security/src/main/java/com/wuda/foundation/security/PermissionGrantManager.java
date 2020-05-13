package com.wuda.foundation.security;

import java.util.List;

/**
 * permission分为{@link PermissionTarget}和{@link PermissionAction}两部分,
 * 如果只为{@link Subject}分配{@link PermissionTarget},而没有分配{@link PermissionAction},那我们也
 * 就只保存{@link Subject}与{@link PermissionTarget}的关系,而没有{@link Subject}与{@link PermissionAction}的关联关系,
 * 如果为{@link Subject}分配了{@link PermissionAction},则一定也分配了{@link PermissionTarget},因为{@link PermissionAction}
 * 唯一属于一个{@link PermissionTarget},那么我们就保存{@link Subject}与{@link PermissionTarget}和{@link PermissionAction}
 * 的关系,即对于{@link Subject}来说,分配了什么就有什么,也就是
 * <ul>
 * <li>分配了目标对象,却没有分配该对象的动作</li>
 * <li>分配了目标对象的动作,自然就被分配了该目标对象</li>
 * </ul>
 * ,你可能会问,只保存{@link Subject}与{@link PermissionTarget}的关系,
 * 而不保存与{@link PermissionAction}的关系,就好像是用户拥有这个目标对象,却不能做任何事情的感觉,那有什么意义呢?
 * 只能说这是具体应用才知道的,具体应用才知道他这样保存的意义,比如只为用户分配目标对象,而不分配允许的动作,
 * 是否就代表拥有这个目标对象的所有操作权限呢?这个是具体应用决定的,我们这里只负责帮它保存数据,维护好数据关系即可.
 * 如果说一定要找出一个理由,那么就是:在一开始的业务中,目标对象可能确实就没有声明有哪些操作,这个时候,
 * 只需要保存{@link Subject}与目标对象的关系,但是随着业务变化,这个{@link PermissionTarget}才慢慢开始有{@link PermissionAction}
 * <p>
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionGrantManager {

    /**
     * 给{@link Subject}授权.
     *
     * @param request request
     */
    void grantPermission(SubjectPermissionGrantRequest request);

    /**
     * 取消授权.
     *
     * @param request request
     */
    void revokePermission(SubjectPermissionGrantRequest request);

    /**
     * 给{@link SubjectGroup}授权.
     *
     * @param request srequest
     */
    void grantPermission(SubjectGroupPermissionGrantRequest request);

    /**
     * 取消授权.
     *
     * @param request subject group
     */
    void revokeRolePermission(SubjectGroupPermissionGrantRequest request);

    /**
     * 获取{@link Subject}的{@link Permission}.
     *
     * @param subject subject
     * @return 该用户的permission
     */
    List<Permission> getPermission(Subject subject);

    /**
     * 获取{@link SubjectGroup}的permission.
     *
     * @param subjectGroup subject group
     * @return 该{@link SubjectGroup}的permission
     */
    List<Permission> getPermission(SubjectGroup subjectGroup);
}
