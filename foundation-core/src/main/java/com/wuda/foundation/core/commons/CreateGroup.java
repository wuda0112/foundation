package com.wuda.foundation.core.commons;

import lombok.Getter;

import java.util.Objects;

/**
 * for create group core.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateGroup {

    private Long groupId;
    private Long parentGroupId;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateGroup() {

    }

    /**
     * 用于创建{@link CreateGroup}.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateGroup> {

        private Long groupId;
        private Long parentGroupId;


        public Builder setGroupId(Long groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder setParentGroupId(Long parentGroupId) {
            this.parentGroupId = parentGroupId;
            return this;
        }

        @Override
        public CreateGroup build() {
            CreateGroup createGroup = new CreateGroup();
            createGroup.groupId = Objects.requireNonNull(this.groupId);
            createGroup.parentGroupId = Objects.requireNonNull(this.parentGroupId);
            return createGroup;
        }
    }
}
