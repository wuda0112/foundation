package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.Map;

/**
 * 在最通用的情况下,Permission应该包含{@link FlatPermissionTarget target},
 * {@link PermissionAction action},{@link PermissionEffect access}.
 * 比如对于Windows文件系统的权限,target是文件(文件夹),action是read/write,access是allow or deny.
 *
 * @param <T> permission target的类型
 */
public interface Permission<T> {

    /**
     * 唯一标记.
     *
     * @return id
     */
    Long getId();

    /**
     * 名称.
     *
     * @return 名称
     */
    String getName();

    /**
     * permission作用的目标对象
     *
     * @return target
     */
    T getTarget();

    /**
     * get actionAndEffectPairs.
     *
     * @return actionAndEffectPairs
     */
    Map<LongIdentifier, PermissionEffect> getActionAndEffectParis();

    /**
     * 新增action and effect pair.如果指定action存在,则没有任何影响,如果不存在的新增.
     *
     * @param action action
     * @param effect effect
     */
    void add(LongIdentifier action, PermissionEffect effect);

    /**
     * 如果存在指定的action and effect pair,则删除,如果没有这样的pair,则没有任何影响.
     *
     * @param action action
     * @param effect effect
     */
    void remove(LongIdentifier action, PermissionEffect effect);

    /**
     * 获取access.
     *
     * @param action action
     * @return access
     */
    PermissionEffect getEffect(LongIdentifier action);

    /**
     * 该permission中是否有action.
     *
     * @return <code>true</code>-如果有
     */
    boolean hasAction();

    /**
     * 清除掉已有的action and effect pairs,使用当前提供的替换.如果新提供的action and effect pairs为<code>null</code>或者为空,
     * 则间接的完成了删除所有这样的功能.
     *
     * @param actionAndEffectPairs action and effect pairs
     */
    void reset(Map<LongIdentifier, PermissionEffect> actionAndEffectPairs);

}
