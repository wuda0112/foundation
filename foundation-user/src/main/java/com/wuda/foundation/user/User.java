package com.wuda.foundation.user;

import com.wuda.foundation.lang.CRUDEntity;
import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.IdValidator;
import lombok.Getter;

import java.util.Objects;

/**
 * 代表一个用户.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class User extends CRUDEntity {
    /**
     * user id.
     */
    private Long userId;
    /**
     * user type.
     */
    private UserType userType;
    /**
     * user status.
     */
    private UserState userState;
    /**
     * user account.
     */
    private UserAccount userAccount;

    @Override
    protected void forCreateCheck() {
        forCreateCheckShortcut0();
        Objects.requireNonNull(userState);
        Objects.requireNonNull(userType);
        Objects.requireNonNull(userAccount);
        userAccount.forCreateCheck();
        UserIdValidator.userRelatedIdValidate(userType, userId);
    }

    @Override
    protected void forUpdateCheck() {
        forUpdateCheckShortcut0();
        IdValidator.notEqualsZero(userId);
        if (userAccount != null) {
            userAccount.forUpdateCheck();
        }
    }

    /**
     * {@link User} builder.
     *
     * @author wuda
     * @since 1.0.0
     */

    public static class Builder extends CRUDEntityBuilder<User, Builder> {
        private User user = new User();

        public Builder(CRUDEntityState crudState) {
            super(crudState);
        }

        /**
         * 设置该用户的id
         *
         * @param userId user id
         * @return this
         */
        public Builder userId(Long userId) {
            user.userId = userId;
            return this;
        }

        /**
         * 设置该用户的类型
         *
         * @param userType user type
         * @return this
         */
        public Builder userType(UserType userType) {
            user.userType = userType;
            return this;
        }

        /**
         * 设置该用户的状态
         *
         * @param userState user status
         * @return this
         */
        public Builder userStatus(UserState userState) {
            user.userState = userState;
            return this;
        }

        /**
         * 设置该用户的账号
         *
         * @param userAccount user account
         * @return this
         */
        public Builder userAccount(UserAccount userAccount) {
            user.userAccount = userAccount;
            return this;
        }

        @Override
        public User build() {
            beforeBuild(user);
            return user;
        }

        @Override
        public Builder createTime(Long createTime) {
            user.createTime = createTime;
            return this;
        }

        @Override
        public Builder createUserId(Long createUserId) {
            user.createUserId = createUserId;
            return this;
        }

        @Override
        public Builder lastModifyTime(Long lastModifyTime) {
            user.lastModifyTime = lastModifyTime;
            return this;
        }

        @Override
        public Builder lastModifyUserId(Long lastModifyUserId) {
            user.lastModifyUserId = lastModifyUserId;
            return this;
        }
    }
}
