package com.wuda.foundation.lang;

/**
 * 执行数据插入操作时的可选模式.
 *
 * @author wuda
 * @since 1.0.0
 */
public enum CreateMode {
    /**
     * 直接向数据库执行插入语句，不做任何重复性检查.如果明确的知道没有重复记录,则直接插入数据库性能会更好,
     * 但也可能会插入重复记录.
     */
    DIRECT,
    /**
     * 分两阶段，先查询，看数据是否存在，如果不存在，再执行插入操作.
     * 这种情况下,是有可能插入重复数据的,比如第一阶段没有查询到数据,
     * 在还没有执行插入操作之前。其他线程/进程却在这个时候插入了一条
     * 相同的记录.
     */
    CREATE_AFTER_SELECT_CHECK,
    /**
     * 如果不存在则插入，但是检查是否存在和插入是"原子"操作,
     * 比如MySQL的INSERT INTO SELECT WHERE NOT EXISTS语法.
     * 这种模式下性能较低,且有可能锁住数据库.
     */
    CREATE_WHERE_NOT_EXISTS;
}
