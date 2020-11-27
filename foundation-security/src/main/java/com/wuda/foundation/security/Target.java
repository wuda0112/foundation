package com.wuda.foundation.security;

import com.wuda.foundation.lang.identify.IdentifierType;
import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 代表权限体系中分配的目标对象.
 *
 * @author wuda
 * @since 1.0.3
 */
public class Target extends LongIdentifier {

    /**
     * 构造函数.
     *
     * @param value value
     * @param type  type
     */
    public Target(Long value, Type type) {
        super(value, type);
    }

    /**
     * target type.
     *
     * @author wuda
     * @since 1.0.3
     */
    public class Type implements IdentifierType {

        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getDescription() {
            return null;
        }
    }

    /**
     * 当前target所代表的是否一个集合。就好比Java中的File类，既可以代表单个具体的文件，也可以代表文件夹，如果当前target is collection，
     * 则当前target就好比是文件夹一样。如果当前target is collection，则表示授权一组target给subject，就好像给一个用户授权一个文件夹，则该文件夹下的所有
     * 文件以及子文件夹都被授权给这个用户.
     *
     * @return <code>true</code>-如果是集合类型
     */
    public boolean isCollection() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Target) {
            Target other = (Target) obj;
            return this.getType().getCode() == ((Target) obj).getType().getCode() && this.getValue().equals(other.getValue());
        }
        return false;
    }
}
