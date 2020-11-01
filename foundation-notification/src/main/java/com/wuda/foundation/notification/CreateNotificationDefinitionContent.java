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
public class CreateNotificationDefinitionContent {

    private Long id;
    private Long notificationDefinitionId;
    private Long notificationDefinitionSendMethodId;
    /**
     * 声明通知的标题
     */
    private String title;
    /**
     * 模板参数，使用模板后生成具体的通知内容。为什么要使用模板参数，
     * 而不直接使用通知内容呢？因为在有些场景下，会先定义通知模板，
     * 比如像短信，短信服务商（比如阿里云的短信发送服务），就是先定义模板；
     * 而有些场景可能是直接写好标题和内容，就直接发送了，为了兼容这两种情况，
     * 使用模板的方式，因为通过模板和模板参数就可以生成消息内容，
     * 对于那些不使用模板的通知，我们只需要内置一个模板，固定下来参数的名称，
     * 然后把通知内容保存时，使用这个参数作为key，通知内容作为value，
     * 就可以生成数据库需要的模板参数了。
     */
    private String templateParameter;

    /**
     * for build {@link CreateNotificationDefinitionContent}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateNotificationDefinitionContent> {

        private Long id;
        private Long notificationDefinitionId;
        private Long notificationDefinitionSendMethodId;
        private String title;
        private String templateParameter;

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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTemplateParameter(String templateParameter) {
            this.templateParameter = templateParameter;
            return this;
        }

        @Override
        public CreateNotificationDefinitionContent build() {
            CreateNotificationDefinitionContent createNotificationDefinitionContent = new CreateNotificationDefinitionContent();
            createNotificationDefinitionContent.id = Objects.requireNonNull(id);
            createNotificationDefinitionContent.notificationDefinitionId = Objects.requireNonNull(notificationDefinitionId);
            createNotificationDefinitionContent.notificationDefinitionSendMethodId = Objects.requireNonNull(notificationDefinitionSendMethodId);
            createNotificationDefinitionContent.title = Objects.requireNonNull(title);
            createNotificationDefinitionContent.templateParameter = Objects.requireNonNull(templateParameter);
            return createNotificationDefinitionContent;
        }
    }

}
