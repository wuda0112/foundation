package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建notification definition content的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateNotificationDefinitionContent {

    private Long id;
    /**
     * 更新通知的标题
     */
    private String title;
    /**
     * 更新模板参数.
     */
    private String templateParameter;

    /**
     * for build {@link UpdateNotificationDefinitionContent}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateNotificationDefinitionContent> {

        private Long id;
        private String title;
        private String templateParameter;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTemplateParameter(String templateParameter) {
            this.templateParameter = templateParameter;
            return this;
        }

        @Override
        public UpdateNotificationDefinitionContent build() {
            UpdateNotificationDefinitionContent updateNotificationDefinitionContent = new UpdateNotificationDefinitionContent();
            updateNotificationDefinitionContent.id = Objects.requireNonNull(id);
            updateNotificationDefinitionContent.title = title;
            updateNotificationDefinitionContent.templateParameter = templateParameter;
            return updateNotificationDefinitionContent;
        }
    }

}
