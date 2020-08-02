package com.wuda.foundation.user;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于更新个人用户基本信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateIndividualUserGeneral {
    /**
     * general id
     */
    private Long id;
    /**
     * nickname
     */
    private String nickname;
    /**
     * 个人简介.
     */
    private String biography;
    private String picture;

    public static class Builder implements com.wuda.foundation.lang.Builder<UpdateIndividualUserGeneral> {

        private Long id;
        private String nickname;
        private String biography;
        private String picture;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setBiography(String biography) {
            this.biography = biography;
            return this;
        }

        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }

        @Override
        public UpdateIndividualUserGeneral build() {
            UpdateIndividualUserGeneral updateIndividualUserGeneral = new UpdateIndividualUserGeneral();
            updateIndividualUserGeneral.id = Objects.requireNonNull(id);
            updateIndividualUserGeneral.nickname = nickname;
            updateIndividualUserGeneral.biography = biography;
            updateIndividualUserGeneral.picture = picture;
            return updateIndividualUserGeneral;
        }
    }
}
