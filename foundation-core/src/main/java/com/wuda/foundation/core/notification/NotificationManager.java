package com.wuda.foundation.core.notification;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateResult;

/**
 * 通知管理.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface NotificationManager {

    /**
     * 创建通知定义的内容.
     *
     * @param createNotificationDefinitionContent 创建通知定义的内容的参数
     * @param opUserId                            操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionContent(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId);

    /**
     * 更新通知定义的内容.
     *
     * @param updateNotificationDefinitionContent 更新通知定义的内容的参数
     * @param opUserId                            操作人用户ID
     */
    void updateDefinitionContent(UpdateNotificationDefinitionContent updateNotificationDefinitionContent, Long opUserId);

    /**
     * 创建通知定义的核心.
     *
     * @param createNotificationDefinitionCore 创建通知定义的核心的参数
     * @param opUserId                         操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionCore(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId);

    /**
     * 更新通知定义的核心.
     *
     * @param updateNotificationDefinitionCore 更新通知定义的核心的参数
     * @param opUserId                         操作人用户ID
     */
    void updateDefinitionCore(UpdateNotificationDefinitionCore updateNotificationDefinitionCore, Long opUserId);

    /**
     * 创建通知定义的接收者.
     *
     * @param createNotificationDefinitionObserver 创建通知定义的接收者的参数
     * @param opUserId                             操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionObserver(CreateNotificationDefinitionObserver createNotificationDefinitionObserver, Long opUserId);

    /**
     * 创建通知定义的发布方式.
     *
     * @param createNotificationDefinitionSendMethod 创建通知定义的发布方式的参数
     * @param opUserId                               操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionSendMethod(CreateNotificationDefinitionSendMethod createNotificationDefinitionSendMethod, Long opUserId);

    /**
     * 更新发布方式.
     *
     * @param updateNotificationSendMethod 更新发布方式的参数
     * @param opUserId                     操作人用户ID
     * @throws AlreadyExistsException 如果已经存在给定的发布方式
     */
    void updateSendMethod(UpdateNotificationSendMethod updateNotificationSendMethod, Long opUserId) throws AlreadyExistsException;

    /**
     * 创建发布方式.
     *
     * @param createNotificationSendMethod 创建发布方式的参数
     * @param opUserId                     操作人用户ID
     * @return 创建结果
     * @throws AlreadyExistsException 如果已经存在给定的发布方式
     */
    CreateResult createSendMethod(CreateNotificationSendMethod createNotificationSendMethod, Long opUserId) throws AlreadyExistsException;

    /**
     * 创建模板.
     *
     * @param createNotificationTemplate 创建模板的参数
     * @param opUserId                   操作人用户ID
     * @return 创建结果
     */
    CreateResult createTemplate(CreateNotificationTemplate createNotificationTemplate, Long opUserId);

    /**
     * 更新模板.
     *
     * @param updateNotificationTemplate 更新模板的参数
     * @param opUserId                   操作人用户ID
     */
    void updateTemplate(UpdateNotificationTemplate updateNotificationTemplate, Long opUserId);
}
