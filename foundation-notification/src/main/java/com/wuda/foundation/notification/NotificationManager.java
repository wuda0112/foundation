package com.wuda.foundation.notification;

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
     * 创建分类.
     *
     * @param createNotificationCategory 创建分类的参数
     * @param opUserId                   操作人用户ID
     * @return 创建结果
     * @throws AlreadyExistsException 如果已经存在给定名称的分类
     */
    CreateResult createCategory(CreateNotificationCategory createNotificationCategory, Long opUserId) throws AlreadyExistsException;

    /**
     * 创建通知定义的内容.
     *
     * @param createNotificationDefinitionContent 创建通知定义的内容的参数
     * @param opUserId                            操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionContent(CreateNotificationDefinitionContent createNotificationDefinitionContent, Long opUserId);

    /**
     * 创建通知定义的核心.
     *
     * @param createNotificationDefinitionCore 创建通知定义的核心的参数
     * @param opUserId                         操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionCore(CreateNotificationDefinitionCore createNotificationDefinitionCore, Long opUserId);

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
     * @param createNotificationDefinitionPostMethod 创建通知定义的发布方式的参数
     * @param opUserId                               操作人用户ID
     * @return 创建结果
     */
    CreateResult createDefinitionPostMethod(CreateNotificationDefinitionPostMethod createNotificationDefinitionPostMethod, Long opUserId);

    /**
     * 创建发布方式.
     *
     * @param createNotificationPostMethod 创建发布方式的参数
     * @param opUserId                     操作人用户ID
     * @return 创建结果
     * @throws AlreadyExistsException 如果已经存在给定的发布方式
     */
    CreateResult createPostMethod(CreateNotificationPostMethod createNotificationPostMethod, Long opUserId) throws AlreadyExistsException;

    /**
     * 创建模板.
     *
     * @param createNotificationTemplate 创建模板的参数
     * @param opUserId                   操作人用户ID
     * @return 创建结果
     */
    CreateResult createTemplate(CreateNotificationTemplate createNotificationTemplate, Long opUserId);
}
