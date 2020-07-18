package com.wuda.foundation.user;

import com.wuda.foundation.commons.EmailManager;
import com.wuda.foundation.commons.PhoneManager;
import com.wuda.foundation.lang.identify.Identifier;
import com.wuda.foundation.lang.keygen.KeyGenerator;

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
    public long createUser(CreateUser createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId) {
        return createUserDbOp(createUser, emailManager, phoneManager, keyGenerator, opUserId);
    }

    /**
     * 作为{@link #createUser}方法的一部分,参数的校验已经在{@link #createUser}
     * 中完成,剩下的是数据库操作,由这个方法完成,如果特定的存储还有其他校验,则可以在这个方法中完成校验逻辑.
     *
     * @param createUser   用于创建用户的信息
     * @param emailManager 如果账号有email,则用于处理email
     * @param phoneManager 如果账号有phone,则用于处理phone
     * @param keyGenerator 用于生成记录的主键
     * @param opUserId     操作人用户ID,是谁正在添加这个新用户
     * @return 新增的用户的ID
     */
    protected abstract long createUserDbOp(CreateUser createUser, EmailManager emailManager, PhoneManager phoneManager, KeyGenerator<Long> keyGenerator, Long opUserId);

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
