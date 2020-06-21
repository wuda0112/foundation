package com.wuda.foundation.user;

import com.wuda.foundation.lang.BasicAttribute;
import com.wuda.foundation.lang.CRUDEntity;
import com.wuda.foundation.lang.CRUDState;
import com.wuda.foundation.lang.IdValidator;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

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
    private UserType userType;
    /**
     * user status.
     */
    private UserState userState;
    /**
     * user account.
     */
    private DescribeUserAccount userAccount;
}
