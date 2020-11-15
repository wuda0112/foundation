package com.wuda.foundation.user;

import lombok.Getter;

import java.util.Objects;

/**
 * 创建用户在组中的基本信息.
 *
 * @author wuda
 * @since 1.0.3
 */
@Getter
public class CreateUserBelongsToGroupGeneralRequest {

    private Long id;
    private Long userBelongsToGroupId;
    private String nickname;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateUserBelongsToGroupGeneralRequest() {

    }

    /**
     * 用于创建{@link CreateUserBelongsToGroupGeneralRequest}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateUserBelongsToGroupGeneralRequest> {

        private Long id;
        private Long userBelongsToGroupId;
        private String nickname;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserBelongsToGroupId(Long userBelongsToGroupId) {
            this.userBelongsToGroupId = userBelongsToGroupId;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        @Override
        public CreateUserBelongsToGroupGeneralRequest build() {
            CreateUserBelongsToGroupGeneralRequest request = new CreateUserBelongsToGroupGeneralRequest();
            request.id = Objects.requireNonNull(this.id);
            request.userBelongsToGroupId = Objects.requireNonNull(this.userBelongsToGroupId);
            request.nickname = Objects.requireNonNull(this.nickname);
            return request;
        }
    }
}
