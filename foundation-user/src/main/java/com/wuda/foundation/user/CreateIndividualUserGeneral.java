package com.wuda.foundation.user;

import lombok.Getter;

import java.util.Objects;

/**
 * for create individual user general.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateIndividualUserGeneral {
    /**
     * general id
     */
    private Long id;
    private Long userId;
    /**
     * nickname
     */
    private String nickname;
    /**
     * 个人简介.
     */
    private String biography;
    /**
     * 头像地址.
     */
    private String picture;

    public static class Builder implements com.wuda.foundation.lang.Builder<CreateIndividualUserGeneral> {

        private Long id;
        private Long userId;
        private String nickname;
        private String biography;
        private String picture;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
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
        public CreateIndividualUserGeneral build() {
            CreateIndividualUserGeneral createIndividualUserGeneral = new CreateIndividualUserGeneral();
            createIndividualUserGeneral.id = Objects.requireNonNull(id);
            createIndividualUserGeneral.userId = Objects.requireNonNull(userId);
            createIndividualUserGeneral.nickname = nickname;
            createIndividualUserGeneral.biography = biography;
            createIndividualUserGeneral.picture = picture;
            return createIndividualUserGeneral;
        }
    }
}
