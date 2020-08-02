package com.wuda.foundation.user;


import com.wuda.foundation.lang.InsertMode;
import com.wuda.foundation.lang.SingleInsertResult;

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
     * @param insertMode                  insert mode
     * @param opUserId                    操作人用户ID
     * @return 创建的结果
     */
    SingleInsertResult createGeneral(CreateIndividualUserGeneral createIndividualUserGeneral, InsertMode insertMode, Long opUserId);

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
