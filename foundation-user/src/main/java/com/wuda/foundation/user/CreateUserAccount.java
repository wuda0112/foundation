package com.wuda.foundation.user;

import com.wuda.foundation.commons.CreateEmail;
import com.wuda.foundation.commons.CreatePhone;
import lombok.Getter;

import java.util.Objects;

/**
 * for create user account.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateUserAccount {

    private Long id;
    private Long userId;
    private String username;
    private String password;
    private UserAccountState state;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserAccount() {

    }

    /**
     * 用于创建{@link CreateUserAccount}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserAccount> {
        private Long id;
        private Long userId;
        private String username;
        private CreateEmail email;
        private CreatePhone phone;
        private String password;
        private UserAccountState state;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setEmail(CreateEmail email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(CreatePhone phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setState(UserAccountState state) {
            this.state = state;
            return this;
        }

        @Override
        public CreateUserAccount build() {
            CreateUserAccount createUserAccount = new CreateUserAccount();
            createUserAccount.id = Objects.requireNonNull(id);
            createUserAccount.userId = Objects.requireNonNull(userId);
            createUserAccount.username = Objects.requireNonNull(this.username);
            createUserAccount.password = Objects.requireNonNull(this.password);
            createUserAccount.state = Objects.requireNonNull(this.state);
            return createUserAccount;
        }
    }
}
