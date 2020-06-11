package com.wuda.foundation.user;

/**
 * 内置的用户类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinUserType implements UserType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinUserType(int code, String description) {
        this.code = code;
        this.description = description;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 是否内建的用户.
     *
     * @param userType user type
     * @return <code>true</code>-如果是系统内建的用户
     */
    public static boolean builtinUserType(UserType userType) {
        int code = userType.getCode();
        BuiltinUserType[] array = BuiltinUserType.values();
        for (BuiltinUserType element : array) {
            if (element.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
