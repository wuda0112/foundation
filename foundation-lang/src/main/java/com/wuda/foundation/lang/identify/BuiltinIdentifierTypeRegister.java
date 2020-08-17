package com.wuda.foundation.lang.identify;

final class BuiltinIdentifierTypeRegister {

    static void register() {
        BuiltinIdentifierType[] types = BuiltinIdentifierType.values();
        for (BuiltinIdentifierType type : types) {
            IdentifierTypeRegistry.defaultRegistry.register(type);
        }
    }
}
