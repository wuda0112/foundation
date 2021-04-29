package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;

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
     * 添加user principal.
     *
     * @param createUserPrincipal user principal
     * @param opUserId            操作人用户ID
     * @throws AlreadyExistsException 如果{@link CreateUserPrincipal#getName()} 已经存在
     */
    void createUserPrincipal(CreateUserPrincipal createUserPrincipal, Long opUserId) throws AlreadyExistsException;

    /**
     * 添加user principal.
     *
     * @param createUserCredential user credential
     * @param opUserId             操作人用户ID
     */
    void createUserCredential(CreateUserCredential createUserCredential, Long opUserId);

    /**
     * 批量添加user principal.
     *
     * @param userPrincipals user principal list
     * @param opUserId       操作人用户ID
     */
    void directBatchInsertUserPrincipal(List<CreateUserPrincipal> userPrincipals, Long opUserId);

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
     * 更新密码.
     *
     * @param userId      用户ID
     * @param newPassword 新的密码
     */
    void updatePassword(Long userId, String newPassword);

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
}
