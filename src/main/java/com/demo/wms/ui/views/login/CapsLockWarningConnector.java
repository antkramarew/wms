package com.demo.wms.ui.views.login;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VOverlay;

/**
 * Created by anton_kramarev on 9/9/2016.
 */
public class CapsLockWarningConnector extends AbstractExtensionConnector {

    @Override
    protected void extend(ServerConnector target) {

        final Widget passwordWidget = ((ComponentConnector)target).getWidget();
        final VOverlay warning = new VOverlay();
        warning.setOwner(passwordWidget);
        warning.add(new HTML("CapsLock is enabled"));
        passwordWidget.addDomHandler(keyPressEvent -> {
            if (isEnabled() && isCapsLockOn(keyPressEvent)) {
                warning.showRelativeTo(passwordWidget);
            } else {
                warning.hide();
            }
        }, KeyPressEvent.getType());
    }

    private boolean isCapsLockOn(KeyPressEvent e) {
        return e.isShiftKeyDown() ^ Character.isUpperCase(e.getCharCode());
    }
}
