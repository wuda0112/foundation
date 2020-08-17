package com.wuda.foundation.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for create phone.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreatePhone {

    private Long id;
    private String number;
    private Byte type;
    private Byte state;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreatePhone() {

    }

    /**
     * 用于创建{@link CreatePhone}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreatePhone> {

        private Long id;
        private String number;
        private Byte type;
        private Byte state;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setPhoneType(Byte type) {
            this.type = type;
            return this;
        }

        public Builder setPhoneState(Byte state) {
            this.state = state;
            return this;
        }

        @Override
        public CreatePhone build() {
            CreatePhone createPhone = new CreatePhone();
            createPhone.id = Objects.requireNonNull(this.id);
            createPhone.number = Objects.requireNonNull(this.number);
            createPhone.type = Objects.requireNonNull(this.type);
            createPhone.state = Objects.requireNonNull(this.state);
            return createPhone;
        }
    }
}
