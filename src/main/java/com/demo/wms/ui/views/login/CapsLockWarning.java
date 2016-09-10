package com.demo.wms.ui.views.login;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.PasswordField;

/**
 * Created by anton_kramarev on 9/9/2016.
 */
public class CapsLockWarning extends AbstractExtension {
    protected CapsLockWarning(PasswordField passwordField) {
        extend(passwordField);
    }

    public static CapsLockWarning warnFor(PasswordField passwordField) {
        return new CapsLockWarning(passwordField);
    }
}
