package com.wuda.foundation.core.security;

/**
 * allow or deny.
 *
 * @author wuda
 * @since 1.0.3
 */
public enum AllowOrDeny {

    ALLOW("allow"),
    DENY("deny");

    public String getValueString() {
        return value;
    }

    private String value;

    AllowOrDeny(String value) {
        this.value = value;
    }

    /**
     * 根据boolean解析.
     *
     * @param b to be parsed
     * @return <code>null</code>-如果没有匹配的
     */
    public static AllowOrDeny parse(Boolean b) {
        if (b.equals(Boolean.TRUE)) {
            return AllowOrDeny.ALLOW;
        }
        return AllowOrDeny.DENY;
    }

    /**
     * 是否allow.
     *
     * @param allowOrDeny allow or deny
     * @return <code>true</code> if {@link #ALLOW}
     */
    public static Boolean allow(AllowOrDeny allowOrDeny) {
        return allowOrDeny.equals(ALLOW);
    }

    /**
     * 是否包含.
     *
     * @param allowOrDeny allow or deny
     * @return <code>true</code> if {@link #DENY}
     */
    public static Boolean deny(AllowOrDeny allowOrDeny) {
        return allowOrDeny.equals(DENY);
    }

    /**
     * is equals.
     *
     * @param allowOrDeny allow or deny
     * @param allow       allow
     * @return <code>true</code>和{@link AllowOrDeny#ALLOW}对应,<code>false</code>和{@link AllowOrDeny#DENY}对应
     */
    public static boolean equals(AllowOrDeny allowOrDeny, boolean allow) {
        AllowOrDeny _allowOrDeny = parse(allow);
        return allowOrDeny.equals(_allowOrDeny);
    }
}
