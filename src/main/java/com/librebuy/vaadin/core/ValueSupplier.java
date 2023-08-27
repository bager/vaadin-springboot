package com.librebuy.vaadin.core;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

class ValueSupplier<T> {

    @Getter
    private final String name;
    private final Consumer<T> setter;
    private final Supplier<? extends T> getter;


    ValueSupplier(String name, Consumer<T> setter, Supplier<? extends T> getter) {
        this.setter = Objects.requireNonNull(setter);
        this.getter = Objects.requireNonNull(getter);
        this.name = name;
        update();
    }

    public void update() {
        setter.accept(getter.get());
    }

}
