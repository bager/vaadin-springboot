package com.librebuy.app.views;

import com.librebuy.app.data.entity.User;
import com.librebuy.app.security.AuthenticatedUser;
import com.librebuy.app.views.about.AboutView;
import com.librebuy.app.views.addressform.AddressFormView;
import com.librebuy.app.views.chat.ChatView;
import com.librebuy.app.views.checkoutform.CheckoutFormView;
import com.librebuy.app.views.creditcardform.CreditCardFormView;
import com.librebuy.app.views.empty.EmptyView;
import com.librebuy.app.views.empty2.Empty2View;
import com.librebuy.app.views.empty3.Empty3View;
import com.librebuy.app.views.empty4.Empty4View;
import com.librebuy.app.views.gridwithfilters.GridwithFiltersView;
import com.librebuy.app.views.helloworld.HelloWorldView;
import com.librebuy.app.views.masterdetail.MasterDetailView;
import com.librebuy.app.views.personform.PersonFormView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Vaadin Demo");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(HelloWorldView.class)) {
            nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

        }
        if (accessChecker.hasAccess(GridwithFiltersView.class)) {
            nav.addItem(new SideNavItem("Grid with Filters", GridwithFiltersView.class,
                    LineAwesomeIcon.FILTER_SOLID.create()));

        }
        if (accessChecker.hasAccess(MasterDetailView.class)) {
            nav.addItem(
                    new SideNavItem("Master-Detail", MasterDetailView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));

        }
        if (accessChecker.hasAccess(PersonFormView.class)) {
            nav.addItem(new SideNavItem("Person Form", PersonFormView.class, LineAwesomeIcon.USER.create()));

        }
        if (accessChecker.hasAccess(AddressFormView.class)) {
            nav.addItem(
                    new SideNavItem("Address Form", AddressFormView.class, LineAwesomeIcon.MAP_MARKER_SOLID.create()));

        }
        if (accessChecker.hasAccess(CreditCardFormView.class)) {
            nav.addItem(new SideNavItem("Credit Card Form", CreditCardFormView.class,
                    LineAwesomeIcon.CREDIT_CARD.create()));

        }
        if (accessChecker.hasAccess(CheckoutFormView.class)) {
            nav.addItem(new SideNavItem("Checkout Form", CheckoutFormView.class, LineAwesomeIcon.CREDIT_CARD.create()));

        }
        if (accessChecker.hasAccess(ChatView.class)) {
            nav.addItem(new SideNavItem("Chat", ChatView.class, LineAwesomeIcon.COMMENTS.create()));

        }
        if (accessChecker.hasAccess(EmptyView.class)) {
            nav.addItem(new SideNavItem("Empty", EmptyView.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(Empty2View.class)) {
            nav.addItem(new SideNavItem("Empty2", Empty2View.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(Empty3View.class)) {
            nav.addItem(new SideNavItem("Empty3", Empty3View.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(Empty4View.class)) {
            nav.addItem(new SideNavItem("Empty4", Empty4View.class, LineAwesomeIcon.FILE.create()));

        }
        if (accessChecker.hasAccess(AboutView.class)) {
            nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Button gcButton = new Button("GC");
        gcButton.addClickListener(e -> System.gc());
        layout.add(gcButton);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
