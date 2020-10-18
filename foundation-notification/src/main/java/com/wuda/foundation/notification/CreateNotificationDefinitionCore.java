package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification definition core的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateNotificationDefinitionCore {

    private Long id;
    private Long notificationDefinitionId;
    /**
     * 该定义本身的名称，就好像Java语言中定义一个Class，要给这个Class命名.
     */
    private String name;
    /**
     * 对于该定义的描述.
     */
    private String description;

    /**
     * for build {@link CreateNotificationDefinitionCore}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationDefinitionCore> {

        private Long id;
        private Long notificationDefinitionId;
        private String name;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationDefinitionId(Long notificationDefinitionId) {
            this.notificationDefinitionId = notificationDefinitionId;
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
        public CreateNotificationDefinitionCore build() {
            CreateNotificationDefinitionCore createNotificationDefinitionCore = new CreateNotificationDefinitionCore();
            createNotificationDefinitionCore.id = Objects.requireNonNull(id);
            createNotificationDefinitionCore.notificationDefinitionId = Objects.requireNonNull(notificationDefinitionId);
            createNotificationDefinitionCore.name = Objects.requireNonNull(name);
            createNotificationDefinitionCore.description = Objects.requireNonNull(description);
            return createNotificationDefinitionCore;
        }
    }

}
