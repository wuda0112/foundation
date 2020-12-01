package com.wuda.foundation.core.user;

import lombok.Data;

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
