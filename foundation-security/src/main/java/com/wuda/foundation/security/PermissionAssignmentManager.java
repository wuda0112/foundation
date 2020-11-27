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
     * 得到{@link Subject}的permission.一个{@link Subject}可以被分配很多权限,
     * 取它们的并集作为该{@link Subject}最终所拥有的permission.由于最终的结果是
     * 经过一些计算得出的,因此命名使用calculate做前缀,也是为了和{@link PermissionGrantManager#getPermissions(Subject)}
     * 的定义区分开,以免引起混淆.
     *
     * @param subject subject
     * @return 该 {@link Subject} 的permission
     */
    List<DescribePermission> calculatePermission(Subject subject);

    /**
     * 描述一个{@link Subject}得到最终{@link DescribePermissionAssignment}的过程.可以用于测试.
     *
     * @param subject subject
     * @return {@link PermissionAssignmentExplain}
     */
    PermissionAssignmentExplain explain(Subject subject);

    /**
     * 获取{@link Target}所拥有的所有{@link Action}.
     *
     * @param target 目标对象
     * @return 该target的所有的action
     */
    List<Action> getActions(Target target);

    /**
     * 获取某一类{@link Target}所拥有的所有{@link Action}.
     * 比如假设{@link Target}的类型是file,则返回file的所有操作,比如read/write.
     *
     * @param targetType 一类{@link Target}
     * @return 该permission target的所有action
     */
    List<Action> getActions(Target.Type targetType);

    /**
     * @param container action container. {@link Action}不一定代表一个具体的动作,也可以代表一类动作,
     *                  就好比{@link Target}可以代表file,也可以代表folder. 更有说服力的是{@link java.io.File}
     *                  既代表file,也可以代表directory.
     * @return 该action所代表的一类action
     */
    List<Action> getActions(Action container);

    /**
     * 获取容器类型的{@link Target}所拥有的所有元素.
     * 比如假设{@link Target}是文件夹,则返回这个文件夹下的所有文件和子文件夹.
     *
     * @param container 该类型的{@link Target}是容器类型,比如文件夹
     * @return 该permission target下的所有元素
     */
    List<Target> getTargets(Target container);
}
