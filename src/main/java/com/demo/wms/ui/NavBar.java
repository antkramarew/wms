package com.demo.wms.ui;

import com.demo.wms.ui.event.LogoutEvent;
import com.demo.wms.ui.views.Views;
import com.demo.wms.util.vaadin.MyTheme;
import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringComponent
@UIScope
public class NavBar extends CssLayout implements ViewChangeListener {

    public static final String NAV_BAR_HEADER = "nav.bar.header";
    @Autowired
    private EventBus eventBus;

    @Autowired
    private I18N messages;

    private Map<String, Button> buttonMap = new HashMap<>();


    public NavBar() {
    }

    @PostConstruct
    public void init() {
        setHeight("100%");
        addStyleName(MyTheme.MENU_ROOT);
        addStyleName(MyTheme.NAVBAR);

        Label logo = new Label("<strong>"+ messages.get(NAV_BAR_HEADER) + "</strong>", ContentMode.HTML);
        logo.addStyleName(MyTheme.MENU_TITLE);
        addComponent(logo);

        addLogoutButton();
        addViews();
    }

    private void addViews() {
        for (Views view : Views.values()) {
            addView(view.getViewName(), messages.get(getMessageCodeForView(view),null), view.getIcon());
        }
    }

    private String getMessageCodeForView(Views view) {
        String viewName = view.getViewName();
        viewName = viewName.equals("") ? "home_view" : viewName;
        return viewName + ".display.name";
    }

    private void addLogoutButton() {
        Button logout = new Button("Log out", click -> eventBus.post(new LogoutEvent()));
        addComponent(logout);

        logout.addStyleName(MyTheme.BUTTON_LOGOUT);
        logout.addStyleName(MyTheme.BUTTON_BORDERLESS);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }

    public void addView(String uri, String displayName, FontAwesome icon) {
        Button viewButton = new Button(displayName,
                click -> UI.getCurrent().getNavigator().navigateTo(uri)/*eventBus.post(new NavigationEvent(uri))*/);
        viewButton.addStyleName(MyTheme.MENU_ITEM);
        viewButton.addStyleName(MyTheme.BUTTON_BORDERLESS);
        viewButton.setIcon(icon);
        buttonMap.put(uri, viewButton);

        addComponent(viewButton, components.size() - 1);
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true; // false blocks navigation, always return true here
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        buttonMap.values().forEach(button -> button.removeStyleName(MyTheme.SELECTED));
        Button button = buttonMap.get(event.getViewName());
        if (button != null) button.addStyleName(MyTheme.SELECTED);
    }

}