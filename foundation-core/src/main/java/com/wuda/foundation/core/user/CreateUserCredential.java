package com.wuda.foundation.core.user;

import lombok.Getter;

import java.util.Objects;

/**
 * for create user credential.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateUserCredential {

    private Long id;
    private Long userId;
    private Byte type;
    private String value;
    private String description;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserCredential() {

    }

    /**
     * 用于创建{@link CreateUserCredential}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserCredential> {
        private Long id;
        private Long userId;
        private Byte type;
        private String value;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
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
        public CreateUserCredential build() {
            CreateUserCredential createUserCredential = new CreateUserCredential();
            createUserCredential.id = Objects.requireNonNull(id);
            createUserCredential.userId = Objects.requireNonNull(userId);
            createUserCredential.type = Objects.requireNonNull(type);
            createUserCredential.value = Objects.requireNonNull(this.value);
            createUserCredential.description = Objects.requireNonNull(this.description);
            return createUserCredential;
        }
    }
}
