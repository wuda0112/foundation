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
public class CreateUser {

    private Long id;
    private Byte userType;
    private Byte userState;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUser() {

    }

    /**
     * 用于创建{@link CreateUser}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUser> {

        private Long id;
        private Byte userType;
        private Byte userState;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserType(Byte userType) {
            this.userType = userType;
            return this;
        }

        public Builder setUserState(Byte userState) {
            this.userState = userState;
            return this;
        }

        @Override
        public CreateUser build() {
            CreateUser createUser = new CreateUser();
            createUser.id = Objects.requireNonNull(this.id);
            createUser.userType = Objects.requireNonNull(this.userType);
            createUser.userState = Objects.requireNonNull(this.userState);
            return createUser;
        }
    }
}
