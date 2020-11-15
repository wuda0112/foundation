package com.wuda.foundation.user;

import lombok.Data;
import lombok.Getter;

/**
 * 描述用户在组中的基本信息.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribeUserBelongsToGroupGeneral {

    private Long id;
    private Long userBelongsToGroupId;
    private String nickname;
}
