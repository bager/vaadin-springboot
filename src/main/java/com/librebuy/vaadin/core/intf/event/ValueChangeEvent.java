package com.librebuy.vaadin.core.intf.event;

import com.librebuy.vaadin.core.LBComponent;

import java.io.Serializable;

public interface ValueChangeEvent<V extends LBComponent<?, ?>, T> extends Serializable {

    V getHasValue();

    boolean isFromClient();

    T getOldValue();

    T getValue();

}