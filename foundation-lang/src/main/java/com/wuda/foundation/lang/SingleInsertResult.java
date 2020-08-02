package com.wuda.foundation.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行单个Insert的结果.必须配合{@link InsertMode}一起使用时才有意义.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class SingleInsertResult {

    /**
     * insert mode.
     */
    private InsertMode insertMode;
    /**
     * 是否执行了insert语句.比如{@link InsertMode}等于{@link InsertMode#INSERT_AFTER_SELECT_CHECK}时,
     * 当查询出有记录时,就不会执行insert语句.
     */
    private boolean executedInsert;
    /**
     * 受影响的行数,比如SQL执行后都会返回受影响的行数.
     * 即使执行了insert,也不一定成功新增记录,比如{@link InsertMode}等于{@link InsertMode#INSERT_WHERE_NOT_EXISTS}时,
     * 肯定执行了insert,但是不一定会新增记录.
     */
    private int affectedRows;
    /**
     * 当{@link InsertMode}是{@link InsertMode#INSERT_AFTER_SELECT_CHECK}或者{@link InsertMode#INSERT_WHERE_NOT_EXISTS}时,
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
