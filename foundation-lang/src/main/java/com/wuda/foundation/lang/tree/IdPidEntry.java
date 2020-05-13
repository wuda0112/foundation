package com.wuda.foundation.lang.tree;

/**
 * 用ID/PID模型表示父子关系的数据.
 * 比如经常看到数据库中的产品分类,文章分类等数据都是ID/PID模型表示上下级关系.
 *
 * @param <T> ID和PID的数据类型
 * @author wuda
 * @since 1.0.0
 */
public interface IdPidEntry<T extends Comparable<T>> {

    /**
     * 获取id.
     *
     * @return id
     */
    T getId();

    /**
     * 获取pid.
     *
     * @return parent id
     */
    T getPid();
}
