package com.demo.wms.ui;

import com.demo.wms.services.LoginService;
import com.demo.wms.ui.event.LogoutEvent;
import com.demo.wms.ui.event.NavigationEvent;
import com.demo.wms.ui.event.LoginEvent;
import com.demo.wms.ui.views.login.LoginView;
import com.demo.wms.util.CurrentUser;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by toxa on 8/21/2016.
 */
@Theme("mytheme")
@SpringUI(path = "/wms")
@Widgetset("com.demo.wms.MyAppWidgetset")
public class MainUI extends UI {


    @Autowired
    private EventBus eventBus;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SpringViewProvider viewProvider;
    @Autowired
    private NavBar navBar;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        eventBus.register(this);
        if (CurrentUser.isLoggedIn()) {
            setContent(new WarehouseApp(navBar, viewProvider));
        } else {
            setContent(new LoginView(loginService, eventBus));
        }
    }

    @Subscribe
    public void userLoggedIn(LoginEvent event) {
        CurrentUser.set(event.getUser());
        setContent(new WarehouseApp(navBar, viewProvider));
    }

    @Subscribe
    public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }

    @Subscribe
    public void logout(LogoutEvent logoutEvent) {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();

    }

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }



}
