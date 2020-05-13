package com.wuda.foundation.security;

/**
 * permission target.
 */
public class PermissionTarget {

    /**
     * id.
     */
    private Integer id;

    /**
     * 所属分类的ID
     */
    private Integer categoryId;

    /**
     * 名称.
     */
    private String name;

    /**
     * 作用对象的类型,比如文件.
     */
    private PermissionTargetType type;

    /**
     * 关联的外部对象的identifier.
     */
    private PermissionTargetReferencedType referencedType;
    /**
     * 关联的外部对象的identifier.
     */
    private Integer referencedIdentifier;
    /**
     * 描述.
     */
    private String description;

    /**
     * 校验参数的正确性.
     */
    // todo 有问题
    public void check() {
        if ((referencedIdentifier == null && type != null)
                || (type == null && referencedIdentifier != null)) {
            throw new IllegalStateException("referencedType和referencedIdentifier必须同时为空或者同时都不为空");
        }
    }
}
