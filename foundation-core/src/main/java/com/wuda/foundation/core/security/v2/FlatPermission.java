package com.wuda.foundation.core.security.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 由于{@link FlatPermissionTarget}没有层级结构,因此最终的权限也是平铺的.
 *
 * @param <T> permission target的类型
 * @author wuda
 */
public class FlatPermission<T> extends AbstractPermission<T> {

    /**
     * 构造方法.
     *
     * @param id     id
     * @param name   name
     * @param target target
     */
    public FlatPermission(Long id, String name, T target) {
        super(id, name, target);
    }

    /**
     * 新建permission.
     *
     * @param targets                  targets
     * @param targetToPermissionMapper target转换成permission的映射器
     * @param <T>                      target类型
     * @return permissions
     */
    public static <T> List<FlatPermission<T>> newPermissions(List<T> targets,
                                                             Function<T, FlatPermission<T>> targetToPermissionMapper) {
        if (targets == null || targets.size() == 0) {
            return null;
        }
        List<FlatPermission<T>> list = new ArrayList<>(targets.size());
        for (T target : targets) {
            FlatPermission<T> flatPermission = targetToPermissionMapper.apply(target);
            list.add(flatPermission);
        }
        return list;
    }
}
