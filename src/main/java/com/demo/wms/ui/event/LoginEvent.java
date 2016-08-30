package com.demo.wms.ui.event;

import com.demo.wms.domain.User;

public class LoginEvent {
    private User user;

    public LoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
