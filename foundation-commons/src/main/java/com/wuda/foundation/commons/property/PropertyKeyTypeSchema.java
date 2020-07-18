package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.UniqueCodeDescriptorSchema;

/**
 * schema of {@link PropertyKeyType}.
 *
 * @author wuda
 * @since 1.0.0
 */
public class PropertyKeyTypeSchema implements UniqueCodeDescriptorSchema {

    /**
     * 该类只是一个占位符,不需要外部实例化.
     */
    private PropertyKeyTypeSchema() {

    }

    public final static PropertyKeyTypeSchema instance = new PropertyKeyTypeSchema();
}
