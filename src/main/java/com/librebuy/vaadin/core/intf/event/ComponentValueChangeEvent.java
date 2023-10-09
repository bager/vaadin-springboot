package com.librebuy.vaadin.core.intf.event;

import com.librebuy.vaadin.core.LBComponent;
import lombok.Value;

@Value
public class ComponentValueChangeEvent<V extends LBComponent<?, ?>, T> implements ValueChangeEvent<V, T> {

    V hasValue;

    boolean fromClient;

    T oldValue;

    T value;

}
