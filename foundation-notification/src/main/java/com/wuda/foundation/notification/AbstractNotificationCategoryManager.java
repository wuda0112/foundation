package com.wuda.foundation.notification;

import com.wuda.foundation.commons.AbstractTreeManager;

public abstract class AbstractNotificationCategoryManager extends AbstractTreeManager<CreateNotificationCategory, UpdateNotificationCategory, DescribeNotificationCategory> implements NotificationCategoryManager {

    @Override
    public int childCount(Long nodeId) {
        return 0;
    }
}
