package com.wuda.foundation.lang.utils;

/**
 * 分页参数工具类.
 *
 * @author wuda
 * @since 1.0.0
 */
public class PaginationUtils {

    /**
     * 最小页码.
     */
    static int minPageNumber = 1;

    /**
     * 参数合法性检查
     *
     * @param pageNumber 页码
     */
    public static void checkPageNumber(int pageNumber) {
        if (pageNumber < minPageNumber) {
            throw new IllegalArgumentException("pageNumber 最小值是" + minPageNumber + ",给定参数值不合法");
        }
    }

    /**
     * 参数合法性检查
     *
     * @param pageSize 页码
     */
    public static void checkPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize不能小于1");
        }
    }

    /**
     * 有时候,可能页码参数有问题,我们不想抛出异常,就用一个常规数据替代.
     *
     * @param pageNumber page number
     */
    public static int normPageNumber(int pageNumber) {
        try {
            checkPageNumber(pageNumber);
            return pageNumber;
        } catch (Exception e) {
            return minPageNumber;
        }
    }

    /**
     * page number转换成MySQL分页参数的offset.
     *
     * @param pageNumber 页码
     * @return mysql 分页参数的offset
     */
    public static int toMySQLOffset(int pageNumber) {
        checkPageNumber(pageNumber);
        return pageNumber - 1;
    }
}
