package com.wuda.foundation.core.security.impl.menu;

import com.wuda.foundation.core.security.Target;
import com.wuda.foundation.core.security.menu.MenuComparator;
import com.wuda.foundation.lang.identify.BuiltinIdentifierType;
import com.wuda.foundation.lang.identify.IdentifierType;

public class MenuComparatorImpl implements MenuComparator {
    @Override
    public boolean support(IdentifierType type) {
        return type.equals(BuiltinIdentifierType.MENU);
    }

    @Override
    public boolean comparable(Target first, Target second) {
        // 同一个菜单才能比较
        return first.getValue().equals(second.getValue());
    }

    @Override
    public boolean implies(Target first, Target second) {
        // 同一个菜单不可能包含
        return false;
    }

    @Override
    public boolean equals(Target first, Target second) {
        // 同一个菜单肯定相等
        return true;
    }

    @Override
    public boolean impliesOrEquals(Target first, Target second) {
        return true;
    }
}
