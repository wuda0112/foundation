package com.wuda.foundation.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link UniqueCodeDescriptor}的注册中心.
 *
 * @author wuda
 * @since 1.0.0
 */
public class UniqueCodeDescriptorRegistry {

    private Map<Class<UniqueCodeDescriptorSchema>, List<UniqueCodeDescriptor>> bySchemaMap = new ConcurrentHashMap<>();

    public static UniqueCodeDescriptorRegistry defaultRegistry = new UniqueCodeDescriptorRegistry();

    /**
     * 注册{@link UniqueCodeDescriptor}.
     *
     * @param descriptor {@link UniqueCodeDescriptor}
     */
    public void register(UniqueCodeDescriptor descriptor) {
        List<UniqueCodeDescriptor> uniqueCodeDescriptors = bySchemaMap.get(descriptor.getSchemaClass());
        if (uniqueCodeDescriptors != null) {
            for (UniqueCodeDescriptor uniqueCodeDescriptor : uniqueCodeDescriptors) {
                if (descriptor.getCode().equals(uniqueCodeDescriptor.getCode())) {
                    throw new IllegalStateException("UniqueCodeDescriptor schema = " + descriptor.getSchemaClass() + ",UniqueCodeDescriptor code = " + descriptor.getCode() + ",Duplicate!");
                }
            }
        } else {
            uniqueCodeDescriptors = new ArrayList<>();
            bySchemaMap.put(descriptor.getSchemaClass(), uniqueCodeDescriptors);
        }
        uniqueCodeDescriptors.add(descriptor);
    }

    /**
     * 查询schema下指定的code值对应的{@link UniqueCodeDescriptor},前提是
     * {@link UniqueCodeDescriptor}必须首先调用{@link UniqueCodeDescriptor#register()}
     * 将自己注册进来.
     *
     * @param schemaClass code所处的schema
     * @param code        code
     * @return 该code对应的descriptor, <code>null</code>-如果没有找到
     */
    public <U extends UniqueCodeDescriptor<C>, D extends UniqueCodeDescriptorSchema, C> U lookup(Class<D> schemaClass, C code) {
        if (code == null) {
            return null;
        }
        U result = null;
        List<UniqueCodeDescriptor> descriptors = bySchemaMap.get(schemaClass);
        if (descriptors != null) {
            for (UniqueCodeDescriptor uniqueCodeDescriptor : descriptors) {
                if (uniqueCodeDescriptor.getSchemaClass().equals(schemaClass)
                        && uniqueCodeDescriptor.getCode().equals(code)) {
                    result = (U) uniqueCodeDescriptor;
                }
            }
        }
        if (result == null) {
            throw new IllegalStateException("UniqueCodeDescriptor schema = " + schemaClass + ",code = " + code + ",没有找到,可能是没有注册");
        }
        return result;
    }
}
