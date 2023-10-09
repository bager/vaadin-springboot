package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.librebuy.vaadin.core.intf.event.ComponentValueChangeEvent;
import com.librebuy.vaadin.core.intf.event.ValueChangeEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;

import java.io.Serializable;
import java.util.EventListener;
import java.util.function.Consumer;

public interface ComponentHasValue<SELF extends LBComponent<SELF, BASE>, BASE extends Component, T,
        V extends ValueChangeEvent<SELF, T>>
        extends ComponentWrapper<SELF, BASE> {

    @SuppressWarnings("unchecked")
    private HasValue<?, T> hasValue() {
        return (HasValue<?, T>) getBase();
    }

    default T getValue() {
        return hasValue().getValue();
    }

    default SELF setValue(T value) {
        hasValue().setValue(value);
        return self();
    }

    @FunctionalInterface
    interface ValueChangeListener<E extends ValueChangeEvent<?, ?>> extends EventListener, Serializable {
        void valueChanged(E event);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default V convertEvent(HasValue.ValueChangeEvent event) {
        return (V) new ComponentValueChangeEvent<>(self(), event.isFromClient(), event.getOldValue(), event.getValue());
    }

    default SELF onChange(ValueChangeListener<V> valueChangeListener) {
        hasValue().addValueChangeListener(event -> valueChangeListener.valueChanged(convertEvent(event)));
        return self();
    }

    default SELF onValueChange(Consumer<T> valueListener) {
        return onChange(event -> valueListener.accept(event.getValue()));
    }

}
