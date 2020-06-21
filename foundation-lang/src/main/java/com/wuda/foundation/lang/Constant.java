package com.wuda.foundation.lang;

/**
 * 常量.
 *
 * @author wuda
 * @since 1.0.0
 */
public class Constant {

    /**
     * 禁止实例化.
     */
    private Constant() {

    }

    /**
     * 写这个类的时间,可以用于检验时间,比如{@link BasicAttribute#getCreateTime()}
     * 就不可能不这个值小.
     */
    public final static long EPOCH = 1584115200;

    /**
     * id的最小值
     */
    public final static long ID_MIN = 100;

    /**
     * 一个特殊值,和<code>null</code>,代表的意义一样.当数据库字段的值有可能不存在,
     * 但是数据库设计又不允许为空(not null)时,可以使用这个值填充
     */
    public final static long NOT_EXISTS_ID = 0;
}
