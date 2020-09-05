package com.wuda.foundation.commons.property;

import java.util.List;

/**
 * 表示多个{@link PropertyTemplate}.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PropertyTemplates {

    /**
     * 返回所有的template.
     *
     * @return templates
     */
    List<? extends PropertyTemplate> templates();

}
