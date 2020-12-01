package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.identify.Identifier;

import java.util.List;

public interface UserManager {

    /**
     * 添加user core.
     *
     * @param createUserCore user core
     * @param opUserId       操作人用户ID
     */
    void createUserCore(CreateUserCore createUserCore, Long opUserId);

    /**
     * 批量添加user core.
     *
     * @param userCores user core list
     * @param opUserId  操作人用户ID
     */
    void directBatchInsertUserCore(List<CreateUserCore> userCores, Long opUserId);

    /**
     * 添加user account.
     *
     * @param createUserAccount user account
     * @param opUserId          操作人用户ID
     * @throws AlreadyExistsException 如果{@link CreateUserAccount#getUsername()}已经存在
     */
    void createUserAccount(CreateUserAccount createUserAccount, Long opUserId) throws AlreadyExistsException;

    /**
     * 批量添加user account.
     *
     * @param userAccounts user account list
     * @param opUserId     操作人用户ID
     */
    void directBatchInsertUserAccount(List<CreateUserAccount> userAccounts, Long opUserId);

    /**
     * 绑定用户和Email.
     *
     * @param bindUserEmail binding
     * @param createMode    create mode
     * @param opUserId      操作人用户ID
     * @return 绑定关系的记录的ID
     */
    Long bindUserEmail(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId);

    /**
     * 绑定用户和Email.
     *
     * @param bindUserEmails binding
     * @param opUserId       操作人用户ID
     * @return 绑定关系的记录的ID
     */
    void directBatchBindUserEmail(List<BindUserEmail> bindUserEmails, Long opUserId);

    /**
     * 绑定用户和Phone.
     *
     * @param bindUserPhone binding
     * @param createMode    create mode
     * @param opUserId      操作人用户ID
     * @return 绑定关系的记录的ID
     */
    Long bindUserPhone(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId);

    /**
     * 绑定用户和Phone.
     *
     * @param bindUserPhones binding
     * @param opUserId       操作人用户ID
     * @return 绑定关系的记录的ID
     */
    void directBatchBindUserPhone(List<BindUserPhone> bindUserPhones, Long opUserId);

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
     * @param createUser 用于创建用户的信息
     * @param opUserId   操作人用户ID,是谁正在添加这个新用户
     * @throws AlreadyExistsException 如果username,email,phone已经存在
     */
    void createUserWithAccount(CreateUserWithAccount createUser, Long opUserId) throws AlreadyExistsException;

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
    void changeUserAccountState(Long userId, Byte newState);

    /**
     * 改变用户的状态.
     *
     * @param userId   用户ID
     * @param newState 新的状态.
     */
    void changeUserState(Long userId, Byte newState);

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
