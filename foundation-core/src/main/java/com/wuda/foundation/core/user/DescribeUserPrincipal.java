package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.BasicAttribute;
import lombok.Data;

/**
 * 用户principal信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
public class DescribeUserPrincipal extends BasicAttribute {
    /**
     * user principal id.
     */
    private Long id;
    /**
     * 所属的用户的ID.
     */
    private Long userId;
    /**
     * 名称.
     */
    private String name;
    /**
     * 描述.
     */
    private String description;
    /**
     * type.
     */
    private Byte type;
}
