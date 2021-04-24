package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用于描述权限的分配信息.
 *
 * @author wuda
 */
public class PermissionAssignment {
    /**
     * subject.
     */
    private LongIdentifier subject;
    /**
     * target.
     */
    private LongIdentifier target;
    /**
     * action.
     */
    private LongIdentifier action;
    /**
     * effect.
     */
    private PermissionEffect effect;
    /**
     * 权限可以多次重复分配,使用版本表示在哪次分配,版本值越大,表明分配的时间越靠后.
     * 使用版本的一个好处是每次只保存变化的部分,而所有变化的内容合在一起最终就是一个完整的权限分配.
     * 想象一下为用户分配菜单权限的场景,在一棵菜单树上,我们可以多次反复勾选、取消勾,然后保存.
     * 而每次可能只勾选了菜单的一小部分,这时,勾选了哪些我们就保存哪些,只保存本次有变化的部分,
     * 而不需要保存该用户对整个菜单的分配信息.我们在计算该用户对这个菜单的权限时,只需要把多次勾选/取消勾选的信息合并,
     * 就可以最终得到结果.
     * 就好像Git每次commit只记录变化的部分,但是无数次变化合在一起就可以得到最终正确的结果.
     */
    private long version;

    /**
     * 构造权限分配信息.
     *
     * @param subject subject
     * @param target  target
     * @param action  action
     * @param effect  effect
     * @param version version
     */
    public PermissionAssignment(LongIdentifier subject, LongIdentifier target, LongIdentifier action, PermissionEffect effect, long version) {
        this.subject = Objects.requireNonNull(subject);
        this.target = Objects.requireNonNull(target);
        this.action = Objects.requireNonNull(action);
        this.effect = Objects.requireNonNull(effect);
        this.version = version;
    }


    /**
     * get subject.
     *
     * @return subject
     */
    public LongIdentifier getSubject() {
        return subject;
    }

    /**
     * get target.
     *
     * @return target
     */
    public LongIdentifier getTarget() {
        return target;
    }

    /**
     * get action
     *
     * @return action
     */
    public LongIdentifier getAction() {
        return action;
    }

    /**
     * get effect.
     *
     * @return effect
     */
    public PermissionEffect getEffect() {
        return effect;
    }

    /**
     * get version
     *
     * @return version
     */
    public long getVersion() {
        return version;
    }

    /**
     * 相同的权限分配, 使用版本号更大的.什么是"相同的权限分配"呢? 那就是Subject,Target,Action组合在一起相同的权限分配,不包含Effect,
     * 查看{@link #getGroupKey()}方法的实现.对于一个Subject可以多次分配同一个对象的访问权限,之前分配的信息可以保留,但是如果多次分配,
     * 则使用版本更大的那个分配.比如
     * <ul>
     * <li>第一次, 为User A分配File A的Read是Allow,这次分配的版本是1</li>
     * <li>第二次, 为User A分配File A的Read是Deny,这次分配的版本是2</li>
     * <li>第三次, 为User A分配File A的Write是Deny,这次分配的版本是3</li>
     * <li>第四次, 为User A分配File A的Write是Allow,这次分配的版本是4</li>
     * <li>这个过程可以一直持续,但是版本会一直递增.</li>
     * </ul>
     * 有这么多条分配信息, 那么User A对于File A的最终权限是怎样呢？
     * 答案是
     * <ul>
     * <li>User A对于File A的Read操作是Deny,使用版本2的那次分配</li>
     * <li>User A对于File A的Write操作是Allow,使用版本4的那次分配</li>
     * </ul>
     *
     * @param multiVersionPermissionAssignments 包含多个版本的权限分配信息
     * @return 合并后的权限分配信息
     */
    public static List<PermissionAssignment> mergeUseGreaterVersion(List<PermissionAssignment> multiVersionPermissionAssignments) {
        Map<String, List<PermissionAssignment>> map = multiVersionPermissionAssignments.stream().collect(Collectors.groupingBy(PermissionAssignment::getGroupKey));
        Set<Map.Entry<String, List<PermissionAssignment>>> entrySet = map.entrySet();
        List<PermissionAssignment> greaterVersionAssignments = new ArrayList<>();
        for (Map.Entry<String, List<PermissionAssignment>> entry : entrySet) {
            List<PermissionAssignment> assignments = entry.getValue();
            long greaterVersion = 0;
            int greaterVersionIndex = 0;
            for (int i = 0; i < assignments.size(); i++) {
                PermissionAssignment permissionAssignment = assignments.get(i);
                if (permissionAssignment.getVersion() > greaterVersion) {
                    greaterVersion = permissionAssignment.getVersion();
                    greaterVersionIndex = i;
                }
            }
            PermissionAssignment greaterVersionAssignment = assignments.get(greaterVersionIndex);
            greaterVersionAssignments.add(greaterVersionAssignment);
        }
        return greaterVersionAssignments;
    }

    private String getGroupKey() {
        return getSubject() + ":" + getTarget() + ":" + getAction();
    }

}
