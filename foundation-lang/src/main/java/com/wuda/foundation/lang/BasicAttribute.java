package com.wuda.foundation.lang;

import lombok.Getter;

/**
 * 数据的基本属性.有些记录可能拥有全部的这些属性,有些可能只有部分这些属性,对于没有的属性,
 * 子类必须重写该属性相关的方法(比如getter/setter),然后抛出{@link UnsupportedOperationException}.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public abstract class BasicAttribute {
    /**
     * 记录的创建时间,the milliseconds.只能是第一次创建时设置,后续不能被修改.
     */
    protected Long createTime;
    /**
     * 谁创建了该记录,只能是第一次创建时设置,后续不能被修改.
     */
    protected Long createUserId;
    /**
     * 最后一次修改时间,the milliseconds.
     */
    protected Long lastModifyTime;
    /**
     * 谁最后一次修改.
     */
    protected Long lastModifyUserId;

    /**
     * 创建时间必须为空,比如在更新数据时,不能更新创建时间.
     */
    protected void createTimeMustNullCheck() {
        if (createTime != null) {
            throw new IllegalStateException("不能设置createTime");
        }
    }

    /**
     * 创建时间不能为空,比如MySQL数据库,可以设置时间字段的定义是<pre>not null default current_timestamp</pre>,
     * 但是有的数据库可能没有这种特性,则新增数据时,必须检查创建时间字段.
     */
    protected void createTimeNonNullCheck() {
        if (createTime == null) {
            throw new IllegalStateException("必须设置createTime");
        }
        if (createTime <= Constant.EPOCH) {
            throw new IllegalArgumentException("createTime值不合法");
        }
    }

    /**
     * 创建时间不能为空,比如MySQL数据库,可以设置时间字段的定义是<pre>not null default current_timestamp</pre>,
     * 但是有的数据库可能没有这种特性,则新增数据时,必须检查创建时间字段.
     */
    protected void lastModifyTimeNonNullCheck() {
        if (lastModifyTime == null) {
            throw new IllegalStateException("必须设置lastModifyTime");
        }
        if (lastModifyTime <= Constant.EPOCH) {
            throw new IllegalArgumentException("lastModifyTime值不合法");
        }
    }

    /**
     * 创建者用户ID必须为空.比如在更新数据时,不能更新创建人.
     */
    protected void createUserIdMustNullCheck() {
        if (createUserId != null) {
            throw new IllegalStateException("不能设置createUserId");
        }
    }

    /**
     * 创建者用户ID不能为空.
     */
    protected void createUserIdNonNullCheck() {
        if (createUserId == null) {
            throw new IllegalStateException("必须设置createUserId");
        }
        if (createUserId <= 0) {
            throw new IllegalArgumentException("createUserId值不合法");
        }
    }

    /**
     * 最后更新人用户ID不能为空.
     */
    protected void lastModifyUserIdNonNullCheck() {
        if (lastModifyUserId == null) {
            throw new IllegalStateException("必须设置lastModifyUserId");
        }
        if (lastModifyUserId <= 0) {
            throw new IllegalArgumentException("lastModifyUserId值不合法");
        }
    }

    /**
     * builder class.
     */
    public abstract static class BasicAttributeBuilder<T extends BasicAttribute, B extends BasicAttributeBuilder<T, B>> implements Builder<T> {

        /**
         * 设置创建时间.
         *
         * @param createTime 创建时间
         * @return this
         */
        public abstract B createTime(Long createTime);

        /**
         * 设置创建者用户ID.
         *
         * @param createUserId 创建者用户ID
         * @return this
         */
        public abstract B createUserId(Long createUserId);

        /**
         * 设置创建时间.
         *
         * @param lastModifyTime 最后更新时间
         * @return this
         */
        public abstract B lastModifyTime(Long lastModifyTime);

        /**
         * 设置创建者用户ID.
         *
         * @param lastModifyUserId 最后更新者用户ID
         * @return this
         */
        public abstract B lastModifyUserId(Long lastModifyUserId);
    }
}
