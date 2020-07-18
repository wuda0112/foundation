package com.wuda.foundation.lang.identify;

public enum BuiltinIdentifierType implements IdentifierType {
    /**
     * MOCK.
     */
    MOCK(0, "MOCK"),
    /**
     * 用户名作为唯一标记.
     */
    USERNAME(1, "username"),
    /**
     * 手机号码作为唯一标记.
     */
    MOBILE_PHONE(2, "mobile_phone"),
    /**
     * 邮箱作为唯一标记.
     */
    EMAIL(3, "email");

    private int code;
    private String description;

    BuiltinIdentifierType(int code, String description) {
        this.code = code;
        this.description = description;
        register();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
