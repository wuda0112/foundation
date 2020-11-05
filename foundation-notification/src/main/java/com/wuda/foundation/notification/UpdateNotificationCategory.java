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

    /**
     * for build {@link UpdateNotificationCategory}
     *
     * @author wuda
     * @since 1.0.3
     */
    public static class Builder extends UpdateTreeNodeBuilder<UpdateNotificationCategory, Builder> {

        @Override
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
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
