package com.wuda.foundation.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for create email.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateEmail {

    private Long id;
    private String address;
    private Byte state;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateEmail() {

    }

    /**
     * 用于创建{@link CreateEmail}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateEmail> {

        private Long id;
        private String address;
        private Byte state;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setEmailState(Byte state) {
            this.state = state;
            return this;
        }

        @Override
        public CreateEmail build() {
            CreateEmail createEmail = new CreateEmail();
            createEmail.id = Objects.requireNonNull(this.id);
            createEmail.address = Objects.requireNonNull(this.address);
            createEmail.state = Objects.requireNonNull(this.state);
            return createEmail;
        }
    }
}
