package com.wuda.foundation.user;

import com.wuda.foundation.lang.CRUDEntity;
import com.wuda.foundation.lang.CRUDEntityState;
import com.wuda.foundation.lang.IdValidator;
import lombok.Getter;

import java.util.Objects;

/**
 * 代表单个用户的个人信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class IndividualUserGeneral extends CRUDEntity {
    /**
     * general id
     */
    private Long individualUserGeneralId;
    /**
     * 所属用户ID.
     */
    private Long userId;
    /**
     * 所属用户的类型.
     */
    private UserType userType;
    /**
     * nickname
     */
    private String nickname;
    /**
     * 个人简介.
     */
    private String biography;

    @Override
    protected void forCreateCheck() {
        forCreateCheckShortcut0();
        Objects.requireNonNull(userType);
        UserIdValidator.userRelatedIdValidate(userType, individualUserGeneralId);
        UserIdValidator.userRelatedIdValidate(userType, userId);
    }

    @Override
    protected void forUpdateCheck() {
        forUpdateCheckShortcut0();
        IdValidator.notEqualsZero(individualUserGeneralId);
    }

    /**
     * builder class.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder extends CRUDEntityBuilder<IndividualUserGeneral, Builder> {

        private IndividualUserGeneral general = new IndividualUserGeneral();

        public Builder(CRUDEntityState crudState) {
            super(crudState);
        }

        public Builder individualUserProfileId(Long individualUserProfileId) {
            general.individualUserGeneralId = individualUserProfileId;
            return this;
        }

        public Builder userId(Long userId) {
            general.userId = userId;
            return this;
        }

        public Builder nickname(String nickname) {
            general.nickname = nickname;
            return this;
        }

        public Builder biography(String biography) {
            general.biography = biography;
            return this;
        }

        public Builder userType(UserType userType) {
            general.userType = userType;
            return this;
        }

        @Override
        public IndividualUserGeneral build() {
            beforeBuild(general);
            return general;
        }

        @Override
        public Builder createTime(Long createTime) {
            general.createTime = createTime;
            return this;
        }

        @Override
        public Builder createUserId(Long createUserId) {
            general.createUserId = createUserId;
            return this;
        }

        @Override
        public Builder lastModifyTime(Long lastModifyTime) {
            general.lastModifyTime = lastModifyTime;
            return this;
        }

        @Override
        public Builder lastModifyUserId(Long lastModifyUserId) {
            general.lastModifyUserId = lastModifyUserId;
            return this;
        }
    }
}
