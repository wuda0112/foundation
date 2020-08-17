package com.wuda.foundation.user;

import lombok.Getter;

import java.util.Objects;

/**
 * for create user.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateUserWithAccount {

    private CreateUser user;
    private CreateUserAccount userAccount;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserWithAccount() {

    }

    /**
     * 用于创建{@link CreateUserWithAccount}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserWithAccount> {

        private CreateUser user;
        private CreateUserAccount userAccount;

        public Builder setUser(CreateUser user) {
            this.user = user;
            return this;
        }

        public Builder setUserAccount(CreateUserAccount userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        @Override
        public CreateUserWithAccount build() {
            CreateUserWithAccount createUserWithAccount = new CreateUserWithAccount();
            createUserWithAccount.user = Objects.requireNonNull(this.user);
            createUserWithAccount.userAccount = Objects.requireNonNull(this.userAccount);
            if (!user.getId().equals(userAccount.getUserId())) {
                throw new IllegalStateException("用户Id不一致");
            }
            return createUserWithAccount;
        }
    }
}
