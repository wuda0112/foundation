package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 没有层次结构的Subject,比如用户.
 *
 * @author wuda
 */
public class FlatPermissionSubject extends AbstractPermissionSubject {

    /**
     * 构造实例.
     *
     * @param identifier  subject的唯一标记
     * @param name        name
     * @param description description
     */
    public FlatPermissionSubject(LongIdentifier identifier, String name, String description) {
        super(identifier, name, description);
    }
}
