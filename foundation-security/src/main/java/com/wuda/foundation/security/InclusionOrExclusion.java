package com.wuda.foundation.security;

/**
 * inclusion or exclusion.
 *
 * @author wuda
 * @since 1.0.3
 */
public enum InclusionOrExclusion {

    INCLUSION("inclusion"),
    EXCLUSION("exclusion");

    public String getValueString() {
        return value;
    }

    private String value;

    InclusionOrExclusion(String value) {
        this.value = value;
    }

    /**
     * 根据boolean解析.
     *
     * @param b to be parsed
     * @return <code>null</code>-如果没有匹配的
     */
    public static InclusionOrExclusion parse(Boolean b) {
        if (b.equals(Boolean.TRUE)) {
            return InclusionOrExclusion.INCLUSION;
        }
        return InclusionOrExclusion.EXCLUSION;
    }

    /**
     * 是否包含.
     *
     * @param inclusionOrExclusion inclusion or exclusion
     * @return <code>true</code> if {@link #INCLUSION}
     */
    public static Boolean inclusion(InclusionOrExclusion inclusionOrExclusion) {
        return inclusionOrExclusion.equals(INCLUSION);
    }

    /**
     * is equals.
     *
     * @param inclusionOrExclusion inclusion or exclusion
     * @param inclusion            是否包含
     * @return <code>true</code>和{@link InclusionOrExclusion#INCLUSION}对应,<code>false</code>和{@link InclusionOrExclusion#EXCLUSION}对应
     */
    public static boolean equals(InclusionOrExclusion inclusionOrExclusion, boolean inclusion) {
        InclusionOrExclusion _inclusionOrExclusion = parse(inclusion);
        return inclusionOrExclusion.equals(_inclusionOrExclusion);
    }
}
