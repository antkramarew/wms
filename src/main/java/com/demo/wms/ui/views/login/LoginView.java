package com.demo.wms.ui.views.login;

import com.demo.wms.services.LoginService;
import com.google.common.eventbus.EventBus;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



public class LoginView extends VerticalLayout {

    private LoginBox loginBox;

    public LoginView(LoginService loginService, EventBus eventBus) {
        this.loginBox = new LoginBox(loginService, eventBus);
        init();
    }

    public void init() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(loginBox);
    }
}
