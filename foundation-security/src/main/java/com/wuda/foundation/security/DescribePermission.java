package com.wuda.foundation.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * 参考{@link java.io.FilePermission}.具体可以查看{@link java.io.FilePermission}的定义.
 *
 * @author wuda
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
@ToString
public class DescribePermission {

    private DescribePermissionTarget target;
    private List<DescribePermissionAction> actions;

}
