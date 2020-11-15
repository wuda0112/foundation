package com.wuda.foundation.user;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Data;

/**
 * 描述用户与组的关联.
 *
 * @author wuda
 * @since 1.0.3
 */
@Data
public class DescribeUserBelongsToGroupCore {

    private Long id;
    private Long userBelongsToGroupId;
    private Long userId;
    private LongIdentifier group;
}
