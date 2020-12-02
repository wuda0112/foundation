package com.wuda.foundation.core.security;

/**
 * inclusion or exclusion.
 *
 * @author wuda
 * @since 1.0.3
 */
public enum AllowOrDeny {

    INCLUSION("inclusion"),
    EXCLUSION("exclusion");

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
            return AllowOrDeny.INCLUSION;
        }
        return AllowOrDeny.EXCLUSION;
    }

    /**
     * 是否包含.
     *
     * @param allowOrDeny inclusion or exclusion
     * @return <code>true</code> if {@link #INCLUSION}
     */
    public static Boolean inclusion(AllowOrDeny allowOrDeny) {
        return allowOrDeny.equals(INCLUSION);
    }

    /**
     * is equals.
     *
     * @param allowOrDeny inclusion or exclusion
     * @param inclusion            是否包含
     * @return <code>true</code>和{@link AllowOrDeny#INCLUSION}对应,<code>false</code>和{@link AllowOrDeny#EXCLUSION}对应
     */
    public static boolean equals(AllowOrDeny allowOrDeny, boolean inclusion) {
        AllowOrDeny _allowOrDeny = parse(inclusion);
        return allowOrDeny.equals(_allowOrDeny);
    }
}
