package com.librebuy.core;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public static boolean isEmpty(Object value) {
        if (value == null)
            return true;

        if (value instanceof String stringVal)
            return stringVal.trim().isEmpty();

        if (value instanceof Collection<?> collectionVal)
            return collectionVal.isEmpty();

        if (value instanceof Map<?, ?> mapVal)
            return mapVal.isEmpty();

        if (value instanceof Array arrayVal)
            return Array.getLength(arrayVal) == 0;

        return Objects.toString(value).trim().isEmpty();
    }

}
