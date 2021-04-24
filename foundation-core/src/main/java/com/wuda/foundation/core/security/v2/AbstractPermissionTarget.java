package com.wuda.foundation.core.security.v2;

import com.wuda.foundation.lang.identify.LongIdentifier;

/**
 * 抽象实现类.
 *
 * @author wuda
 */
public abstract class AbstractPermissionTarget implements PermissionTarget {

    protected LongIdentifier identifier;
    protected String name;
    protected String description;

    protected AbstractPermissionTarget(LongIdentifier identifier, String name, String description) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
    }

    @Override
    public LongIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
