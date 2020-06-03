package com.wuda.foundation.user;

import com.wuda.foundation.lang.Identifier;

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
    public long addUser(UserType userType,
                        UserState userState,
                        List<Identifier<String>> identifiers,
                        String password,
                        UserAccountState userAccountState,
                        Long opUserId) {
        Objects.requireNonNull(userType);
        Objects.requireNonNull(userState);
        Objects.requireNonNull(identifiers);
        Objects.requireNonNull(password);
        Objects.requireNonNull(userAccountState);
        Objects.requireNonNull(opUserId);
        return addUserDbOp(userType, userState, identifiers, password, userAccountState, opUserId);
    }

    /**
     * 作为{@link #addUser(UserType, UserState, List, String, UserAccountState, Long)}方法的一部分,
     * 参数的校验已经在{@link #addUser(UserType, UserState, List, String, UserAccountState, Long)}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param userType         用户类型
     * @param userState        用户状态
     * @param identifiers      唯一标记这个用户,比如username,email等等
     * @param password         密码
     * @param userAccountState 账号的状态
     * @param opUserId         操作人用户ID,是谁正在添加这个新用户
     * @return 用户ID
     */
    protected abstract long addUserDbOp(UserType userType,
                                        UserState userState,
                                        List<Identifier<String>> identifiers,
                                        String password,
                                        UserAccountState userAccountState,
                                        Long opUserId);

    @Override
    public void updatePassword(Long userId, String newPassword) {

    }

    @Override
    public void updateUserAccountStatus(Long userId, UserAccountState status) {

    }

    @Override
    public void updateUserStatus(Long userId, UserState status) {

    }

    @Override
    public User getUser(Long userId) {
        return null;
    }

    @Override
    public UserAccount getUserAccount(Long userId) {
        return null;
    }
}
