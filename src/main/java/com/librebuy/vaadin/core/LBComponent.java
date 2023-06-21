package com.librebuy.vaadin.core;

import com.vaadin.flow.component.Component;
import lombok.Getter;

public class LBComponent<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        implements ComponentWrapper<SELF, BASE> {

    @Getter
    protected final BASE base;

    protected LBComponent(BASE base) {
        this.base = base;
    }

}
