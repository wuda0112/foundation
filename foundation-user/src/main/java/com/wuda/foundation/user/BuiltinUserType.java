package com.wuda.foundation.user;

/**
 * 内置的用户类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinUserType implements UserType {

    ZERO(0, "系统内建的一个状态值,业务不可使用,就好像系统保留的关键字一样,表示系统用户");

    private int status;
    private String desc;

    BuiltinUserType(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }


    @Override
    public int get() {
        return status;
    }

    /**
     * 是否内建的用户.
     *
     * @param userType user type
     * @return <code>true</code>-如果是系统内建的用户
     */
    public static boolean builtinUserType(UserType userType) {
        int type = userType.get();
        BuiltinUserType[] array = BuiltinUserType.values();
        for (BuiltinUserType element : array) {
            if (element.get() == type) {
                return true;
            }
        }
        return false;
    }
}
