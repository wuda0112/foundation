package com.wuda.foundation.notification;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateResult;

public abstract class AbstractNotificationManager implements NotificationManager {
    @Override
    public CreateResult createCategory(CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException {
        return createCategoryDbOp(createNotificationCategory, opUserId);
    }

    protected abstract CreateResult createCategoryDbOp(CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException;

    @Override
    public CreateResult createDefinitionContent(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId) {
        return createDefinitionContentDbOp(createNotificationDefinitionContent, opUserId);
    }

    protected abstract CreateResult createDefinitionContentDbOp(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId);

    @Override
    public CreateResult createDefinitionCore(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId) {
        return createDefinitionCoreDbOp(createNotificationDefinitionCore, opUserId);
    }

    protected abstract CreateResult createDefinitionCoreDbOp(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId);

    @Override
    public CreateResult createDefinitionObserver(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId) {
        return createDefinitionObserverDbOp(createNotificationDefinitionObserver, opUserId);
    }

    protected abstract CreateResult createDefinitionObserverDbOp(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId);

    @Override
    public CreateResult createDefinitionPostMethod(CreateNotificationDefinitionPostMethod createNotificationDefinitionPostMethod, Long opUserId) {
        return createDefinitionPostMethodDbOp(createNotificationDefinitionPostMethod, opUserId);
    }

    protected abstract CreateResult createDefinitionPostMethodDbOp(CreateNotificationDefinitionPostMethod createNotificationDefinitionPostMethod, Long opUserId);

    @Override
    public CreateResult createPostMethod(CreateNotificationPostMethod createNotificationPostMethod, Long opUserId) throws AlreadyExistsException {
        return createPostMethodDbOp(createNotificationPostMethod, opUserId);
    }

    protected abstract CreateResult createPostMethodDbOp(CreateNotificationPostMethod createNotificationPostMethod, Long opUserId) throws AlreadyExistsException;

    @Override
    public CreateResult createTemplate(CreateNotificationTemplate createNotificationTemplate, Long opUserId) {
        return createTemplateDbOp(createNotificationTemplate, opUserId);
    }

    protected abstract CreateResult createTemplateDbOp(CreateNotificationTemplate createNotificationTemplate, Long opUserId);
}
