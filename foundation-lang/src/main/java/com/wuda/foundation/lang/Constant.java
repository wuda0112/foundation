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
}
