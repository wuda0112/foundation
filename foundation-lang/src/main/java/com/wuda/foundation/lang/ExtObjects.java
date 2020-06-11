package com.wuda.foundation.lang;

import java.util.Objects;

/**
 * {@link java.util.Objects}扩展类.
 *
 * @author wuda
 * @since 1.0.0
 */
public class ExtObjects {

    /**
     * Checks that all the specified object reference is not {@code null}
     *
     * @param objects objects
     */
    public static void requireNonNull(Object... objects) {
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }

}
