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
import java.util.Random;

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

        Div parentDiv = new Div(null);

        Div textDiv = parentDiv.$div()
                .style(s -> s.setColor("red"))
                .setText("Pierwszy Div");

        Div styleDiv = parentDiv.$div()
                .addProperty("data", LocalDateTime::now);
        styleDiv.styleSupplier(s -> {
                    System.out.println("new style:" + LocalDateTime.now());
                    LocalDateTime data = styleDiv.getProperty("data", LocalDateTime.class);
                    System.out.println("style:" + data);

                    boolean isOdd = LocalDateTime.now().getSecond() % 2 == 0;
                    s.setColor(isOdd ? "blue" : "green");
                })
                .setText(() -> {
                    System.out.println("new text:" + LocalDateTime.now());
                    LocalDateTime data = styleDiv.getProperty("data", LocalDateTime.class);
                    System.out.println("text:" + data);
                    return "Drugi Div " + (LocalDateTime.now());
                });

        Div rendererDiv = parentDiv.$div()
                .addSupplier(div -> {
                    Random random = new Random();
                    int num = random.nextInt(5) + 1;
                    for (int i = 0; i < num; i++) {
                        div.$div().setText("Div " + i);
                    }
                });

        Button refreshButton = new Button("Odśwież", event -> {
            styleDiv.update();
            rendererDiv.update();
        });

        add(parentDiv.getBase(), refreshButton);
    }

}
