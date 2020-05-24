package com.wuda.foundation.user;

import com.wuda.foundation.lang.CRUDEntity;
import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.IdValidator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户账号信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UserAccount extends CRUDEntity {
    /**
     * 只能通过{@link Builder}构建实例.
     */
    private UserAccount() {

    }

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
    private List<UserPrincipal> principals;
    /**
     * 密码.
     */
    private String password;
    /**
     * 账号的状态.
     */
    private UserAccountState status;
    /**
     * 所属用户的类型.
     */
    private UserType userType;

    private UserAccountValidator validator;

    @Override
    protected void forCreateCheck() {
        forCreateCheckShortcut0();
        Objects.requireNonNull(userType);
        UserIdValidator.userRelatedIdValidate(userType, userAccountId);
        UserIdValidator.notEqualsZero(userId);
        if (validator == null) {
            throw new IllegalArgumentException("新建用户,必须指定" + UserAccountValidator.class.getSimpleName());
        }
        if (status == null) {
            throw new NullPointerException("用户账号状态不能为空");
        }
        if (principals == null || principals.isEmpty()) {
            throw new IllegalStateException("新建用户,至少应该有一个principal");
        }
        validator.validPassword(password);
        for (UserPrincipal principal : principals) {
            validator.validPrincipal(principal);
        }
    }

    @Override
    protected void forUpdateCheck() {
        forUpdateCheckShortcut0();
        IdValidator.notEqualsZero(userAccountId);
        if (validator == null) {
            throw new IllegalArgumentException("更新用户账号,必须指定validator");
        }
    }

    /**
     * 用于实例化{@link UserAccount}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder extends CRUDEntityBuilder<UserAccount, Builder> {

        private UserAccount userAccount = new UserAccount();

        /**
         * 生成builder.
         *
         * @param state     由此builder实例化的{@link UserAccount}准备用于哪种操作
         * @param validator 如果实例化的{@link UserAccount}被用于{@link CRUDEntityState#CREATE}或者{@link CRUDEntityState#UPDATE},
         *                  则必须指定校验器,因为需要校验参数的合法性.其他时候可以设置为<code>null</code>
         */
        public Builder(CRUDEntityState state, UserAccountValidator validator) {
            super(state);
            Objects.requireNonNull(state);
            userAccount.validator = validator;
        }

        /**
         * 设置用户ID.
         *
         * @param userId 用户ID,可以为空,当新建用户时,就可能没有用户ID,比如使用mysql自增ID.
         * @return this
         */
        public Builder userId(Long userId) {
            userAccount.userId = userId;
            return this;
        }

        /**
         * 设置用户账号ID.
         *
         * @param userAccountId 用户账号ID,可以为空,当新建用户时,就可能没有用户账号ID,比如使用mysql自增ID.
         * @return this
         */
        public Builder userAccountId(Long userAccountId) {
            userAccount.userAccountId = userAccountId;
            return this;
        }

        /**
         * 添加principal.可以多次添加.
         *
         * @param principal principal,不能为<code>null</code>
         * @return this
         */
        public Builder addPrincipal(UserPrincipal principal) {
            if (principal == null) {
                throw new NullPointerException();
            }
            if (userAccount.principals == null) {
                userAccount.principals = new ArrayList<>(3);
            }
            userAccount.principals.add(principal);
            return this;
        }

        /**
         * 设置账户的状态.
         *
         * @param status 状态
         * @return this
         */
        public Builder status(UserAccountState status) {
            userAccount.status = status;
            return this;
        }

        /**
         * 设置密码.
         *
         * @param password password
         * @return this
         */
        public Builder password(String password) {
            userAccount.password = password;
            return this;
        }

        public Builder userType(UserType userType) {
            userAccount.userType = userType;
            return this;
        }

        @Override
        public UserAccount build() {
            beforeBuild(userAccount);
            return userAccount;
        }

        @Override
        public Builder createTime(Long createTime) {
            userAccount.createTime = createTime;
            return this;
        }

        @Override
        public Builder createUserId(Long createUserId) {
            userAccount.createUserId = createUserId;
            return this;
        }

        @Override
        public Builder lastModifyTime(Long lastModifyTime) {
            userAccount.lastModifyTime = lastModifyTime;
            return this;
        }

        @Override
        public Builder lastModifyUserId(Long lastModifyUserId) {
            userAccount.lastModifyUserId = lastModifyUserId;
            return this;
        }
    }
}
