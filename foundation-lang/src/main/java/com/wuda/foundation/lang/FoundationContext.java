package com.wuda.foundation.lang;

import com.wuda.foundation.lang.keygen.KeyGenerator;
import com.wuda.foundation.lang.keygen.KeyGeneratorSnowflake;

/**
 * 在项目级别内的Context.
 *
 * @author wuda
 * @since 1.0.0
 */
public class FoundationContext {

    private static KeyGenerator<Long> longKeyGenerator = new KeyGeneratorSnowflake(1);

    /**
     * 设置key generator.
     *
     * @param longKeyGenerator key generator,用于生成long类型的key
     */
    public static void setLongKeyGenerator(KeyGenerator<Long> longKeyGenerator) {
        FoundationContext.longKeyGenerator = longKeyGenerator;
    }

    /**
     * 获取long类型的key generator.
     *
     * @return key generator,用于生成long类型的key
     */
    public static KeyGenerator<Long> getLongKeyGenerator() {
        return longKeyGenerator;
    }
}
