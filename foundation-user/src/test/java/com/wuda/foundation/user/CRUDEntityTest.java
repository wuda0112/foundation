package com.wuda.foundation.user;

import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.Constant;
import org.junit.Test;

public class CRUDEntityTest {

    private UserAccount getUserAccount() {
        return new UserAccount.Builder(CRUDEntityState.UPDATE, new NoOpUserAccountValidator())
                .password("123456")
                .userId(1L)
                .userType(BuiltinUserType.ZERO)
                .userAccountId(1L)
                .status(BuiltinUserAccountState.ZERO)
                .lastModifyUserId(1L)
                .lastModifyTime(Constant.EPOCH + 1)
                .build();
    }

    @Test
    public void buildUserAccountTest() {
        UserAccount userAccount = getUserAccount();
        System.out.println(userAccount);
    }

    @Test
    public void buildUserTest() {
        UserAccount userAccount = getUserAccount();
        User user = new User.Builder(CRUDEntityState.CREATE)
                .userId(1L)
                .userType(BuiltinUserType.ZERO)
                .userStatus(BuiltinUserState.ZERO)
                .userAccount(userAccount)
                .createUserId(1L)
                .createTime(Constant.EPOCH + 1)
                .build();
        System.out.println(user);
    }

    @Test
    public void buildIndividualUserGeneralTest() {
        IndividualUserGeneral general = new IndividualUserGeneral.Builder(CRUDEntityState.CREATE)
                .individualUserProfileId(1001L)
                .userType(BuiltinUserType.ZERO)
                .userId(1L)
                .nickname("nickname")
                .biography("biography")
                .createUserId(1L)
                .createTime(Constant.EPOCH + 1)
                .build();
        System.out.println(general.getUserId());
    }
}
