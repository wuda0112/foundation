package com.wuda.foundation.security;

import java.util.List;

/**
 * permission owner manager.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PermissionAssignmentManager {

    /**
     * 得到{@link Subject}的{@link DescribePermissionAssignment}.来自两部分
     * <ul>
     * <li>该{@link Subject}自身拥有的permission</li>
     * </ul>
     * 取它们的并集作为该{@link Subject}最终所拥有的permission.由于最终的结果是
     * 经过一些计算得出的,因此命名使用calculate做前缀,也是为了和{@link PermissionGrantManager#getPermission(Subject)}
     * 的定义区分开,以免引起混淆.
     *
     * @param subject subject
     * @return 该用户的permission
     */
    List<DescribePermissionAssignment> calculatePermission(Subject subject);

    /**
     * 描述一个{@link Subject}得到最终{@link DescribePermissionAssignment}的过程.可以用于测试.
     *
     * @param subject subject
     * @return {@link PermissionAssignmentExplain}
     */
    PermissionAssignmentExplain explain(Subject subject);
}
