package com.wuda.foundation.user;


import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.Identifier;

/**
 * 管理系统用户.
 *
 * @author wuda
 * @since 1.0.0
 */
public class SystemUserManager {

    private UserManager userManager;

    /**
     * root user principal.
     */
    private Identifier rootUserIdentifier = new UsernameIdentifier("root");

    /**
     * 初始化root用户.应用程序在第一次启动时应该执行该方法,为系统初始化root用户,以后就不需要再重复执行了.
     */
    public void initRootUser() {
        boolean exists = userManager.exists(rootUserIdentifier);
        if (!exists) {
            UserAccount userAccount = newRootUserAccount();
        }
    }

    /**
     * 新建一个root用户的账号信息.
     *
     * @return root用户账号
     */
    private UserAccount newRootUserAccount() {
        return new UserAccount.Builder(CRUDEntityState.CREATE, null)
                .password("123456")
                .userId(1L)
                .userAccountId(1L)
                .status(BuiltinUserAccountState.ZERO)
                .createUserId(1L)
                .build();
    }


}
