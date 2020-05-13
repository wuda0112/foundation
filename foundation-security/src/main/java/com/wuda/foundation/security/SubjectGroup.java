package com.wuda.foundation.security;

/**
 * 表示一组{@link Subject}.如果{@link Subject}是用户,那么role就是一种类型的组.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface SubjectGroup {

    /**
     * subject group identifier.
     */
    Long getIdentifier();

    /**
     * subject group type.
     */
    SubjectGroupType getType();
}
