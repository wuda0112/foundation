package com.wuda.foundation.core.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 更新notification template的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateNotificationTemplate {

    private Long id;
    private String name;
    private String content;
    private String description;

    /**
     * for build {@link UpdateNotificationTemplate}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateNotificationTemplate> {

        private Long id;
        private String name;
        private String content;
        private String description;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public UpdateNotificationTemplate build() {
            UpdateNotificationTemplate updateNotificationTemplate = new UpdateNotificationTemplate();
            updateNotificationTemplate.id = Objects.requireNonNull(id);
            updateNotificationTemplate.name = name;
            updateNotificationTemplate.content = content;
            updateNotificationTemplate.description = description;
            return updateNotificationTemplate;
        }
    }

}
