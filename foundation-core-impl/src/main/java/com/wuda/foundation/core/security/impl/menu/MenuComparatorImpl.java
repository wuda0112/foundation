package com.wuda.foundation.core.security.impl.menu;

import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.core.security.menu.MenuComparator;
import com.wuda.foundation.lang.identify.IdentifierType;

public class MenuComparatorImpl implements MenuComparator {
    @Override
    public boolean support(IdentifierType type) {
        return false;
    }

    @Override
    public boolean comparable(Target first, Target second) {
        return first.getValue().equals(second.getValue());
    }

    @Override
    public boolean implies(Target first, Target second) {
        return false;
    }

    @Override
    public boolean equals(Target first, Target second) {
        return false;
    }

    @Override
    public boolean impliesOrEquals(Target first, Target second) {
        return false;
    }
}
