package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ComponentHelper<COMPONENT extends LBComponent<COMPONENT, BASE>, BASE extends Component> {

    private final COMPONENT component;

    private final Map<String, Object> props = new HashMap<>();
    private final Map<String, Supplier<Object>> propsSupplier = new HashMap<>();

    private final Map<String, ValueSupplier<?>> suppliers = new HashMap<>();

    public ComponentHelper(COMPONENT component) {
        this.component = component;
    }

    public <T> T getProperty(String name, Class<T> propClass) {
        if (!props.containsKey(name) && propsSupplier.containsKey(name))
            props.put(name, propsSupplier.get(name).get());

        Object propValue = props.get(name);
        if (propValue == null)
            return null;
        return propClass.cast(propValue);
    }

    public void addProperty(String name, Supplier<Object> propSupplier) {
        propsSupplier.put(name, propSupplier);
    }

    public <T> void addSupplier(String name, Consumer<T> setter, Supplier<? extends T> getter) {
        suppliers.put(name, new ValueSupplier<>(name, setter, getter));
    }

    public void update() {
        propsSupplier.forEach((propName, propValueSupplier) -> props.put(propName, propValueSupplier.get()));
        suppliers.values().forEach(ValueSupplier::update);
    }
}
