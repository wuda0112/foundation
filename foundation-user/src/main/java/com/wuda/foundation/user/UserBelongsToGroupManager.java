package com.wuda.foundation.user;

/**
 * 用户与组的关系的管理.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface UserBelongsToGroupManager {

    /**
     * 用户加入组中.
     *
     * @param userJoinGroupRequest 请求参数
     * @param opUserId             操作人用户ID
     */
    void userJoinGroup(UserJoinGroupRequest userJoinGroupRequest, Long opUserId);
}
