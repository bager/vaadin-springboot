package com.librebuy.vaadin.core.intf;

import com.librebuy.vaadin.core.ComponentProps;
import com.librebuy.vaadin.core.ComponentWrapper;
import com.librebuy.vaadin.core.LBComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.dom.Style;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ComponentHasStyle<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        extends ComponentWrapper<SELF, BASE> {

    private HasStyle hasStyle() {
        return getBase();
    }

    default Style getStyle() {
        return hasStyle().getStyle();
    }

    default SELF style(String name, String value) {
        getStyle().set(name, value);
        return self();
    }

    default SELF style(Consumer<Style> styleConsumer) {
        styleConsumer.accept(getStyle());
        return self();
    }

    default SELF styleSupplier(Consumer<Style> styleSupplier) {
        getHelper().addSupplier("style", styleSupplier, this::getStyle);
        return self();
    }

    default SELF styleSupplier(BiConsumer<ComponentProps<SELF, BASE>, Style> styleSupplier) {
        return styleSupplier(style -> styleSupplier.accept(this, style));
    }

    default boolean hasClassName(String className) {
        return hasStyle().hasClassName(className);
    }

    default SELF className(String className) {
        return className(className, true);
    }

    default SELF className(String className, boolean add) {
        if (add)
            hasStyle().addClassName(className);
        else
            hasStyle().setClassName(className);
        return self();
    }

    default SELF removeClassName(String className) {
        hasStyle().setClassName(className, false);
        return self();
    }

    default SELF removeAllClassNames() {
        hasStyle().setClassName(null);
        return self();
    }


//    default boolean hasClass(String className) {
//        return hasStyle().hasClassName(className);
//    }
//
//    /**
//     * Style typu inline - generowane jako atrybut style danego tagu
//     */
//    default Selector style() {
//        Style style = hasStyle().getStyle();
//        return new Selector().onChange(style::set);
//    }
//
//    default SELF style(Consumer<Selector> consumer) {
//        Style style = hasStyle().getStyle();
//        Selector selector = new Selector();
//        selector.onChange(style::set);
//        consumer.accept(selector);
//        return self();
//    }
//
//    default SELF styleSupplier(Consumer<Selector> consumer) {
//        getHelper().addSupplier("style", consumer, this::style);
//        return self();
//    }
//
//    default SELF markAsToDo() {
//        style().color("red")
//                .cursor(Cursor.NOT_ALLOWED);
//        self().hint(I18n.NOT_IMPLEMENTED);
//        return self();
//    }

}
