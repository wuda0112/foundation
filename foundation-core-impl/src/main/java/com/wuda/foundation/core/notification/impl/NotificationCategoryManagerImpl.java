package com.wuda.foundation.core.notification.impl;

import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.RelatedDataExists;
import com.wuda.foundation.core.notification.AbstractNotificationCategoryManager;
import com.wuda.foundation.core.notification.CreateNotificationCategory;
import com.wuda.foundation.core.notification.DescribeNotificationCategory;
import com.wuda.foundation.core.notification.UpdateNotificationCategory;
import com.wuda.foundation.jooq.code.generation.notification.tables.records.NotificationCategoryRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wuda.foundation.jooq.code.generation.notification.tables.NotificationCategory.NOTIFICATION_CATEGORY;

public class NotificationCategoryManagerImpl extends AbstractNotificationCategoryManager implements JooqCommonDbOp {
    @Override
    protected CreateResult createTreeNodeDbOp(CreateNotificationCategory createTreeNode, CreateMode createMode, Long opUserId) {
        NotificationCategoryRecord record = notificationCategoryRecordForInsert(createTreeNode, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_CATEGORY, record);
    }

    @Override
    protected void updateTreeNodeDbOp(UpdateNotificationCategory updateTreeNode, Long opUserId) {
        NotificationCategoryRecord record = notificationCategoryRecordForUpdate(updateTreeNode, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected void deleteTreeNodeDbOp(Long nodeId, Long opUserId) throws RelatedDataExists {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource());
        dslContext.update(NOTIFICATION_CATEGORY)
                .set(NOTIFICATION_CATEGORY.IS_DELETED, NOTIFICATION_CATEGORY.NOTIFICATION_CATEGORY_ID)
                .set(NOTIFICATION_CATEGORY.LAST_MODIFY_USER_ID, ULong.valueOf(opUserId))
                .set(NOTIFICATION_CATEGORY.LAST_MODIFY_TIME, LocalDateTime.now())
                .where(NOTIFICATION_CATEGORY.NOTIFICATION_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .execute();
    }

    private NotificationCategoryRecord notificationCategoryRecordForInsert(CreateNotificationCategory createNotificationCategory, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationCategoryRecord(ULong.valueOf(createNotificationCategory.getId()),
                ULong.valueOf(createNotificationCategory.getParentId()),
                ULong.valueOf(createNotificationCategory.getRootId()),
                UByte.valueOf(createNotificationCategory.getDepth()),
                createNotificationCategory.getName(),
                createNotificationCategory.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationCategoryRecord notificationCategoryRecordForUpdate(UpdateNotificationCategory updateNotificationCategory, Long opUserId) {
        NotificationCategoryRecord record = new NotificationCategoryRecord();
        record.setNotificationCategoryId(ULong.valueOf(updateNotificationCategory.getId()));
        record.setName(updateNotificationCategory.getName());
        record.setDescription(updateNotificationCategory.getDescription());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        record.setLastModifyTime(LocalDateTime.now());
        return record;
    }

    @Override
    public int childCount(Long nodeId) {
        return JooqContext.getOrCreateDSLContext(JooqContext.getDataSource())
                .fetchCount(NOTIFICATION_CATEGORY,
                        NOTIFICATION_CATEGORY.PARENT_NOTIFICATION_CATEGORY_ID.eq(ULong.valueOf(nodeId))
                                .and(NOTIFICATION_CATEGORY.IS_DELETED.eq(notDeleted())));
    }

    @Override
    public List<DescribeNotificationCategory> getChildren(Long nodeId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<NotificationCategoryRecord> records = dslContext.selectFrom(NOTIFICATION_CATEGORY)
                .where(NOTIFICATION_CATEGORY.PARENT_NOTIFICATION_CATEGORY_ID.eq(ULong.valueOf(nodeId)))
                .and(NOTIFICATION_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromNotificationCategoryRecords(records);
    }

    @Override
    public DescribeNotificationCategory getTreeNode(Long nodeId) {
        NotificationCategoryRecord record = getById(nodeId, NOTIFICATION_CATEGORY);
        return copyFromNotificationCategoryRecord(record);
    }

    @Override
    public List<DescribeNotificationCategory> getDescendantOfRoot(Long rootId) {
        DSLContext dslContext = JooqContext.getOrCreateDSLContext();
        Result<NotificationCategoryRecord> records = dslContext.selectFrom(NOTIFICATION_CATEGORY)
                .where(NOTIFICATION_CATEGORY.ROOT_NOTIFICATION_CATEGORY_ID.eq(ULong.valueOf(rootId)))
                .and(NOTIFICATION_CATEGORY.IS_DELETED.eq(notDeleted()))
                .fetch();
        return copyFromNotificationCategoryRecords(records);
    }

    @Override
    public boolean checkNameExists(Long parentId, String childName) {
        int count = JooqContext.getOrCreateDSLContext(JooqContext.getDataSource())
                .fetchCount(NOTIFICATION_CATEGORY,
                        NOTIFICATION_CATEGORY.PARENT_NOTIFICATION_CATEGORY_ID.eq(ULong.valueOf(parentId))
                                .and(NOTIFICATION_CATEGORY.NAME.eq(childName))
                                .and(NOTIFICATION_CATEGORY.IS_DELETED.eq(notDeleted())));
        return count > 0;
    }

    private DescribeNotificationCategory copyFromNotificationCategoryRecord(NotificationCategoryRecord record) {
        DescribeNotificationCategory describeNotificationCategory = new DescribeNotificationCategory();
        describeNotificationCategory.setId(record.getNotificationCategoryId().longValue());
        describeNotificationCategory.setParentId(record.getParentNotificationCategoryId().longValue());
        describeNotificationCategory.setRootId(record.getRootNotificationCategoryId().longValue());
        describeNotificationCategory.setDepth(record.getDepth().intValue());
        describeNotificationCategory.setName(record.getName());
        describeNotificationCategory.setDescription(record.getDescription());
        return describeNotificationCategory;
    }

    private List<DescribeNotificationCategory> copyFromNotificationCategoryRecords(Result<NotificationCategoryRecord> records) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        List<DescribeNotificationCategory> list = new ArrayList<>();
        for (NotificationCategoryRecord record : records) {
            list.add(copyFromNotificationCategoryRecord(record));
        }
        return list;
    }
}
