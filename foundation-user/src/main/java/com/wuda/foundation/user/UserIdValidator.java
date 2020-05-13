package com.wuda.foundation.user;

import com.wuda.foundation.lang.IdValidator;

/**
 * 用户ID校验器.
 *
 * @author wuda
 */
public class UserIdValidator extends IdValidator {

    private UserIdValidator() {
        super();
    }

    /**
     * 检查用户相关的数据的id.
     *
     * @param userType user type
     * @param id       记录的唯一id
     */
    public static void userRelatedIdValidate(UserType userType, long id) {
        if (BuiltinUserType.builtinUserType(userType)) {
            // 系统用户可以使用保留的id
            IdValidator.notEqualsZero(id);
        } else {
            IdValidator.notEqualsZeroAndGreaterThanMin(id);
        }
    }
}
