package com.demo.wms.ui;

import com.demo.wms.util.vaadin.MyTheme;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.layouts.MPanel;

/**
 * Created by toxa on 8/26/2016.
 */

public class WarehouseApp extends HorizontalLayout {


    private NavBar navBar;

    private MPanel viewContent = new MPanel()
            .withSize(MSize.FULL_SIZE)
            .withStyleName(MyTheme.PANEL_BORDERLESS);
    private SpringViewProvider viewProvider;

    public WarehouseApp(NavBar navBar, SpringViewProvider viewProvider) {
        this.navBar = navBar;
        this.viewProvider = viewProvider;
        init();
    }

    public void init() {
        setSizeFull();
        setStyleName(MyTheme.MAIN_LAYOUT);
        addComponents(navBar, viewContent);
        setExpandRatio(viewContent, 1);

        Navigator navigator = new Navigator(MainUI.getCurrent(), viewContent);
        navigator.addProvider(viewProvider);
        navigator.addViewChangeListener(navBar);
        navigator.navigateTo(navigator.getState());

    }
}
