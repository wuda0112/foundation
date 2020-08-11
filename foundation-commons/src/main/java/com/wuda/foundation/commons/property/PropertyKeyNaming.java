package com.wuda.foundation.commons.property;

import com.wuda.foundation.lang.identify.IdentifierType;

import java.util.List;

/**
 * 如果使用字符串作为property key的方式获取property,会导致代码中出现大量的魔法值,比如
 * <pre>
 *     DescribeProperty property = Properties.get("my_property_key"); //my_property_key就是一个魔法值
 * </pre>
 * 如果使用一种强类型的方式(比如枚举),那么就可以避免字符串的property key分散在代码中,因此可以使用枚举的方式实现该类,将
 * property key集中管理起来,但是要求
 * <ul>
 * <li>必须指明该property属于哪种实体,比如是店铺的属性或者是商品的属性,等等;使用{@link #getIdentifierType()}返回的值表明,
 * 并且这个返回值必须和数据库中保存的property owner type保持一致,查看{@link CreatePropertyKey#getOwner()}</li>
 * <li>property key必须和数据库中保存的名称一致,即{@link #getKey()}的值和数据库中保存的属性名一致</li>
 * </ul>
 * .查看{@link PropertyUtils#getProperty(List, PropertyKeyNaming)}的使用.
 *
 * @author wuda
 * @since 1.0.0
 */
public interface PropertyKeyNaming {


    /**
     * 获取property key的唯一标记.
     *
     * @return keys
     */
    String getKey();

    /**
     * 表示该property key所属的主体的类型.
     *
     * @return 该property key所属的主体的类型
     */
    IdentifierType getIdentifierType();

}
