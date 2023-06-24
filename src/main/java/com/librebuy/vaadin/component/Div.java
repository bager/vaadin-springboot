package com.librebuy.vaadin.component;

import com.librebuy.vaadin.core.HasSessionContext;
import com.librebuy.vaadin.core.LBComponent;
import com.librebuy.vaadin.core.intf.ComponentHasComponents;
import com.librebuy.vaadin.core.intf.ComponentHasStyle;
import com.librebuy.vaadin.core.intf.ComponentHasText;

public class Div extends LBComponent<Div, com.vaadin.flow.component.html.Div> implements
        ComponentHasStyle<Div, com.vaadin.flow.component.html.Div>,
        ComponentHasText<Div, com.vaadin.flow.component.html.Div>,
        ComponentHasComponents<Div, com.vaadin.flow.component.html.Div> {

    public Div(HasSessionContext context) {
        super(context, new com.vaadin.flow.component.html.Div());
    }

}
