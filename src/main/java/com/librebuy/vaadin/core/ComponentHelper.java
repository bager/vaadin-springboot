package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ComponentHelper<COMPONENT extends LBComponent<COMPONENT, BASE>, BASE extends Component> {

    private final COMPONENT component;

    private Map<String, ValueSupplier<?>> suppliers = new HashMap<>();

    public ComponentHelper(COMPONENT component) {
        this.component = component;
    }

    public <T> void addSupplier(String name, Consumer<T> setter, Supplier<? extends T> getter) {
        suppliers.put(name, new ValueSupplier<>(name, setter, getter));
    }

    public void update() {
        suppliers.values().forEach(ValueSupplier::update);
    }
}
