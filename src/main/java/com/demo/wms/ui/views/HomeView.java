package com.demo.wms.ui.views;

import com.demo.wms.util.CurrentUser;
import com.demo.wms.util.vaadin.MyTheme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by toxa on 8/21/2016.
 */

@SpringView(name = "")
public class HomeView extends VerticalLayout implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @PostConstruct
    public void init() {
        Label caption = new Label("Welcome, "  /* +CurrentUser.get().getLogin()*/);
        Label description = new Label(
                "Последние продажи <br/> Зарегистрированые пользоватили и т.д", ContentMode.HTML);

        addComponents(caption, description);

        caption.addStyleName(MyTheme.LABEL_HUGE);
        description.addStyleName(MyTheme.LABEL_LARGE);
    }
}
