package com.wuda.foundation.security;

/**
 * 内置的permission target类型.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum BuiltinPermissionTargetType implements PermissionTargetType {

    ZERO(0, "MOCK");

    private int code;
    private String description;

    BuiltinPermissionTargetType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static {
        BuiltinPermissionTargetType[] types = BuiltinPermissionTargetType.values();
        for (BuiltinPermissionTargetType type : types) {
            type.register();
        }
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
