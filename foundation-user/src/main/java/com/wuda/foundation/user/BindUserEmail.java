package com.wuda.foundation.user;

import com.wuda.foundation.commons.EmailUse;
import lombok.Getter;

import java.util.Objects;

/**
 * 绑定用户和Email.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class BindUserEmail {

    private Long id;
    private Long userId;
    private Long emailId;
    private EmailUse use;
    private UserEmailState state;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private BindUserEmail() {

    }

    /**
     * 用于创建{@link BindUserEmail}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<BindUserEmail> {

        private Long id;
        private Long userId;
        private Long emailId;
        private EmailUse use;
        private UserEmailState state;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setEmailId(Long emailId) {
            this.emailId = emailId;
            return this;
        }

        public Builder setState(UserEmailState state) {
            this.state = state;
            return this;
        }

        public Builder setUse(EmailUse use) {
            this.use = use;
            return this;
        }

        @Override
        public BindUserEmail build() {
            BindUserEmail bindUserEmail = new BindUserEmail();
            bindUserEmail.id = Objects.requireNonNull(this.id);
            bindUserEmail.userId = Objects.requireNonNull(this.userId);
            bindUserEmail.emailId = Objects.requireNonNull(this.emailId);
            bindUserEmail.use = Objects.requireNonNull(this.use);
            bindUserEmail.state = Objects.requireNonNull(this.state);
            return bindUserEmail;
        }
    }
}
