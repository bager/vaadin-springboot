package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentProps;
import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;

import java.util.function.Function;
import java.util.function.Supplier;

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

    default SELF setText(Supplier<String> text) {
        getHelper().addSupplier("innerText", this::setText, text);
        return self();
    }

    default SELF setText(Function<ComponentProps<SELF, BASE>, String> text) {
        return setText(() -> text.apply(this));
    }

    default String getText() {
        return getBase().getElement().getText();
    }

}
