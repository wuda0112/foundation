package com.wuda.foundation.lang.utils;

/**
 * 排序.
 */
public class OrderBy {

    /**
     * 列.
     */
    private OrderByColumn column;

    /**
     * 顺序.
     */
    private OrderByOrder order;

    /**
     * order by 所使用的列的名称.
     */
    public interface OrderByColumn {
        /**
         * 获取column name.
         *
         * @return column name
         */
        String name();
    }

    /**
     * order by 的顺序.
     */
    public enum OrderByOrder {
        ASCENDING, DESCENDING;
    }
}
