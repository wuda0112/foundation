package com.wuda.foundation.core.security;

import com.wuda.foundation.lang.identify.LongIdentifier;
import lombok.Data;
import lombok.ToString;

/**
 * 表示permission作用的目标对象,比如文件.
 *
 * @author wuda
 * @since 1.0.0
 */
@Data
@ToString
public class DescribePermissionTarget {

    /**
     * id.
     */
    private Long id;

    /**
     * 所属分类的ID
     */
    private Long categoryId;

    /**
     * 名称.
     */
    private String name;

    /**
     * 作用对象的类型,比如文件.
     */
    private Byte type;
    /**
     * 关联的外部对象的identifier.
     */
    private LongIdentifier referencedIdentifier;
    /**
     * 描述.
     */
    private String description;
}
