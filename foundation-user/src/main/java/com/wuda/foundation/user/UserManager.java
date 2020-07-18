package com.wuda.foundation.user;

import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;

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
     * @param createUser   用于创建用户的信息
     * @param emailManager 如果账号有email,则用于处理email
     * @param phoneManager 如果账号有phone,则用于处理phone
     * @param keyGenerator 用于生成记录的主键
     * @param opUserId     操作人用户ID,是谁正在添加这个新用户
     * @return 新增的用户的ID
     */
    long createUser(CreateUser createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId);

    /**
     * 更新密码.
     *
     * @param userId      用户ID
     * @param newPassword 新的密码
     */
    void updatePassword(Long userId, String newPassword);

    /**
     * 改变用户账号的状态.
     *
     * @param userId   用户ID
     * @param newState 新的状态.
     */
    void changeUserAccountState(Long userId, UserAccountState newState);

    /**
     * 改变用户的状态.
     *
     * @param userId   用户ID
     * @param newState 新的状态.
     */
    void changeUserState(Long userId, UserState newState);

    /**
     * 获取用户详细信息.
     *
     * @param userId 用户ID
     * @return user, <code>null</code>-如果用户不存在
     */
    DescribeUser getUser(Long userId);

    /**
     * 获取用户账户信息.
     *
     * @param userId 用户ID
     * @return 该用户的账户信息, <code>null</code>-如果用户不存在
     */
    DescribeUserAccount getUserAccount(Long userId);
}
