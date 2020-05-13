package com.wuda.foundation.user;

public interface UserManager {

    /**
     * 检查principal是否存在.
     *
     * @param principal 用户唯一标记
     * @return <code>true</code>-如果存在
     */
    boolean exists(UserPrincipal principal);

    /**
     * 新增一个用户.
     *
     * @param account 账号
     * @param type    用户类型
     * @param status  用户状态
     * @return 用户ID
     */
    long addUser(UserAccount account, UserType type, UserStatus status);

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
    void updateUserAccountStatus(Long userId, UserAccountStatus status);

    /**
     * 更新用户账号的状态.
     *
     * @param userId 用户ID
     * @param status 用户账号的状态.
     */
    void updateUserStatus(Long userId, UserStatus status);

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
