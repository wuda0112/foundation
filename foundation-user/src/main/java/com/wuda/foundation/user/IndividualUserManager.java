package com.wuda.foundation.user;


import com.wuda.foundation.lang.AlreadyExistsException;

/**
 * 个人用户管理.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface IndividualUserManager {

    /**
     * 为用户添加profile,每个用户只有一个profile,因此必须检查该用户是否已经存在profile,
     * 只有当不存在是才能添加,否则抛出异常.
     *
     * @return 新增的基本信息的id
     * @throws AlreadyExistsException 该用户已经拥有基本信息
     */
    long addGeneral(IndividualUserGeneral general) throws AlreadyExistsException;

}
