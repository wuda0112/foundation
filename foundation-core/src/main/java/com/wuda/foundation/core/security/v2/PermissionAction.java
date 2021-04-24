package com.wuda.foundation.core.security.v2;

/**
 * 在权限体系中,Action是主体对于目标对象所执行的动作,比read/write等等.
 *
 * @author wuda
 */
public interface PermissionAction {

    /**
     * unique action name.比如read/write.
     *
     * @return name
     */
    String getName();

}
