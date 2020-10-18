package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification post method的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationPostMethod {

    private Long id;
    private String name;
    private String description;

    /**
     * for build {@link CreateNotificationPostMethod}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationPostMethod> {

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
        public CreateNotificationPostMethod build() {
            CreateNotificationPostMethod createNotificationPostMethod = new CreateNotificationPostMethod();
            createNotificationPostMethod.id = Objects.requireNonNull(id);
            createNotificationPostMethod.name = Objects.requireNonNull(name);
            createNotificationPostMethod.description = Objects.requireNonNull(description);
            return createNotificationPostMethod;
        }
    }

}
