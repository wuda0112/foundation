package com.wuda.foundation.user;

import com.wuda.foundation.lang.BasicAttribute;
import com.wuda.foundation.lang.Identifier;
import lombok.Data;

import java.util.List;

/**
 * 用户账号信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
public class DescribeUserAccount extends BasicAttribute {
    /**
     * user account id.
     */
    private Long userAccountId;
    /**
     * 所属的用户的ID.
     */
    private Long userId;
    /**
     * 可以唯一标记用户的信息,比如username,email.
     */
    private List<Identifier<String>> principals;
    /**
     * 密码.
     */
    private String password;
    /**
     * 账号的状态.
     */
    private UserAccountState state;
}
