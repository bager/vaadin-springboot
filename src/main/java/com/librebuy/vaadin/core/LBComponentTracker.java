package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.internal.ComponentTracker;
import com.vaadin.flow.router.internal.AbstractNavigationStateRenderer;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class LBComponentTracker {

    private static boolean trackerInitialized = false;
    private static Boolean productionMode;
    private static String[] prefixesToSkip = new String[]{
            "com.librebuy.vaadin.component.",
            "com.librebuy.vaadin.core."
    };
    private static Map<Component, ComponentTracker.Location> createLocation;
    private static Map<Component, ComponentTracker.Location> attachLocation;

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private static void loadTrackerData() {
        if (trackerInitialized)
            return;

        Class<ComponentTracker> trackerClass = ComponentTracker.class;

        productionMode = (Boolean) getStaticFieldValue(trackerClass, "productionMode");
        prefixesToSkip = mergeArrays(prefixesToSkip, (String[]) getStaticFieldValue(trackerClass, "prefixesToSkip"));
        createLocation = (Map<Component, ComponentTracker.Location>) getStaticFieldValue(trackerClass, "createLocation");
        attachLocation = (Map<Component, ComponentTracker.Location>) getStaticFieldValue(trackerClass, "attachLocation");

        trackerInitialized = true;
    }

    public static void trackCreate(Component component) {
        loadTrackerData();

        if (productionMode) {
            return;
        }

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ComponentTracker.Location location = findRelevantLocation(component.getClass(), stack,
                null);
        if (isNavigatorCreate(location)) {
            location = findRelevantLocation(null, stack, null);
        }
        createLocation.put(component, location);
    }

    public static void trackAttach(Component component) {
        loadTrackerData();

        if (productionMode) {
            return;
        }
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        // In most cases the interesting attach call is found in the same class
        // where the component was created and not in a generic layout class
        ComponentTracker.Location location = findRelevantLocation(component.getClass(), stack,
                findCreate(component));
        if (isNavigatorCreate(location)) {
            // For routes, we can just show the init location as we have nothing
            // better
            location = createLocation.get(component);
        }
        attachLocation.put(component, location);
    }

    public static ComponentTracker.Location findCreate(Component component) {
        return createLocation.get(component);
    }

    private static boolean isNavigatorCreate(ComponentTracker.Location location) {
        return location.className()
                .equals(AbstractNavigationStateRenderer.class.getName());
    }

    private static ComponentTracker.Location findRelevantLocation(
            Class<? extends Component> excludeClass, StackTraceElement[] stack,
            ComponentTracker.Location preferredClass) {
        List<StackTraceElement> candidates = Stream.of(stack)
                .filter(e -> excludeClass == null
                        || !e.getClassName().equals(excludeClass.getName()))
                .filter(e -> {
                    for (String prefixToSkip : prefixesToSkip) {
                        if (e.getClassName().startsWith(prefixToSkip)) {
                            return false;
                        }
                    }
                    return true;
                }).toList();
        if (preferredClass != null) {
            Optional<StackTraceElement> preferredCandidate = candidates.stream()
                    .filter(e -> e.getClassName()
                            .equals(preferredClass.className()))
                    .findFirst();
            if (preferredCandidate.isPresent()) {
                return toLocation(preferredCandidate.get());
            }
        }
        return toLocation(candidates.stream().findFirst().orElse(null));
    }

    private static ComponentTracker.Location toLocation(StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            return null;
        }

        String className = stackTraceElement.getClassName();
        String fileName = stackTraceElement.getFileName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        return new ComponentTracker.Location(className, fileName, methodName, lineNumber);
    }

    @SneakyThrows
    private static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(null);
    }

    private static String[] mergeArrays(String[] a, String[] b) {
        List<String> resultList = new ArrayList<>(a.length + b.length);
        Collections.addAll(resultList, a);
        Collections.addAll(resultList, b);
        return resultList.toArray(new String[0]);
    }

}
