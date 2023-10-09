package com.librebuy.app.views.dashboard;

import com.librebuy.app.views.MainLayout;
import com.librebuy.vaadin.component.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.util.Random;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setSpacing(false);
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

        final String DATE_PROP = "date";
        Div styleDiv = parentDiv.$div()
                .addProperty(DATE_PROP, LocalDateTime::now)
                .styleSupplier((props, style) -> {
                    System.out.println("new style:" + LocalDateTime.now());
                    LocalDateTime data = props.getProperty(DATE_PROP, LocalDateTime.class);
                    System.out.println("style:" + data);

                    boolean isOdd = LocalDateTime.now().getSecond() % 2 == 0;
                    style.setColor(isOdd ? "blue" : "green");
                })
                .setText(props -> {
                    System.out.println("new text:" + LocalDateTime.now());
                    LocalDateTime data = props.getProperty(DATE_PROP, LocalDateTime.class);
                    System.out.println("text:" + data);
                    return "Drugi Div " + (LocalDateTime.now());
                });

        Div rendererDiv = parentDiv.$div()
                .addSupplier(div -> {
                    Random random = new Random();
                    int num = random.nextInt(5) + 1;
                    for (int i = 0; i < num; i++) {
                        div.$div().setText("Div " + i);
                        div.$textField().setValue("Val " + i)
                                .onChange(event -> {
                                    System.out.println("EVENT:" + event);
                                })
                                .onValueChange(val -> {
                                    System.out.println("VAL: " + val);
                                });

                    }
                });

        Button refreshButton = new Button("Odśwież", event -> {
            styleDiv.update();
            rendererDiv.update();
        });

        add(parentDiv.getBase(), refreshButton);
    }

}
