package com.wuda.foundation.lang.utils;

import lombok.Getter;

/**
 * 分页参数.其实也可以直接使用两个{@link Integer}类型的参数表示分页,但是为了能统一校验和维护分页参数,
 * 比如页码从1开始,因此使用实体类来作为分页参数.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class Pagination {

    /**
     * 页码
     */
    private int pageNumber;
    /**
     * 每页的数据量.
     */
    private int pageSize = 20;

    Pagination(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 获取分页对象
     *
     * @param pageNumber 页码,从1开始.
     * @param pageSize   每页数量
     * @return 分页对象
     */
    public static Pagination getInstance(int pageNumber, int pageSize) {
        PaginationUtils.checkPageNumber(pageNumber);
        PaginationUtils.checkPageSize(pageSize);
        return new Pagination(pageNumber, pageSize);
    }

}
