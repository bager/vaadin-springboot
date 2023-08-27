package com.librebuy.app.views.empty;

import com.librebuy.app.views.MainLayout;
import com.librebuy.vaadin.component.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import java.time.LocalDateTime;

@PageTitle("Empty")
@Route(value = "empty", layout = MainLayout.class)
@AnonymousAllowed
public class EmptyView extends VerticalLayout {

    public EmptyView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        Div firstDiv = new Div(null)
                .style("color", "red")
                .setText("Pierwszy firstDiv");

        Div secondDiv = firstDiv.$div()
                .style("color", "blue")
                .setText(() -> "Drugi secondDiv " + (LocalDateTime.now()));

        Button refreshButton = new Button("Odśwież", event -> {
            secondDiv.update();
        });

        add(firstDiv.getBase(), refreshButton);
    }

}
