package com.wuda.foundation.notification;

import lombok.Getter;

import java.util.Objects;

/**
 * 更新notification definition core的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateNotificationDefinitionCore {

    private Long id;
    /**
     * 更新名称.
     */
    private String name;
    /**
     * 更新对于该定义的描述.
     */
    private String description;

    /**
     * for build {@link UpdateNotificationDefinitionCore}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateNotificationDefinitionCore> {

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
        public UpdateNotificationDefinitionCore build() {
            UpdateNotificationDefinitionCore updateNotificationDefinitionCore = new UpdateNotificationDefinitionCore();
            updateNotificationDefinitionCore.id = Objects.requireNonNull(id);
            updateNotificationDefinitionCore.name = name;
            updateNotificationDefinitionCore.description = description;
            return updateNotificationDefinitionCore;
        }
    }

}
