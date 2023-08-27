package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentFactory;
import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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

    default SELF addSupplier(Consumer<SELF> consumer) {
        // FixMe: Add function to remove all children
        getHelper().addSupplier("renderer", self -> {
            removeAll();
            consumer.accept(self);
        }, this::self);
        return self();
    }

    default SELF remove(LBComponent<?, ?>... components) {
        List<Component> baseComponents = Arrays.stream(components).map(LBComponent::getBase).map(Component.class::cast).toList();
        hasComponents().remove(baseComponents);
        return self();
    }

    default SELF remove(Component... components) {
        hasComponents().remove(components);
        return self();
    }

    default SELF removeAll() {
        hasComponents().removeAll();
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
