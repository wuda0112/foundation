package com.wuda.foundation.core.user;


import com.wuda.foundation.lang.CreateMode;
import com.wuda.foundation.lang.CreateResult;

import java.util.List;

/**
 * 个人用户管理.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface IndividualUserManager {

    /**
     * 为用户新增基本信息.
     *
     * @param createIndividualUserGeneral 基本信息
     * @param createMode                  create mode
     * @param opUserId                    操作人用户ID
     * @return 创建的结果
     */
    CreateResult createGeneral(CreateIndividualUserGeneral createIndividualUserGeneral, CreateMode createMode, Long opUserId);

    /**
     * 为用户新增基本信息.
     *
     * @param createIndividualUserGenerals 基本信息
     * @param opUserId                     操作人用户ID
     */
    void directBatchInsertGeneral(List<CreateIndividualUserGeneral> createIndividualUserGenerals, Long opUserId);

    /**
     * 更新基本信息.
     *
     * @param updateIndividualUserGeneral 基本信息
     * @param opUserId                    操作人用户ID
     */
    void updateGeneral(UpdateIndividualUserGeneral updateIndividualUserGeneral, Long opUserId);

}
