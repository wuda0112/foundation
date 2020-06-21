package com.wuda.foundation.user;

import com.wuda.foundation.lang.Updatable;
import lombok.Getter;

/**
 * 用于更新个人用户基本信息.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class UpdateIndividualUserGeneral implements Updatable {
    /**
     * nickname
     */
    private String nickname;
    /**
     * 个人简介.
     */
    private String biography;
}
