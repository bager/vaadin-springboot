package com.librebuy.vaadin.core;

import com.librebuy.core.Utils;
import com.librebuy.vaadin.core.intf.ComponentHasComponents;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import lombok.Getter;

public class LBComponent<SELF extends LBComponent<SELF, BASE>, BASE extends Component>
        implements ComponentWrapper<SELF, BASE> {

    @Getter
    protected final BASE base;
    protected final HasSessionContext context;

    protected LBComponent(HasSessionContext context, BASE base) {
        this.base = base;
        this.context = context;

        if (context instanceof ComponentHasComponents<?, ?> hasComponents)
            hasComponents.add(this);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        String text = this.getBase() instanceof HasText hasText && !Utils.isEmpty(hasText.getText()) ? hasText.getText() : null;

        System.out.printf("-------- Finalize: %s (%s) --------%n", getClass().getSimpleName(), text);
        super.finalize();
    }

}
