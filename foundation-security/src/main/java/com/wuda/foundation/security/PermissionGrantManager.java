package com.wuda.foundation.security;

import java.util.List;
import java.util.Set;

/**
 * 类似于MySQL的Grant语法,这里是为{@link Subject}分配{@link Target}和{@link Action},
 * 但是这种分配不一定是拥有,也可以是排除,主要看分配是使用的{@link InclusionOrExclusion}.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionGrantManager {

    /**
     * 为{@link Subject}分配{@link Target}.
     *
     * @param subject              subject
     * @param targetSet            target set
     * @param inclusionOrExclusion {@link Subject}不一定是拥有{@link Target},也有可能是排除
     * @param opUserId             操作人用户ID
     */
    void createAssignment(Subject subject, Set<Target> targetSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId);

    /**
     * grant permission on action to {@link Subject}.给{@link Subject}授权给定的action.
     *
     * @param subject              subject
     * @param target               target
     * @param actionSet            action set,这些action必须属于给定的target,不然会造成数据混乱
     * @param inclusionOrExclusion {@link Subject}不一定是拥有{@link Action},也有可能是排除
     * @param opUserId             操作人用户ID
     */
    void createAssignment(Subject subject, Target target, Set<Action> actionSet, InclusionOrExclusion inclusionOrExclusion, Long opUserId);

    /**
     * 清除分配给{@link Subject}的{@link Target},分配给{@link Subject}的该{@link Target}上的所有{@link Action}都会被清除.
     * clear assignment与{@link InclusionOrExclusion#EXCLUSION}的区别,clear assignment是完全清除掉实体之间的关联,清理后,
     * 实体之间相互独立,没有了任何关联信息.{@link InclusionOrExclusion#EXCLUSION}是实体之间建立关联,只是这种关联表明{@link Subject}
     * 排除{@link Target}和{@link Action}.比如有如下文件夹结构
     * <pre>
     *     a
     *     - b
     *     - - c
     * </pre>
     * 假设为用户A分配了文件夹a的所有权限,这时用户A自然也拥有了文件夹b和c的所有权限,但是
     * <ul>
     * <li>如果想排除掉文件夹b和c呢,这时只需要为用户A分配文件夹b,但是使用{@link InclusionOrExclusion#EXCLUSION}即可排除.</li>
     * <li>如果是清除用户A和文件夹a的关系,则使用clear assignment,这时用户A和文件夹a就没有了任何关系</li>
     * </ul>
     * 所有,它们之间是有本质区别的,使用时应该注意.
     *
     * @param subject   subject
     * @param targetSet target set
     * @param opUserId  操作人用户ID
     */
    void clearAssigment(Subject subject, Set<Target> targetSet, Long opUserId);

    /**
     * 清除分配给{@link Subject}的{@link Action}.更丰富的描述,查看{@link #clearAssigment(Subject, Set, Long)}.
     *
     * @param subject   subject
     * @param target    target
     * @param actionSet action  set,这些action必须属于给定的target,不然会造成数据混乱
     * @param opUserId  操作人用户ID
     */
    void clearAssigment(Subject subject, Target target, Set<Action> actionSet, Long opUserId);

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

    /**
     * 检查是否已经为{@link Subject}分配了{@link Target}.
     *
     * @param subject subject
     * @param target  target
     * @param inclusionOrExclusion inclusionOrExclusion
     * @return <code>true</code>-如果是
     */
    boolean subjectTargetAssigned(Subject subject, Target target, InclusionOrExclusion inclusionOrExclusion);
}
