package com.wuda.foundation.core.user;

import lombok.Getter;

import java.util.Objects;

/**
 * for create user.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateUserCore {

    private Long id;
    private Long userId;
    private Byte represent;
    private Byte userType;
    private Byte userState;
    private Boolean canSignIn;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserCore() {

    }

    /**
     * 用于创建{@link CreateUserCore}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserCore> {

        private Long id;
        private Long userId;
        private Byte represent;
        private Byte userType;
        private Byte userState;
        private Boolean canSignIn;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setRepresent(Byte represent) {
            this.represent = represent;
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

        public Builder setCanSignIn(Boolean canSignIn) {
            this.canSignIn = canSignIn;
            return this;
        }

        @Override
        public CreateUserCore build() {
            CreateUserCore createUserCore = new CreateUserCore();
            createUserCore.id = Objects.requireNonNull(this.id);
            createUserCore.userId = Objects.requireNonNull(this.userId);
            createUserCore.represent = Objects.requireNonNull(this.represent);
            createUserCore.userType = Objects.requireNonNull(this.userType);
            createUserCore.userState = Objects.requireNonNull(this.userState);
            createUserCore.canSignIn = Objects.requireNonNull(this.canSignIn);
            return createUserCore;
        }
    }
}
