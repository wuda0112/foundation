package com.wuda.foundation.lang.keygen;

/**
 * 用于生成主键.
 *
 * @param <T> 生成的主键的类型,比如long类型
 * @author wuda
 * @since 1.0.0
 */
public interface KeyGenerator<T> {

    /**
     * 返回下一个key.
     *
     * @return 下一个key
     * @throws KeyGenExceedMaxValueException 此生成器不能无限生成key,会有一个最大值,当超过这个最大值时,抛出此异常
     * @throws KeyGenTimeBackwardsException  机器的时间被向后调整了
     */
    T next() throws KeyGenExceedMaxValueException, KeyGenTimeBackwardsException;

    /**
     * 返回指定数量的key.
     *
     * @param size 一次性获取多个key
     * @return keys
     * @throws KeyGenExceedMaxValueException 此生成器不能无限生成key,会有一个最大值,当超过这个最大值时,抛出此异常
     * @throws KeyGenTimeBackwardsException  机器的时间被向后调整了
     */
    T[] next(int size) throws KeyGenExceedMaxValueException, KeyGenTimeBackwardsException;
}
