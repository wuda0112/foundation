package com.wuda.foundation.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创建记录的结果.可以配合{@link CreateMode}一起使用.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class CreateResult {

    /**
     * create mode.
     */
    private CreateMode createMode;
    /**
     * 当{@link CreateMode}是{@link CreateMode#CREATE_AFTER_SELECT_CHECK}或者{@link CreateMode#CREATE_WHERE_NOT_EXISTS}时,
     * 如果记录已经存在,则返回已经存在的记录的ID.
     */
    private Long existsRecordId;
    /**
     * 新增的记录的ID.
     */
    private Long newlyAddedRecordId;

    /**
     * 是否新增加了记录
     *
     * @return <code>true</code>-表示新增加了记录
     */
    public boolean newlyAddedRecord() {
        return newlyAddedRecordId != null;
    }

    /**
     * 获取新增或者已经存在的记录的ID
     *
     * @return 新增或者已经存在的记录的ID
     */
    public Long getRecordId() {
        valid();
        if (newlyAddedRecordId != null) {
            return newlyAddedRecordId;
        }
        return existsRecordId;
    }

    /**
     * 检测结果的有效性.如果结果是无效的,则会抛出异常.
     */
    public void valid() {
        if (existsRecordId != null && newlyAddedRecordId != null) {
            throw new IllegalStateException("已经存在记录,又新添加了记录?");
        } else if (existsRecordId == null && newlyAddedRecordId == null) {
            throw new IllegalStateException("既没有已经存在记录,又没有添加新记录?");
        }
    }

}
