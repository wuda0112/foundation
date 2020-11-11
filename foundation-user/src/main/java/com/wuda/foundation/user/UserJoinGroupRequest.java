package com.wuda.foundation.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户加入组.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UserJoinGroupRequest {

    private Long userId;
    private LongIdentifier group;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private UserJoinGroupRequest() {

    }

    /**
     * 用于创建{@link UserJoinGroupRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UserJoinGroupRequest> {

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
        public UserJoinGroupRequest build() {
            UserJoinGroupRequest userJoinGroupRequest = new UserJoinGroupRequest();
            userJoinGroupRequest.userId = Objects.requireNonNull(this.userId);
            userJoinGroupRequest.group = Objects.requireNonNull(this.group);
            return userJoinGroupRequest;
        }
    }
}
