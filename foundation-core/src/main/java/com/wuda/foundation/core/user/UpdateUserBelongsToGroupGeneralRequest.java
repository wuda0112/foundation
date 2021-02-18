package com.wuda.foundation.core.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Getter;

import java.util.Objects;

/**
 * 更新用户在组中的基本信息.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class UpdateUserBelongsToGroupGeneralRequest {

    private Long userId;
    private LongIdentifier group;
    private String nickname;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private UpdateUserBelongsToGroupGeneralRequest() {

    }

    /**
     * 用于创建{@link UpdateUserBelongsToGroupGeneralRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateUserBelongsToGroupGeneralRequest> {

        private Long userId;
        private LongIdentifier group;
        private String nickname;

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setGroup(LongIdentifier group) {
            this.group = group;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        @Override
        public UpdateUserBelongsToGroupGeneralRequest build() {
            UpdateUserBelongsToGroupGeneralRequest request = new UpdateUserBelongsToGroupGeneralRequest();
            request.userId = Objects.requireNonNull(this.userId);
            request.group = Objects.requireNonNull(this.group);
            request.nickname = Objects.requireNonNull(this.nickname);
            return request;
        }
    }
}
