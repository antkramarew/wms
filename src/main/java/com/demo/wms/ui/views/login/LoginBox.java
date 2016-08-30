package com.demo.wms.ui.views.login;

import com.demo.wms.domain.User;
import com.demo.wms.exeptions.LoginException;
import com.demo.wms.services.LoginService;
import com.demo.wms.ui.event.LoginEvent;
import com.demo.wms.util.vaadin.MyTheme;
import com.google.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;

public class LoginBox extends VerticalLayout {

    private LoginService loginService;
    private EventBus eventBus;

    private TextField username;
    private PasswordField password;

    public LoginBox(LoginService loginService, EventBus eventBus) {
        this.loginService = loginService;
        this.eventBus = eventBus;
        init();
    }

    public void init() {
        setWidth("400px");
        addStyleName(MyTheme.LOGIN_BOX);
        setSpacing(true);
        setMargin(true);

        addCaption();
        addForm();
        addButtons();
    }

    private void addCaption() {
        Label caption = new Label("Login to system");
        addComponent(caption);

        caption.addStyleName(MyTheme.LABEL_H1);
    }

    private void addForm() {
        FormLayout loginForm = new FormLayout();
        username = new TextField("Username");
        password = new PasswordField("Password");
        loginForm.addComponents(username, password);
        addComponent(loginForm);

        loginForm.setSpacing(true);
        loginForm.forEach(component -> component.setWidth("100%"));

        username.focus();
    }

    private void addButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button forgotButton = new Button("Forgot", click -> Notification.show("Not implemented", Notification.Type.TRAY_NOTIFICATION));
        Button loginButton = new Button("Login", click -> login());
        buttonsLayout.addComponents(forgotButton, loginButton);
        addComponent(buttonsLayout);

        buttonsLayout.setSpacing(true);

        forgotButton.addStyleName(MyTheme.BUTTON_LINK);

        loginButton.addStyleName(MyTheme.BUTTON_PRIMARY);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
    }

    private void login() {
        try {
            User user = loginService.login(username.getValue(), password.getValue());
            eventBus.post(new LoginEvent(user));
        } catch (LoginException e) {
            Notification.show("Login failed.", e.getMessage(), Notification.Type.WARNING_MESSAGE);
            username.focus();
        }
    }
}
