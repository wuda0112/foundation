package com.wuda.foundation.notification.impl;

import com.wuda.foundation.commons.GroupManager;
import com.wuda.foundation.commons.TreeManager;
import com.wuda.foundation.jooq.JooqCommonDbOp;
import com.wuda.foundation.jooq.JooqContext;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateResult;
import com.wuda.foundation.lang.IsDeleted;
import com.wuda.foundation.lang.identify.LongIdentifier;
import com.wuda.foundation.notification.*;
import com.wuda.foundation.notification.impl.jooq.generation.tables.records.*;
import org.jooq.types.UByte;
import org.jooq.types.ULong;

import java.time.LocalDateTime;

import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationCategory.NOTIFICATION_CATEGORY;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationDefinitionContent.NOTIFICATION_DEFINITION_CONTENT;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationDefinitionCore.NOTIFICATION_DEFINITION_CORE;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationDefinitionObserver.NOTIFICATION_DEFINITION_OBSERVER;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationDefinitionSendMethod.NOTIFICATION_DEFINITION_SEND_METHOD;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationSendMethod.NOTIFICATION_SEND_METHOD;
import static com.wuda.foundation.notification.impl.jooq.generation.tables.NotificationTemplate.NOTIFICATION_TEMPLATE;

public class NotificationManagerImpl extends AbstractNotificationManager implements JooqCommonDbOp {

