package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;

public interface ComponentWrapper<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends HasSessionContext {

    BASE getBase();

    @SuppressWarnings("unchecked")
    default SELF self() {
        return (SELF) this;
    }

}
