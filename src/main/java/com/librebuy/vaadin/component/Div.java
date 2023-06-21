package com.librebuy.vaadin.component;

import com.librebuy.vaadin.core.LBComponent;
import com.librebuy.vaadin.core.intf.ComponentHasStyle;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.dom.Element;

public class Div extends LBComponent<Div, com.vaadin.flow.component.html.Div>
    implements ComponentHasStyle<Div, com.vaadin.flow.component.html.Div> {

    public Div() {
        super(new com.vaadin.flow.component.html.Div());
    }

}
