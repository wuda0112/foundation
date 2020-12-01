package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.BasicAttribute;
import lombok.Data;

/**
 * 代表一个用户.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
public class DescribeUser extends BasicAttribute {
    /**
     * user id.
     */
    private Long userId;
    /**
     * user type.
     */
    private Byte userType;
    /**
     * user status.
     */
    private Byte userState;
    /**
     * user account.
     */
    private DescribeUserAccount userAccount;
}
