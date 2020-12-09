package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.identify.IdentifierType;

import java.security.Permission;

/**
 * 范围对比.在权限分配体系中,{@link Subject},{@link Target},{@link Action}
 * 所代表的的实体,既可以是某个具体实体,也可以是一组实体.比如在文件系统权限中,
 * <pre>
 *     用户对于文件/文件夹可以执行的操作
 * </pre>
 * 这个模型中,{@link Target}就可以是具体的文件,也可以是表示一类文件的文件夹,这时,
 * 表示文件夹的{@link Target}范围就比表示文件的{@link Target}大,并且如果这个文件
 * 刚好就在文件夹内时,我们可以说表示文件夹的{@link Target} {@link #implies(Object, Object) implies}
 * 表示文件的{@link Target}.
 *
 * @param <T> 用于比较的实体的类型
 * @author wuda
 * @since 1.0.3
 */
public interface ScopeComparator<T> {

    /**
     * 是否支持该类型实体的对比.
     *
     * @param type type
     * @return <code>true</code>-如果支持
     */
    boolean support(IdentifierType type);

    /**
     * 检查第一个是否"包含"第二个.
     *
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return <code>true</code>-如果第一个包含第二个
     * @see java.io.FilePermission#implies(Permission)
     */
    boolean implies(T first, T second);

    /**
     * 检查第一个是否等于第二个.
     *
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return <code>true</code>-如果第一个等于第二个
     */
    boolean equals(T first, T second);

    /**
     * 检查第一个是否"包含"或者等于第二个.
     *
     * @param first  the first object to be compared.
     * @param second the second object to be compared.
     * @return <code>true</code>-如果第一个包含或者等于第二个
     */
    boolean impliesOrEquals(T first, T second);
}
