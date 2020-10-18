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
public class CreateNotificationTemplate {

    private Long id;
    private String name;
    private String content;
    private String description;

    /**
     * for build {@link CreateNotificationTemplate}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationTemplate> {

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
        public CreateNotificationTemplate build() {
            CreateNotificationTemplate createNotificationTemplate = new CreateNotificationTemplate();
            createNotificationTemplate.id = Objects.requireNonNull(id);
            createNotificationTemplate.name = Objects.requireNonNull(name);
            createNotificationTemplate.content = Objects.requireNonNull(content);
            createNotificationTemplate.description = Objects.requireNonNull(description);
            return createNotificationTemplate;
        }
    }

}
