package com.wuda.foundation.user;

import lombok.Data;

/**
 * 描述menu item.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribeMenuItem {

    private Long id;
    private Short groupType;
    private String name;
    private String description;
}
