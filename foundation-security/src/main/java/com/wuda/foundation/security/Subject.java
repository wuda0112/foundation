package com.wuda.foundation.security;

import com.wuda.foundation.lang.Identifier;

/**
 * A subject may be any entity, such as a person or a service.
 * 表示拥有permission的任何实体.
 *
 * @param <T> subject的唯一标记的类型
 * @author wuda
 * @since 1.0.0
 */
public interface Subject<T> extends Identifier<T> {
    /**
     * subject type.
     */
    SubjectType getType();
}
