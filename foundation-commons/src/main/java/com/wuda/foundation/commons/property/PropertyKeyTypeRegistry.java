package com.wuda.foundation.commons.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyKeyTypeRegistry {

    private List<PropertyKeyType> propertyKeyTypes = new ArrayList<>();

    public void register(PropertyKeyType propertyKeyType) {
        propertyKeyTypes.add(propertyKeyType);
    }
}
