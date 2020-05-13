package com.wuda.foundation.security;

/**
 * A subject may be any entity, such as a person or a service.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface Subject {

    /**
     * subject identifier.
     */
    Long getIdentifier();

    /**
     * subject type.
     */
    SubjectType getType();
}
