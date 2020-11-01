package com.wuda.foundation.notification;

import com.wuda.foundation.commons.UpdateTreeNode;
import lombok.Getter;

import java.util.Objects;

/**
 * 更新notification category的实体参数.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateNotificationCategory extends UpdateTreeNode {

    private String name;
    private String description;

    /**
     * for build {@link UpdateNotificationCategory}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateNotificationCategory> {

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
        public UpdateNotificationCategory build() {
            UpdateNotificationCategory updateNotificationCategory = new UpdateNotificationCategory();
            updateNotificationCategory.id = Objects.requireNonNull(id);
            updateNotificationCategory.name = name;
            updateNotificationCategory.description = description;
            return updateNotificationCategory;
        }
    }

}
