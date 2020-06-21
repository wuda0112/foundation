package com.wuda.foundation.user;

import com.wuda.foundation.lang.CRUDState;
import com.wuda.foundation.lang.Constant;
import org.junit.Test;

public class CRUDEntityTest {
    @Test
    public void buildIndividualUserGeneralTest() {
        IndividualUserGeneral general = new IndividualUserGeneral.Builder(CRUDState.CREATE)
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
