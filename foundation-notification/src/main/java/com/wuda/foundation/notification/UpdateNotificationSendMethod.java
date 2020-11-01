package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 更新notification send method的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateNotificationSendMethod {

    private Long id;
    private String name;
    private String description;

    /**
     * for build {@link UpdateNotificationSendMethod}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateNotificationSendMethod> {

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
        public UpdateNotificationSendMethod build() {
            UpdateNotificationSendMethod updateNotificationSendMethod = new UpdateNotificationSendMethod();
            updateNotificationSendMethod.id = Objects.requireNonNull(id);
            updateNotificationSendMethod.name = name;
            updateNotificationSendMethod.description = description;
            return updateNotificationSendMethod;
        }
    }

}
