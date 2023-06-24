package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentFactory;
import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

public interface ComponentHasComponents<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends ComponentWrapper<SELF, BASE>, ComponentFactory<SELF, BASE> {

    private HasComponents hasComponents() {
        assert getBase() instanceof HasComponents;
        return (HasComponents) getBase();
    }

    default SELF add(LBComponent<?, ?> component) {
        return add(component.getBase());
    }

    default SELF add(Component component) {
        hasComponents().add(component);
        return self();
    }


//    default SELF add(Component component) {
//        ComponentHasChildren<?, ?> container = getChildrenContainer();
//        Component base = Objects.requireNonNull(container, "Container is missing").getBase();
//
//        if (!(base instanceof HasComponents))
//            throw new ServiceException("Incorrect parent");
//
//        ((HasComponents) base).add(component);
//        getHelper().addListener.dispatch(null, component);
//        return self();
//    }
//
//    default SELF add(NBComponent<?, ?> component) {
//        ComponentHasChildren<?, ?> container = getChildrenContainer();
//        Component base = Objects.requireNonNull(container, "Container is missing").getBase();
//
//        if (!(base instanceof HasComponents))
//            throw new ServiceException("Incorrect parent");
//
//        Component c = component.asComponent();
//        ((HasComponents) base).add(c);
//
//        getHelper().addListener.dispatch(component, c);
//        return self();
//    }

}
