package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification definition post method的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationDefinitionPostMethod {

    private Long id;
    private Long notificationDefinitionId;
    private Long notificationPostMethodId;
    private Long notificationTemplateId;

    /**
     * for build {@link CreateNotificationDefinitionPostMethod}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationDefinitionPostMethod> {

        private Long id;
        private Long notificationDefinitionId;
        private Long notificationPostMethodId;
        private Long notificationTemplateId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationDefinitionId(Long notificationDefinitionId) {
            this.notificationDefinitionId = notificationDefinitionId;
            return this;
        }

        public Builder setNotificationPostMethodId(Long notificationPostMethodId) {
            this.notificationPostMethodId = notificationPostMethodId;
            return this;
        }

        public Builder setNotificationTemplateId(Long notificationTemplateId) {
            this.notificationTemplateId = notificationTemplateId;
            return this;
        }

        @Override
        public CreateNotificationDefinitionPostMethod build() {
            CreateNotificationDefinitionPostMethod createNotificationDefinitionPostMethod = new CreateNotificationDefinitionPostMethod();
            createNotificationDefinitionPostMethod.id = Objects.requireNonNull(id);
            createNotificationDefinitionPostMethod.notificationDefinitionId = Objects.requireNonNull(notificationDefinitionId);
            createNotificationDefinitionPostMethod.notificationPostMethodId = Objects.requireNonNull(notificationPostMethodId);
            createNotificationDefinitionPostMethod.notificationTemplateId = Objects.requireNonNull(notificationTemplateId);
            return createNotificationDefinitionPostMethod;
        }
    }

}
