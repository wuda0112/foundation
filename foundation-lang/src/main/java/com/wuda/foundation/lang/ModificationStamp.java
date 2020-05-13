package com.wuda.foundation.lang;

import java.util.Objects;

/**
 * 为数据设置修改时的印记.
 *
 * @author wuda
 * @since 1.0.0
 */
public class ModificationStamp extends BasicAttribute {

    /**
     * 构建实例.
     *
     * @param lastModifyTime   记录的最后更新时间,non null
     * @param lastModifyUserId 谁最后更新了这个记录,non null
     */
    public ModificationStamp(Long lastModifyTime, Long lastModifyUserId) {
        Objects.requireNonNull(lastModifyTime);
        Objects.requireNonNull(lastModifyUserId);
        this.lastModifyTime = lastModifyTime;
        this.lastModifyUserId = lastModifyUserId;
    }

    /**
     * Unsupported Operation
     *
     * @return null
     */
    public Long getCreateTime() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported Operation
     *
     * @return null
     */
    public Long getCreateUserId() {
        throw new UnsupportedOperationException();
    }

}
