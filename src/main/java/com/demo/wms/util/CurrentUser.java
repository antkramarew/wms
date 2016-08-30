package com.demo.wms.util;

import com.demo.wms.domain.User;
import com.vaadin.server.VaadinSession;

public class CurrentUser {

    private static final String KEY = "currentser";

    public static void set(User user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
    }

    public static User get() {
        return (User) VaadinSession.getCurrent().getAttribute(KEY);
    }

    public static boolean isLoggedIn() {
        return get() != null;
    }
}