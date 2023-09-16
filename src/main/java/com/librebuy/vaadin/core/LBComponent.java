package com.librebuy.vaadin.core;

import com.librebuy.core.Utils;
import com.librebuy.vaadin.core.intf.ComponentHasComponents;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import lombok.Getter;

import java.util.function.Supplier;

public class LBComponent<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        implements ComponentWrapper<SELF, BASE> {

    @Getter
    protected final BASE base;
    protected final HasSessionContext context;

    protected final ComponentHelper<SELF, BASE> helper;

    @SuppressWarnings("unchecked")
    protected LBComponent(HasSessionContext context, BASE base) {
        this.base = base;
        this.context = context;
        this.helper = new ComponentHelper<>((SELF) this);

        LBComponentTracker.trackCreate(base);

        if (context instanceof ComponentHasComponents<?, ?> hasComponents) {
            hasComponents.add(this);

            LBComponentTracker.trackAttach(base);
        }
    }

    @Override
    public <T> T getProperty(String name, Class<T> propClass) {
        return helper.getProperty(name, propClass);
    }

    @Override
    public SELF addProperty(String name, Supplier<Object> propSupplier) {
        helper.addProperty(name, propSupplier);
        return self();
    }

    public void update() {
        helper.update();
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        String text = this.getBase() instanceof HasText hasText && !Utils.isEmpty(hasText.getText()) ? hasText.getText() : null;

        System.out.printf("-------- Finalize: %s (%s) --------%n", getClass().getSimpleName(), text);
        super.finalize();
    }

}
