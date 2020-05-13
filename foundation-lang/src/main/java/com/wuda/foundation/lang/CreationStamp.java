package com.wuda.foundation.lang;

import java.util.Objects;

/**
 * 为数据设置创建时的印记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class CreationStamp extends BasicAttribute {

    /**
     * 构建实例.
     *
     * @param createTime   记录的创建时间,non null
     * @param createUserId 谁创建了这个记录,non null
     */
    public CreationStamp(Long createTime, Long createUserId) {
        Objects.requireNonNull(createTime);
        Objects.requireNonNull(createUserId);
        this.createTime = createTime;
        this.createUserId = createUserId;
    }

    /**
     * Unsupported Operation,如果确实需要,有个隐含规则,
     * 那就是数据在创建时,它的最后更新时间其实也就是创建时间.
     *
     * @return null
     */
    public Long getLastModifyTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported Operation,如果确实需要,有个隐含规则,
     * 那就是数据在创建时,它的最后更新者其实也就是创建者.
     *
     * @return null
     */
    public Long getLastModifyUserId() {
        throw new UnsupportedOperationException();
    }
}
