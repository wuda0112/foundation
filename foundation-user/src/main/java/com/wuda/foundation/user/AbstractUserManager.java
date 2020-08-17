package com.wuda.foundation.user;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.identify.Identifier;

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

    protected abstract void createUserDbOp(CreateUser createUser, Long opUserId);

    @Override
    public void directBatchInsertUser(List<CreateUser> userList, Long opUserId) {
        directBatchInsertUserDbOp(userList, opUserId);
    }

    protected abstract void directBatchInsertUserDbOp(List<CreateUser> userList, Long opUserId);

    @Override
    public void createUserAccount(CreateUserAccount createUserAccount, Long opUserId) throws AlreadyExistsException {
        createUserAccountDbOp(createUserAccount, opUserId);
    }

    protected abstract void createUserAccountDbOp(CreateUserAccount createUserAccount, Long opUserId) throws AlreadyExistsException;

    @Override
    public void directBatchInsertUserAccount(List<CreateUserAccount> userAccounts, Long opUserId) {
        directBatchInsertUserAccountDbOp(userAccounts, opUserId);
    }

    protected abstract void directBatchInsertUserAccountDbOp(List<CreateUserAccount> userAccounts, Long opUserId);

    @Override
    public Long bindUserEmail(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId) {
        return bindUserEmailDbOp(bindUserEmail, createMode, opUserId);
    }

    protected abstract Long bindUserEmailDbOp(BindUserEmail bindUserEmail, CreateMode createMode, Long opUserId);

    @Override
    public void directBatchBindUserEmail(List<BindUserEmail> bindUserEmails, Long opUserId) {
        directBatchBindUserEmailDbOp(bindUserEmails, opUserId);
    }

    protected abstract void directBatchBindUserEmailDbOp(List<BindUserEmail> bindUserEmails, Long opUserId);

    @Override
    public Long bindUserPhone(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId) {
        return bindUserPhoneDbOp(bindUserPhone, createMode, opUserId);
    }

    protected abstract Long bindUserPhoneDbOp(BindUserPhone bindUserPhone, CreateMode createMode, Long opUserId);

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

    protected abstract boolean existsDbOp(Identifier<String> identifier);

    @Override
    public void createUserWithAccount(CreateUserWithAccount createUser, Long opUserId) throws AlreadyExistsException {
        createUserWithAccountDbOp(createUser, opUserId);
    }

    protected abstract void createUserWithAccountDbOp(CreateUserWithAccount createUser, Long opUserId) throws AlreadyExistsException;

    @Override
    public void updatePassword(Long userId, String newPassword) {

    }

    @Override
    public void changeUserAccountState(Long userId, Byte newState) {

    }

    @Override
    public void changeUserState(Long userId, Byte newState) {

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
