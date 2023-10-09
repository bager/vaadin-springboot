package com.librebuy.vaadin.core;

import com.librebuy.vaadin.component.Div;
import com.librebuy.vaadin.component.TextField;
import com.vaadin.flow.component.Component;

public interface ComponentFactory<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends ComponentWrapper<SELF, BASE> {

    // ----------------------------------------------------- DIV -------------------------------------------------------
    default Div $div() {
        return new Div(this);
    }

    // ------------------------------------------------- TEXT_FIELD ----------------------------------------------------
    default TextField $textField() {
        return new TextField(this);
    }

}