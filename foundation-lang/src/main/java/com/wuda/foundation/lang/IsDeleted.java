package com.wuda.foundation.lang;

/**
 * 是否删除.
 *
 * @author wuda
 */
public enum IsDeleted {

    /**
     * 没有删除.
     */
    NO(0);

    private long value;

    IsDeleted(long value) {
        this.value = value;
    }

    /**
     * 获取value.
     *
     * @return value
     */
    public long getValue() {
        return value;
    }

}
