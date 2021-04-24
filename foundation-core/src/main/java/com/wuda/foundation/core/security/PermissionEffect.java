package com.wuda.foundation.core.security;

/**
 * allow or deny.
 *
 * @author wuda
 * @since 1.0.3
 */
public enum PermissionEffect {

    /**
     * allow.
     */
    ALLOW("allow"),
    /**
     * deny.
     */
    DENY("deny");

    public String getValueString() {
        return value;
    }

    private String value;

    PermissionEffect(String value) {
        this.value = value;
    }

    /**
     * 根据boolean解析.
     *
     * @param b to be parsed
     * @return <code>null</code>-如果没有匹配的
     */
    public static PermissionEffect parse(Boolean b) {
        if (b.equals(Boolean.TRUE)) {
            return PermissionEffect.ALLOW;
        }
        return PermissionEffect.DENY;
    }

    /**
     * 是否allow.
     *
     * @param permissionEffect allow or deny
     * @return <code>true</code> if {@link #ALLOW}
     */
    public static Boolean allow(PermissionEffect permissionEffect) {
        return permissionEffect.equals(ALLOW);
    }

    /**
     * 是否包含.
     *
     * @param permissionEffect allow or deny
     * @return <code>true</code> if {@link #DENY}
     */
    public static Boolean deny(PermissionEffect permissionEffect) {
        return permissionEffect.equals(DENY);
    }

    /**
     * is equals.
     *
     * @param permissionEffect allow or deny
     * @param allow            allow
     * @return <code>true</code>和{@link PermissionEffect#ALLOW}对应,<code>false</code>和{@link PermissionEffect#DENY}对应
     */
    public static boolean equals(PermissionEffect permissionEffect, boolean allow) {
        PermissionEffect _permissionEffect = parse(allow);
        return permissionEffect.equals(_permissionEffect);
    }
}
