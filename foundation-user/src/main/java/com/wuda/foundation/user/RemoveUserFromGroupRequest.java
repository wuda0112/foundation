package com.wuda.foundation.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 把用户从组中移除.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class RemoveUserFromGroupRequest {

    private Long userId;
    private LongIdentifier group;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private RemoveUserFromGroupRequest() {

    }

    /**
     * 用于创建{@link RemoveUserFromGroupRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<RemoveUserFromGroupRequest> {

        private Long userId;
        private LongIdentifier group;

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setGroup(LongIdentifier group) {
            this.group = group;
            return this;
        }

        @Override
        public RemoveUserFromGroupRequest build() {
            RemoveUserFromGroupRequest request = new RemoveUserFromGroupRequest();
            request.userId = Objects.requireNonNull(this.userId);
            request.group = Objects.requireNonNull(this.group);
            return request;
        }
    }
}
