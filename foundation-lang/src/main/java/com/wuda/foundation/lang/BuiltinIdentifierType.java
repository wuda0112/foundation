package com.wuda.foundation.lang;

public enum BuiltinIdentifierType implements IdentifierType {
    /**
     * 使用用户名作为用户唯一标记.
     */
    USERNAME(0, "username"),
    /**
     * 使用手机号码作为用户唯一标记.
     */
    MOBILE_PHONE(1, "mobile_phone"),
    /**
     * 使用邮箱作为用户唯一标记.
     */
    EMAIL(2, "email");

    private int code;
    private String description;

    BuiltinIdentifierType(int code, String description) {
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
}
