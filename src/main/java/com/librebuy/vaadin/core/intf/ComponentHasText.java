package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;

@SuppressWarnings("unused")
public interface ComponentHasText<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends ComponentWrapper<SELF, BASE> {

    private HasText hasText() {
        assert getBase() instanceof HasText;
        return (HasText) getBase();
    }

    default SELF setText(String text) {
        hasText().setText(text);
        return self();
    }

    default String getText() {
        return getBase().getElement().getText();
    }

}
