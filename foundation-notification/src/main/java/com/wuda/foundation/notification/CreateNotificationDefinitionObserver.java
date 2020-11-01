package com.wuda.foundation.notification;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification definition observer的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationDefinitionObserver {

    private Long id;
    private Long notificationDefinitionId;
    private Long notificationDefinitionSendMethodId;
    private LongIdentifier observer;

    /**
     * for build {@link CreateNotificationDefinitionObserver}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationDefinitionObserver> {

        private Long id;
        private Long notificationDefinitionId;
        private Long notificationDefinitionSendMethodId;
        private LongIdentifier observer;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationDefinitionId(Long notificationDefinitionId) {
            this.notificationDefinitionId = notificationDefinitionId;
            return this;
        }

        public Builder setNotificationDefinitionSendMethodId(Long notificationDefinitionSendMethodId) {
            this.notificationDefinitionSendMethodId = notificationDefinitionSendMethodId;
            return this;
        }

        public Builder setObserver(LongIdentifier observer) {
            this.observer = observer;
            return this;
        }

        @Override
        public CreateNotificationDefinitionObserver build() {
            CreateNotificationDefinitionObserver createNotificationDefinitionObserver = new CreateNotificationDefinitionObserver();
            createNotificationDefinitionObserver.id = Objects.requireNonNull(id);
            createNotificationDefinitionObserver.notificationDefinitionId = Objects.requireNonNull(notificationDefinitionId);
            createNotificationDefinitionObserver.notificationDefinitionSendMethodId = Objects.requireNonNull(notificationDefinitionSendMethodId);
            createNotificationDefinitionObserver.observer = Objects.requireNonNull(observer);
            return createNotificationDefinitionObserver;
        }
    }

}
