package com.demo.wms.ui.views;

import com.demo.wms.ui.views.form.order.OrderView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;

/**
 * Created by toxa on 8/21/2016.
 */
public enum  Views {

    HOME_VIEW(HomeView.class, FontAwesome.BOOKMARK_O),
    ORDER_HISTORY_VIEW(OrderHistoryView.class,FontAwesome.FILES_O),
    PRODUCT_VIEW(ProductView.class,FontAwesome.CUBES),
    ORDER_VIEW(OrderView.class, FontAwesome.DOLLAR),
    USER_VIEW(UserView.class, FontAwesome.USERS);

    private Class<? extends View> viewClass;
    private FontAwesome icon;
    private String viewName;

    Views(Class<? extends View> viewClass, FontAwesome icon) {
        this.viewClass = viewClass;
        this.icon = icon;
        this.viewName = viewClass.getAnnotation(SpringView.class).name();
    }

    public Class getViewClass() {
        return viewClass;
    }

    public FontAwesome getIcon() {
        return icon;
    }

    public String getViewName() {
        return viewName;
    }
}
