package com.wuda.foundation.user;

import com.wuda.foundation.commons.PhoneUse;
import lombok.Getter;

import java.util.Objects;

/**
 * 绑定用户和Email.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class BindUserPhone {

    private Long id;
    private Long userId;
    private Long phoneId;
    private PhoneUse use;
    private UserPhoneState state;
    private String description;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private BindUserPhone() {

    }

    /**
     * 用于创建{@link BindUserPhone}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<BindUserPhone> {

        private Long id;
        private Long userId;
        private Long phoneId;
        private PhoneUse use;
        private UserPhoneState state;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setPhoneId(Long phoneId) {
            this.phoneId = phoneId;
            return this;
        }

        public Builder setState(UserPhoneState state) {
            this.state = state;
            return this;
        }

        public Builder setUse(PhoneUse use) {
            this.use = use;
            return this;
        }


        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public BindUserPhone build() {
            BindUserPhone bindUserPhone = new BindUserPhone();
            bindUserPhone.id = Objects.requireNonNull(this.id);
            bindUserPhone.userId = Objects.requireNonNull(this.userId);
            bindUserPhone.phoneId = Objects.requireNonNull(this.phoneId);
            bindUserPhone.use = Objects.requireNonNull(this.use);
            bindUserPhone.state = Objects.requireNonNull(this.state);
            return bindUserPhone;
        }
    }
}
