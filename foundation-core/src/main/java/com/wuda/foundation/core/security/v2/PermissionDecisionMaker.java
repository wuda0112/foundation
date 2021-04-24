package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.tree.Tree;
import com.wuda.foundation.lang.tree.TreeNode;

import java.util.List;
import java.util.function.Function;

/**
 * 当分配权限后,决定主体最终拥有哪些权限.
 * 由于主体的权限有多种来源(比如用户的权限可以来源于用户本身,用户组,角色等),
 * 并且权限可以继承(有些应用可能不继承),因此需要处理合并,继承等规则,然后得到
 * 最终的权限.
 *
 * @author wuda
 */
public interface PermissionDecisionMaker {

    /**
     * 如果知道权限分配的信息和目标对象的结构,我们就可以知道主体对于该目标对象的访问权限.
     * 权限分配信息和目标对象都是有层级结构的.
     * 以对文件系统的访问权限控制为例,
     * <p>
     * 第一,假设有如下文件系统结构
     * <blockquote>
     * <pre>
     *         directory_a
     *         - - file_b
     *         - - file_c
     *         directory_d
     *         - - directory_e
     *         - - - - file_f
     *     </pre>
     * </blockquote>
     * </p>
     * <p>
     * 第二,假设为用户组分配的权限如下
     * <blockquote>
     * <pre>
     *         ugr_a
     *         - - ugr_b
     *         - - - - can read/write directory a
     *         - - - - can write file b
     *         - - - - ugr_c
     *         ugr_d
     *         - - can read/write directory e
     *         - - ugr_e
     *         - - - - ugr_f
     *     </pre>
     * </blockquote>
     * </p>
     * <p>
     * 第三,如果用户user_k既加入了用户组ugr_b,也加入了用户组ugr_d,请问用户user_k对于上述文件系统的权限是如何分布呢?
     * </p>
     * 这个方法就是为了解决这个问题的.在上面的例子中,文件系统就是权限作用的目标对象,
     * 然后我们又知道了权限分配信息,我们自然就可以决定用户user_k最终对于该文件系统的权限.
     *
     * @param permissionTargetTree     权限作用的目标对象,就好比上面例子中的文件系统
     * @param targetToPermissionMapper target转换成permission的映射器
     * @param permissionAssignmentTree 权限分配信息,就好比上面例子中为用户组分配的权限信息
     * @param <T>                      permission target的类型
     * @return 通过计算得到的最终权限
     */
    <T extends TreeNode<Long>> Tree<Long, HierarchicalPermission<T>> decide(Tree<Long, T> permissionTargetTree,
                                                                            Function<T, HierarchicalPermission<T>> targetToPermissionMapper,
                                                                            Tree<Long, HierarchicalPermissionAssignmentCollection> permissionAssignmentTree);

    /**
     * 详细描述请查看{@link #decide(Tree, Function, Tree)},当前方法只是{@link #decide(Tree, Function, Tree)}这个方法退化的一种情形.
     *
     * @param permissionTargetTree           权限作用的目标对象
     * @param targetToPermissionMapper       target转换成permission的映射器
     * @param permissionAssignmentCollection 权限分配信息
     * @param <T>                            permission target的类型
     * @return 通过计算得到的最终权限
     */
    <T extends TreeNode<Long>> Tree<Long, HierarchicalPermission<T>> decide(Tree<Long, T> permissionTargetTree,
                                                                            Function<T, HierarchicalPermission<T>> targetToPermissionMapper,
                                                                            PermissionAssignmentCollection permissionAssignmentCollection);

    /**
     * 详细描述请查看{@link #decide(Tree, Function, Tree)},当前方法只是{@link #decide(Tree, Function, Tree)}这个方法退化的一种情形.
     *
     * @param permissionTargets        权限作用的目标对象
     * @param targetToPermissionMapper target转换成permission的映射器
     * @param permissionAssignmentTree 权限分配信息
     * @param <T>                      permission target的类型
     * @return 通过计算得到的最终权限
     */
    <T> List<FlatPermission<T>> decide(List<T> permissionTargets,
                                       Function<T, FlatPermission<T>> targetToPermissionMapper,
                                       Tree<Long, HierarchicalPermissionAssignmentCollection> permissionAssignmentTree);

    /**
     * 详细描述请查看{@link #decide(Tree, Function, Tree)},当前方法只是{@link #decide(Tree, Function, Tree)}这个方法退化的一种情形.
     *
     * @param permissionTargets              权限作用的目标对象
     * @param targetToPermissionMapper       target转换成permission的映射器
     * @param permissionAssignmentCollection 权限分配信息
     * @param <T>                            permission target的类型
     * @return 通过计算得到的最终权限
     */
    <T> List<FlatPermission<T>> decide(List<T> permissionTargets,
                                       Function<T, FlatPermission<T>> targetToPermissionMapper,
                                       PermissionAssignmentCollection permissionAssignmentCollection);

    /**
     * 对于目标对象,之前我们已经知道了权限分布,假设现在又有新的权限分配信息,
     * 我们可以把新的权限分配信息应用到之前的权限上,从而得出新的权限分布.
     * 以对文件系统的访问权限控制为例,
     * <p>
     * 第一,假设对于用户组ugr_a,我们已经有如下的权限控制
     * <blockquote>
     * <pre>
     *         directory_a (can r/w)
     *         - - file_b (can r/w)
     *         - - file_c (can r/w)
     *         directory_d (can r/w)
     *         - - directory_e (can r/w)
     *         - - - - file_f (can r/w)
     *     </pre>
     * </blockquote>
     * </p>
     * <p>
     * 第二,假设用户组ugr_a又得到了新的权限分配
     * <blockquote>
     * <pre>
     *         ugr_a
     *         - - can not write file b
     *         - - ugr_b
     *         - - - - can read/write directory a
     *         - - - - ugr_c
     *     </pre>
     * </blockquote>
     * </p>
     * <p>
     * 当应用了新的权限分配信息后,请问用户ugr_a对于上述文件系统的权限是如何分布呢?
     * </p>
     * 这个方法就是为了解决这个问题的.在上面的例子中,如果应用了新的权限分配信息后,
     * 用户组ugr_a对于文件系统的访问权限分布如下
     * <blockquote>
     * <pre>
     *         directory_a (can r/w)
     *         - - file_b (<strong style="color:red;">can r</strong>)
     *         - - file_c (can r/w)
     *         directory_d (can r/w)
     *         - - directory_e (can r/w)
     *         - - - - file_f (can r/w)
     *     </pre>
     * </blockquote>
     *
     * @param permissionTree                 权限,就好比上面例子中对文件系统的访问权限
     * @param permissionAssignmentCollection 权限分配信息,就好比上面例子中为用户组分配的权限信息
     * @param <T>                            permission target的类型
     */
    <T extends TreeNode<Long>> void applyPermissionAssignment(Tree<Long, HierarchicalPermission<T>> permissionTree,
                                                              PermissionAssignmentCollection permissionAssignmentCollection);

    /**
     * 详细请查看{@link #applyPermissionAssignment(Tree, PermissionAssignmentCollection)},当前方法只是{@link #applyPermissionAssignment(Tree, PermissionAssignmentCollection)}
     * 的一种退化场景.
     *
     * @param permissions                    权限
     * @param permissionAssignmentCollection 权限分配信息
     * @param <T>                            permission target的类型
     */
    <T> void applyPermissionAssignment(List<FlatPermission<T>> permissions,
                                       PermissionAssignmentCollection permissionAssignmentCollection);


}
