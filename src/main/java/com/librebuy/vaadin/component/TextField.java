package com.librebuy.vaadin.component;

import com.librebuy.vaadin.core.HasSessionContext;
import com.librebuy.vaadin.core.LBComponent;
import com.librebuy.vaadin.core.intf.ComponentHasStyle;
import com.librebuy.vaadin.core.intf.ComponentHasValue;
import com.librebuy.vaadin.core.intf.event.ComponentValueChangeEvent;

public class TextField extends LBComponent<TextField, com.vaadin.flow.component.textfield.TextField> implements
        ComponentHasStyle<TextField, com.vaadin.flow.component.textfield.TextField>,
        ComponentHasValue<TextField, com.vaadin.flow.component.textfield.TextField, String, ComponentValueChangeEvent<TextField, String>> {

    public TextField(HasSessionContext context) {
        super(context, new com.vaadin.flow.component.textfield.TextField());
    }

}
