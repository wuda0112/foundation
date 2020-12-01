package com.wuda.foundation.core.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification definition send method的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationDefinitionSendMethod {

    private Long id;
    private Long notificationDefinitionId;
    private Long notificationSendMethodId;
    private Long notificationTemplateId;

    /**
     * for build {@link CreateNotificationDefinitionSendMethod}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationDefinitionSendMethod> {

        private Long id;
        private Long notificationDefinitionId;
        private Long notificationSendMethodId;
        private Long notificationTemplateId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationDefinitionId(Long notificationDefinitionId) {
            this.notificationDefinitionId = notificationDefinitionId;
            return this;
        }

        public Builder setNotificationSendMethodId(Long notificationSendMethodId) {
            this.notificationSendMethodId = notificationSendMethodId;
            return this;
        }

        public Builder setNotificationTemplateId(Long notificationTemplateId) {
            this.notificationTemplateId = notificationTemplateId;
            return this;
        }

        @Override
        public CreateNotificationDefinitionSendMethod build() {
            CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod = new CreateNotificationDefinitionSendMethod();
            createNotificationDefinitionSendMethod.id = Objects.requireNonNull(id);
            createNotificationDefinitionSendMethod.notificationDefinitionId = Objects.requireNonNull(notificationDefinitionId);
            createNotificationDefinitionSendMethod.notificationSendMethodId = Objects.requireNonNull(notificationSendMethodId);
            createNotificationDefinitionSendMethod.notificationTemplateId = Objects.requireNonNull(notificationTemplateId);
            return createNotificationDefinitionSendMethod;
        }
    }

}
