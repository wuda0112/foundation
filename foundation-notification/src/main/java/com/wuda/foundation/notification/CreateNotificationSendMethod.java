package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification send method的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationSendMethod {

    private Long id;
    private String name;
    private String description;

    /**
     * for build {@link CreateNotificationSendMethod}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationSendMethod> {

        private Long id;
        private String name;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
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

        @Override
        public CreateNotificationSendMethod build() {
            CreateNotificationSendMethod createNotificationSendMethod = new CreateNotificationSendMethod();
            createNotificationSendMethod.id = Objects.requireNonNull(id);
            createNotificationSendMethod.name = Objects.requireNonNull(name);
            createNotificationSendMethod.description = Objects.requireNonNull(description);
            return createNotificationSendMethod;
        }
    }

}
