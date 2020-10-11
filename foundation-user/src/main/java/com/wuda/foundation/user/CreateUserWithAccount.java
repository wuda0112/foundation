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

    private CreateUserCore userCore;
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

        private CreateUserCore userCore;
        private CreateUserAccount userAccount;

        public Builder setUser(CreateUserCore userCore) {
            this.userCore = userCore;
            return this;
        }

        public Builder setUserAccount(CreateUserAccount userAccount) {
            this.userAccount = userAccount;
            return this;
        }

        @Override
        public CreateUserWithAccount build() {
            CreateUserWithAccount createUserWithAccount = new CreateUserWithAccount();
            createUserWithAccount.userCore = Objects.requireNonNull(this.userCore);
            createUserWithAccount.userAccount = Objects.requireNonNull(this.userAccount);
            if (!userCore.getUserId().equals(userAccount.getUserId())) {
                throw new IllegalStateException("用户Id不一致");
            }
            return createUserWithAccount;
        }
    }
}
