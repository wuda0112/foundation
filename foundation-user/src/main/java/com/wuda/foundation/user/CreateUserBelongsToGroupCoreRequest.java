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
public class CreateUserBelongsToGroupCoreRequest {

    private Long id;
    private Long userBelongsToGroupId;
    private Long userId;
    private LongIdentifier group;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserBelongsToGroupCoreRequest() {

    }

    /**
     * 用于创建{@link CreateUserBelongsToGroupCoreRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserBelongsToGroupCoreRequest> {

        private Long id;
        private Long userBelongsToGroupId;
        private Long userId;
        private LongIdentifier group;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserBelongsToGroupId(Long userBelongsToGroupId) {
            this.userBelongsToGroupId = userBelongsToGroupId;
            return this;
        }

        public Builder setGroup(LongIdentifier group) {
            this.group = group;
            return this;
        }

        @Override
        public CreateUserBelongsToGroupCoreRequest build() {
            CreateUserBelongsToGroupCoreRequest request = new CreateUserBelongsToGroupCoreRequest();
            request.id = Objects.requireNonNull(this.id);
            request.userBelongsToGroupId = Objects.requireNonNull(this.userBelongsToGroupId);
            request.userId = Objects.requireNonNull(this.userId);
            request.group = Objects.requireNonNull(this.group);
            return request;
        }
    }
}
