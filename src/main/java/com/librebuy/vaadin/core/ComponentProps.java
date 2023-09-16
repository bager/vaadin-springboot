package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;

import java.util.function.Supplier;

public interface ComponentProps<SELF extends LBComponent<SELF, BASE>, BASE extends Component> {

    <T> T getProperty(String name, Class<T> propClass);

    SELF addProperty(String name, Supplier<Object> propSupplier);

}
