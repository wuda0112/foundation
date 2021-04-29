package com.wuda.foundation.core.user;

import lombok.Getter;

import java.util.Objects;

/**
 * for create user principal.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateUserPrincipal {

    private Long id;
    private Long userId;
    private Byte type;
    private String name;
    private String description;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserPrincipal() {

    }

    /**
     * 用于创建{@link CreateUserPrincipal}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserPrincipal> {
        private Long id;
        private Long userId;
        private Byte type;
        private String name;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setType(Byte type) {
            this.type = type;
            return this;
        }

        @Override
        public CreateUserPrincipal build() {
            CreateUserPrincipal createUserPrincipal = new CreateUserPrincipal();
            createUserPrincipal.id = Objects.requireNonNull(id);
            createUserPrincipal.userId = Objects.requireNonNull(userId);
            createUserPrincipal.type = Objects.requireNonNull(type);
            createUserPrincipal.name = Objects.requireNonNull(this.name);
            createUserPrincipal.description = Objects.requireNonNull(this.description);
            return createUserPrincipal;
        }
    }
}
