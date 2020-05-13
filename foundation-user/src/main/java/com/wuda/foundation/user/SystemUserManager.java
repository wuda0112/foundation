package com.wuda.foundation.user;


import com.wuda.foundation.lang.CRUDEntityState;

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
    private UserPrincipal rootUserPrincipal = new UsernamePrincipal("root");

    /**
     * 初始化root用户.应用程序在第一次启动时应该执行该方法,为系统初始化root用户,以后就不需要再重复执行了.
     */
    public void initRootUser() {
        boolean exists = userManager.exists(rootUserPrincipal);
        if (!exists) {
            UserAccount userAccount = newRootUserAccount();
            userManager.addUser(userAccount, BuiltinUserType.ZERO, BuiltinUserStatus.ZERO);
        }
    }

    /**
     * 新建一个root用户的账号信息.
     *
     * @return root用户账号
     */
    private UserAccount newRootUserAccount() {
        return new UserAccount.Builder(CRUDEntityState.CREATE, null)
                .addPrincipal(rootUserPrincipal)
                .password("123456")
                .userId(1L)
                .userAccountId(1L)
                .status(BuiltinUserAccountStatus.ZERO)
                .createUserId(1L)
                .build();
    }


}
