package com.wuda.foundation.lang;

/**
 * An object that provides a read-only or updatable <em>view</em> of non-opaque
 * values associated with an entity. 一个entity可以是用户,商品,店铺等等,这个entity的核心信息,会在核心模型中处理,
 * 但是,除了核心信息外,还包含了其他很多属性,比如对于用户,除了用户名/密码,这个用户还会有昵称,生日等等,这样的信息会随着业务的发展而变的很多,
 * 并且通常他们不会和核心的信息保存在一起(如果用关系型数据库,通常会分成多个表),这个接口用于管理一个实体的某一方面(可能是一个表,也可能是组成某一方面的多个表)的属性.
 * 一个问题就是哪些是核心信息,哪些又是"其他"属性呢?这个不好回答,但是参考{@link java.io.File}和{@link java.nio.file.attribute.FileAttributeView},
 * 应该会正确处理的.实现类必须提供类型安全的方法.在web系统中,通常,对于每个表都有一个相关的service,这个接口和他们的区别是: 为entity的某一个方面提供操作,
 * 而不是为每一个表,当然,有可能某个方面用一个单独的表就可以表示.
 *
 * @author wuda
 * @see java.nio.file.attribute.FileAttributeView
 * @since 1.0.0
 */
public interface EntityAspectAttributeView {
    /**
     * Returns the name of the attribute view.
     *
     * @return the name of the attribute view
     */
    String name();
}