    @Override
    protected CreateResult createCategoryDbOp(TreeManager treeManager, GroupManager groupManager, CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException {
        NotificationCategoryRecord record = notificationCategoryRecordForInsert(createNotificationCategory, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_CATEGORY, record);
    }

    @Override
    protected void updateCategoryDbOp(TreeManager treeManager, UpdateNotificationCategory updateNotificationCategory, Long opUserId) throws AlreadyExistsException {
        NotificationCategoryRecord record = notificationCategoryRecordForUpdate(updateNotificationCategory, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected CreateResult createDefinitionContentDbOp(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId) {
        NotificationDefinitionContentRecord record = notificationDefinitionContentRecordForInsert(createNotificationDefinitionContent, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_DEFINITION_CONTENT, record);
    }

    @Override
    protected void updateDefinitionContentDbOp(UpdateNotificationDefinitionContent updateNotificationDefinitionContent, Long opUserId) {
        NotificationDefinitionContentRecord record = notificationDefinitionContentRecordForUpdate(updateNotificationDefinitionContent, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected CreateResult createDefinitionCoreDbOp(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId) {
        NotificationDefinitionCoreRecord record = notificationDefinitionCoreRecordForInsert(createNotificationDefinitionCore, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_DEFINITION_CORE, record);
    }

    @Override
    protected void updateDefinitionCoreDbOp(UpdateNotificationDefinitionCore updateNotificationDefinitionCore, Long opUserId) {
        NotificationDefinitionCoreRecord record = notificationDefinitionCoreRecordForUpdate(updateNotificationDefinitionCore, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected CreateResult createDefinitionObserverDbOp(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId) {
        NotificationDefinitionObserverRecord record = notificationDefinitionObserverRecordForInsert(createNotificationDefinitionObserver, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_DEFINITION_OBSERVER, record);
    }

    @Override
    protected CreateResult createDefinitionSendMethodDbOp(CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod, Long opUserId) {
        NotificationDefinitionSendMethodRecord record = notificationDefinitionSendMethodRecordForInsert(createNotificationDefinitionSendMethod, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_DEFINITION_SEND_METHOD, record);
    }

    @Override
    protected void updateSendMethodDbOp(UpdateNotificationSendMethod updateNotificationSendMethod, Long opUserId) throws AlreadyExistsException {
        NotificationSendMethodRecord record = notificationSendMethodRecordForUpdate(updateNotificationSendMethod, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    @Override
    protected CreateResult createSendMethodDbOp(CreateNotificationSendMethod createNotificationSendMethod, Long opUserId) throws AlreadyExistsException {
        NotificationSendMethodRecord record = notificationSendMethodRecordForInsert(createNotificationSendMethod, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_SEND_METHOD, record);
    }

    @Override
    protected CreateResult createTemplateDbOp(CreateNotificationTemplate createNotificationTemplate, Long opUserId) {
        NotificationTemplateRecord record = notificationTemplateRecordForInsert(createNotificationTemplate, opUserId);
        return insert(JooqContext.getDataSource(), NOTIFICATION_TEMPLATE, record);
    }

    @Override
    protected void updateTemplateDbOp(UpdateNotificationTemplate updateNotificationTemplate, Long opUserId) {
        NotificationTemplateRecord record = notificationTemplateRecordForUpdate(updateNotificationTemplate, opUserId);
        updateSelectiveByPrimaryKey(JooqContext.getDataSource(), record);
    }

    private NotificationCategoryRecord notificationCategoryRecordForInsert(CreateNotificationCategory createNotificationCategory, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationCategoryRecord(ULong.valueOf(createNotificationCategory.getId()),
                ULong.valueOf(createNotificationCategory.getParentCategoryId()),
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

    private NotificationDefinitionContentRecord notificationDefinitionContentRecordForInsert(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationDefinitionContentRecord(ULong.valueOf(createNotificationDefinitionContent.getId()),
                ULong.valueOf(createNotificationDefinitionContent.getNotificationDefinitionId()),
                ULong.valueOf(createNotificationDefinitionContent.getNotificationDefinitionSendMethodId()),
                createNotificationDefinitionContent.getTitle(),
                createNotificationDefinitionContent.getTemplateParameter(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationDefinitionContentRecord notificationDefinitionContentRecordForUpdate(UpdateNotificationDefinitionContent updateNotificationDefinitionContent, Long opUserId) {
        NotificationDefinitionContentRecord record = new NotificationDefinitionContentRecord();
        record.setNotificationDefinitionContentId(ULong.valueOf(updateNotificationDefinitionContent.getId()));
        record.setTitle(updateNotificationDefinitionContent.getTitle());
        record.setTemplateParameter(updateNotificationDefinitionContent.getTemplateParameter());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        record.setLastModifyTime(LocalDateTime.now());
        return record;
    }

    private NotificationDefinitionCoreRecord notificationDefinitionCoreRecordForInsert(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationDefinitionCoreRecord(ULong.valueOf(createNotificationDefinitionCore.getId()),
                ULong.valueOf(createNotificationDefinitionCore.getNotificationDefinitionId()),
                createNotificationDefinitionCore.getName(), createNotificationDefinitionCore.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationDefinitionCoreRecord notificationDefinitionCoreRecordForUpdate(UpdateNotificationDefinitionCore updateNotificationDefinitionCore, Long opUserId) {
        NotificationDefinitionCoreRecord record = new NotificationDefinitionCoreRecord();
        record.setNotificationDifinitionCoreId(ULong.valueOf(updateNotificationDefinitionCore.getId()));
        record.setName(updateNotificationDefinitionCore.getName());
        record.setDescription(updateNotificationDefinitionCore.getDescription());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        record.setLastModifyTime(LocalDateTime.now());
        return record;
    }

    private NotificationDefinitionObserverRecord notificationDefinitionObserverRecordForInsert(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId) {
        LongIdentifier observer = createNotificationDefinitionObserver.getObserver();
        LocalDateTime now = LocalDateTime.now();
        return new NotificationDefinitionObserverRecord(ULong.valueOf(createNotificationDefinitionObserver.getId()),
                ULong.valueOf(createNotificationDefinitionObserver.getNotificationDefinitionId()),
                ULong.valueOf(createNotificationDefinitionObserver.getNotificationDefinitionSendMethodId()),
                UByte.valueOf(observer.getType().getCode()),
                ULong.valueOf(observer.getValue()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue())
        );
    }

    private NotificationDefinitionSendMethodRecord notificationDefinitionSendMethodRecordForInsert(CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationDefinitionSendMethodRecord(ULong.valueOf(createNotificationDefinitionSendMethod.getId()),
                ULong.valueOf(createNotificationDefinitionSendMethod.getNotificationDefinitionId()),
                ULong.valueOf(createNotificationDefinitionSendMethod.getNotificationSendMethodId()),
                ULong.valueOf(createNotificationDefinitionSendMethod.getNotificationTemplateId()),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationSendMethodRecord notificationSendMethodRecordForInsert(CreateNotificationSendMethod createNotificationSendMethod, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationSendMethodRecord(ULong.valueOf(createNotificationSendMethod.getId()),
                createNotificationSendMethod.getName(),
                createNotificationSendMethod.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationSendMethodRecord notificationSendMethodRecordForUpdate(UpdateNotificationSendMethod updateNotificationSendMethod, Long opUserId) {
        NotificationSendMethodRecord record = new NotificationSendMethodRecord();
        record.setNotificationSendMethodId(ULong.valueOf(updateNotificationSendMethod.getId()));
        record.setName(updateNotificationSendMethod.getName());
        record.setDescription(updateNotificationSendMethod.getDescription());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        record.setLastModifyTime(LocalDateTime.now());
        return record;
    }

    private NotificationTemplateRecord notificationTemplateRecordForInsert(CreateNotificationTemplate createNotificationTemplate, Long opUserId) {
        LocalDateTime now = LocalDateTime.now();
        return new NotificationTemplateRecord(ULong.valueOf(createNotificationTemplate.getId()),
                createNotificationTemplate.getName(),
                createNotificationTemplate.getContent(),
                createNotificationTemplate.getDescription(),
                now, ULong.valueOf(opUserId), now, ULong.valueOf(opUserId), ULong.valueOf(IsDeleted.NO.getValue()));
    }

    private NotificationTemplateRecord notificationTemplateRecordForUpdate(UpdateNotificationTemplate updateNotificationTemplate, Long opUserId) {
        NotificationTemplateRecord record = new NotificationTemplateRecord();
        record.setNotificationTemplateId(ULong.valueOf(updateNotificationTemplate.getId()));
        record.setName(updateNotificationTemplate.getName());
        record.setContent(updateNotificationTemplate.getContent());
        record.setDescription(updateNotificationTemplate.getDescription());
        record.setLastModifyUserId(ULong.valueOf(opUserId));
        record.setLastModifyTime(LocalDateTime.now());
        return record;
    }
}
