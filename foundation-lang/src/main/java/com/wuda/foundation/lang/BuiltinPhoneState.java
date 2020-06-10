package com.wuda.foundation.lang;

import com.wuda.foundation.lang.PhoneState;

/**
 * 内置phone state.
 *
 * @author wuda
 */
public enum BuiltinPhoneState implements PhoneState {

    ZERO(0, "初始化状态,刚添加到系统中"),
    ONE(1, "验证通过");

    private int code;
    private String description;

    BuiltinPhoneState(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
