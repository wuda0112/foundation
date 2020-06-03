package com.wuda.foundation.user;

import com.wuda.foundation.lang.Identifier;

import java.util.List;

public interface UserManager {

    /**
     * 检查用户是否存在.
     *
     * @param identifier 用户唯一标记
     * @return <code>true</code>-如果存在
     */
    boolean exists(Identifier<String> identifier);

    /**
     * 新增一个新用户.
     *
     * @param userType         用户类型
     * @param userState        用户状态
     * @param identifiers      唯一标记这个用户,比如username,email等等
     * @param password         密码
     * @param userAccountState 账号的状态
     * @param opUserId         操作人用户ID,是谁正在添加这个新用户
     * @return 用户ID
     */
    long addUser(UserType userType,
                 UserState userState,
                 List<Identifier<String>> identifiers,
                 String password,
                 UserAccountState userAccountState,
                 Long opUserId);

    /**
     * 更新密码.
     *
     * @param userId      用户ID
     * @param newPassword 新的密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 更新用户账号的状态.
     *
     * @param userId 用户ID
     * @param status 用户账号的状态.
     */
    void updateUserAccountStatus(Long userId, UserAccountState status);

    /**
     * 更新用户账号的状态.
     *
     * @param userId 用户ID
     * @param status 用户账号的状态.
     */
    void updateUserStatus(Long userId, UserState status);

    /**
     * 获取用户详细信息.
     *
     * @param userId 用户ID
     * @return user, <code>null</code>-如果用户不存在
     */
    User getUser(Long userId);

    /**
     * 获取用户账户信息.
     *
     * @param userId 用户ID
     * @return 该用户的账户信息, <code>null</code>-如果用户不存在
     */
    UserAccount getUserAccount(Long userId);
}
