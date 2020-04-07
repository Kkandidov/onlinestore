package org.astashonok.onlinestore.util.inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class FieldReflector {
    static List<Field> collectUpTo(Class<?> clazz, Class<?> upperBound) {
        ArrayList<Field> result = new ArrayList<>();
        Class<?> current = clazz;
        while (current != upperBound) {
            result.addAll(asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return result;
    }

    static List<Field> filterInject(List<Field> allFields) {
        ArrayList<Field> result = new ArrayList<>();
        for (Field field : allFields) {
            Inject annotation = field.getAnnotation(Inject.class);
            if (annotation != null) {
                result.add(field);
            }
        }
        return result;
    }
}
