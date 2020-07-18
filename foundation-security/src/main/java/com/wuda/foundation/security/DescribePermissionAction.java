package com.wuda.foundation.security;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Data;
import lombok.ToString;

/**
 * 表示permission允许执行的动作,比如文件的read/write.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
@ToString
public class DescribePermissionAction {

    /**
     * id.
     */
    private Long id;

    /**
     * 所属的permission target的id.
     */
    private Long permissionTargetId;

    /**
     * action.
     */
    private PermissionActionName name;
    /**
     * 描述.
     */
    private String description;

    /**
     * 关联的外部对象的identifier.
     */
    private LongIdentifier referencedIdentifier;

}
