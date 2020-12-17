package com.wuda.foundation.core.commons;

import lombok.Data;

/**
 * 描述menu item.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribeMenuItemCore {

    private Long id;
    private Long menuItemId;
    private String name;
    private String description;
}
