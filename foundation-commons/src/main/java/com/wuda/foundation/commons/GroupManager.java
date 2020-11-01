package com.wuda.foundation.commons;

import com.wuda.foundation.lang.AlreadyExistsException;
import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

/**
 * group manager.
 *
 * @author wuda
 * @since 1.0.3
 */
public interface GroupManager {

    /**
     * 创建group core.
     *
     * @param createGroup 参数
     * @param createMode  create mode
     * @param opUserId    操作者用户ID
     * @return 创建结果
     * @throws AlreadyExistsException 如果已经存在
     */
    CreateResult createGroup(CreateGroup createGroup, CreateMode createMode, Long opUserId) throws AlreadyExistsException;
}
