package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;

public interface ComponentWrapper<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends HasSessionContext, ComponentProps<SELF, BASE> {

    BASE getBase();

    @SuppressWarnings("unchecked")
    default SELF self() {
        return (SELF) this;
    }

    default ComponentHelper<SELF, BASE> getHelper() {
        return self().helper;
    }

}
