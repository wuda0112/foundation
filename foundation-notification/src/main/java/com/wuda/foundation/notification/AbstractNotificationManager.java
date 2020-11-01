package com.wuda.foundation.notification;

import com.wuda.foundation.commons.GroupManager;
import com.wuda.foundation.commons.TreeManager;
import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

public abstract class AbstractNotificationManager implements NotificationManager {
    @Override
    public CreateResult createCategory(TreeManager treeManager, GroupManager groupManager, CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException {
        treeManager.createNode(createNotificationCategory.toCreateTreeNode(), CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        groupManager.createGroup(createNotificationCategory.toCreateGroup(), CreateMode.CREATE_AFTER_SELECT_CHECK, opUserId);
        return createCategoryDbOp(treeManager, groupManager, createNotificationCategory, opUserId);
    }

    protected abstract CreateResult createCategoryDbOp(TreeManager treeManager, GroupManager groupManager, CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException;

    @Override
    public void updateCategory(TreeManager treeManager, UpdateNotificationCategory updateNotificationCategory, Long opUserId) throws AlreadyExistsException {
        updateCategoryDbOp(treeManager, updateNotificationCategory, opUserId);
    }

    protected abstract void updateCategoryDbOp(TreeManager treeManager, UpdateNotificationCategory updateNotificationCategory, Long opUserId) throws AlreadyExistsException;

    @Override
    public CreateResult createDefinitionContent(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId) {
        return createDefinitionContentDbOp(createNotificationDefinitionContent, opUserId);
    }

    protected abstract CreateResult createDefinitionContentDbOp(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId);

    @Override
    public void updateDefinitionContent(UpdateNotificationDefinitionContent updateNotificationDefinitionContent, Long opUserId) {
        updateDefinitionContentDbOp(updateNotificationDefinitionContent, opUserId);
    }

    protected abstract void updateDefinitionContentDbOp(UpdateNotificationDefinitionContent updateNotificationDefinitionContent, Long opUserId);

    @Override
    public CreateResult createDefinitionCore(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId) {
        return createDefinitionCoreDbOp(createNotificationDefinitionCore, opUserId);
    }

    protected abstract CreateResult createDefinitionCoreDbOp(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId);

    @Override
    public void updateDefinitionCore(UpdateNotificationDefinitionCore updateNotificationDefinitionCore, Long opUserId) {
        updateDefinitionCoreDbOp(updateNotificationDefinitionCore, opUserId);
    }

    protected abstract void updateDefinitionCoreDbOp(UpdateNotificationDefinitionCore updateNotificationDefinitionCore, Long opUserId);

    @Override
    public CreateResult createDefinitionObserver(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId) {
        return createDefinitionObserverDbOp(createNotificationDefinitionObserver, opUserId);
    }

    protected abstract CreateResult createDefinitionObserverDbOp(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId);

    @Override
    public CreateResult createDefinitionSendMethod(CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod, Long opUserId) {
        return createDefinitionSendMethodDbOp(createNotificationDefinitionSendMethod, opUserId);
    }

    protected abstract CreateResult createDefinitionSendMethodDbOp(CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod, Long opUserId);

    @Override
    public void updateSendMethod(UpdateNotificationSendMethod updateNotificationSendMethod, Long opUserId) throws AlreadyExistsException {
        updateSendMethodDbOp(updateNotificationSendMethod, opUserId);
    }

    protected abstract void updateSendMethodDbOp(UpdateNotificationSendMethod updateNotificationSendMethod, Long opUserId) throws AlreadyExistsException;

    @Override
    public CreateResult createSendMethod(CreateNotificationSendMethod createNotificationSendMethod, Long opUserId) throws AlreadyExistsException {
        return createSendMethodDbOp(createNotificationSendMethod, opUserId);
    }

    protected abstract CreateResult createSendMethodDbOp(CreateNotificationSendMethod createNotificationSendMethod, Long opUserId) throws AlreadyExistsException;

    @Override
    public CreateResult createTemplate(CreateNotificationTemplate createNotificationTemplate, Long opUserId) {
        return createTemplateDbOp(createNotificationTemplate, opUserId);
    }

    protected abstract CreateResult createTemplateDbOp(CreateNotificationTemplate createNotificationTemplate, Long opUserId);

    @Override
    public void updateTemplate(UpdateNotificationTemplate updateNotificationTemplate, Long opUserId) {
        updateTemplateDbOp(updateNotificationTemplate, opUserId);
    }

    protected abstract void updateTemplateDbOp(UpdateNotificationTemplate updateNotificationTemplate, Long opUserId);
}
