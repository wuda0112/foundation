package com.wuda.foundation.user;

import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;

import java.util.List;
import java.util.Objects;

/**
 * 抽象实现,用于统一数据校验,设置和更新缓存等逻辑.比如,不管数据保存到MySQL还是MongoDB中,
 * 数据模型的完整性校验是一样的,因此,在接口和具体的存储实现类之间,加一层抽象实现,用于
 * 统一处理这些操作.
 *
 * @author wuda
 */
public abstract class AbstractUserManager implements UserManager {
    @Override
    public void createUser(CreateUser createUser, Long opUserId) {
        createUserDbOp(createUser, opUserId);
    }

    /**
     * 作为{@link #createUser(CreateUser, Long)}方法的一部分,参数的校验已经完成,
     * 剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createUser user
     * @param opUserId   操作人用户ID
     */
    protected abstract void createUserDbOp(CreateUser createUser, Long opUserId);

    @Override
    public void directBatchInsertUser(List<CreateUser> userList, Long opUserId) {
        directBatchInsertUserDbOp(userList, opUserId);
    }

    /**
     * 作为{@link #directBatchInsertUser(List, Long)}方法的一部分,参数的校验已经完成,
     * 剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param userList user list
     * @param opUserId 操作人用户ID
     */
    protected abstract void directBatchInsertUserDbOp(List<CreateUser> userList, Long opUserId);

    @Override
    public void createUserAccount(CreateUserAccount createUserAccount, Long opUserId) {
        createUserAccountDbOp(createUserAccount, opUserId);
    }

    protected abstract void createUserAccountDbOp(CreateUserAccount createUserAccount, Long opUserId);

    @Override
    public void directBatchInsertUserAccount(List<CreateUserAccount> userAccounts, Long opUserId) {
        directBatchInsertUserAccountDbOp(userAccounts, opUserId);
    }

    protected abstract void directBatchInsertUserAccountDbOp(List<CreateUserAccount> userAccounts, Long opUserId);

    @Override
    public Long bindUserEmail(BindUserEmail bindUserEmail, InsertMode insertMode, Long opUserId) {
        return bindUserEmailDbOp(bindUserEmail, insertMode, opUserId);
    }

    protected abstract Long bindUserEmailDbOp(BindUserEmail bindUserEmail, InsertMode insertMode, Long opUserId);

    @Override
    public void directBatchBindUserEmail(List<BindUserEmail> bindUserEmails, Long opUserId) {
        directBatchBindUserEmailDbOp(bindUserEmails, opUserId);
    }

    protected abstract void directBatchBindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId);

    @Override
    public Long bindUserPhone(BindUserPhone bindUserPhone, InsertMode insertMode, Long opUserId) {
        return bindUserPhoneDbOp(bindUserPhone, insertMode, opUserId);
    }

    protected abstract Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, InsertMode insertMode, Long opUserId);

    @Override
    public void directBatchBindUserPhone(List<BindUserPhone> bindUserPhones, Long opUserId) {
        directBatchBindUserPhoneDbOp(bindUserPhones, opUserId);
    }

    protected abstract void directBatchBindUserPhoneDbOp(List<BindUserPhone> bindUserPhones, Long opUserId);

    @Override
    public boolean exists(Identifier<String> identifier) {
        Objects.requireNonNull(identifier);
        Objects.requireNonNull(identifier.getType());
        Objects.requireNonNull(identifier.getValue());
        return existsDbOp(identifier);
    }

    /**
     * 作为{@link #exists(Identifier)}方法的一部分,
     * 参数的校验已经在{@link #exists(Identifier)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param identifier 用户唯一标记
     * @return <code>true</code>-如果存在
     */
    protected abstract boolean existsDbOp(Identifier<String> identifier);

    @Override
    public long createUser(CreateUserWithAccount createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId) {
        return createUserDbOp(createUser, emailManager, phoneManager, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createUser(CreateUserWithAccount, EmailManager, PhoneManager, KeyGenerator, Long)}方法的一部分,参数的校验已经
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createUser   用于创建用户的信息
     * @param emailManager 如果账号有email,则用于处理email
     * @param phoneManager 如果账号有phone,则用于处理phone
     * @param keyGenerator 如果创建了phone或者email,则需要{@link BindUserEmail}或者{@link BindUserPhone},这时需要用于生成ID
     * @param opUserId     操作人用户ID,是谁正在添加这个新用户
     * @return 新增的用户的ID
     */
    protected abstract long createUserDbOp(CreateUserWithAccount createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId);

    @Override
    public void updatePassword(Long userId, String newPassword) {

    }

    @Override
    public void changeUserAccountState(Long userId, UserAccountState newState) {

    }

    @Override
    public void changeUserState(Long userId, UserState newState) {

    }

    @Override
    public DescribeUser getUser(Long userId) {
        return null;
    }

    @Override
    public DescribeUserAccount getUserAccount(Long userId) {
        return null;
    }
}
