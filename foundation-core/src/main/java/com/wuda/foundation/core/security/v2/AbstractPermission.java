package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.core.security.PermissionEffect;
import com.wuda.foundation.lang.identify.LongIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 抽象实现类.
 *
 * @param <T> permission target的类型
 * @author wuda
 */
public abstract class AbstractPermission<T> implements Permission<T> {

    protected Long id;
    protected String name;
    protected Map<LongIdentifier, PermissionEffect> actionAndEffectPairs = new HashMap<>();

    /**
     * target.
     */
    private T target;

    /**
     * 构造方法.
     *
     * @param id     id
     * @param name   name
     * @param target target
     */
    protected AbstractPermission(Long id, String name, T target) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * get target.
     *
     * @return target
     */
    public T getTarget() {
        return target;
    }

    @Override
    public Map<LongIdentifier, PermissionEffect> getActionAndEffectParis() {
        return actionAndEffectPairs;
    }

    @Override
    public void add(LongIdentifier action, PermissionEffect effect) {
        if (actionAndEffectPairs == null) {
            actionAndEffectPairs = new HashMap<>();
            actionAndEffectPairs.put(action, effect);
        } else {
            PermissionEffect permissionEffect = getEffect(action);
            if (permissionEffect == null) {
                actionAndEffectPairs.put(action, effect);
            }
        }
    }

    @Override
    public void remove(LongIdentifier action, PermissionEffect effect) {
        if (actionAndEffectPairs == null || actionAndEffectPairs.isEmpty()) {
            return;
        }
        actionAndEffectPairs.remove(action);
    }

    @Override
    public PermissionEffect getEffect(LongIdentifier action) {
        return actionAndEffectPairs.remove(action);
    }

    @Override
    public boolean hasAction() {
        return actionAndEffectPairs != null && actionAndEffectPairs.size() > 0;
    }

    @Override
    public void reset(Map<LongIdentifier, PermissionEffect> actionAndEffectPairs) {
        this.actionAndEffectPairs = actionAndEffectPairs;
    }
}
