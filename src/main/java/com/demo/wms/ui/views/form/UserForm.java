package com.demo.wms.ui.views.form;

import com.demo.wms.domain.User;
import com.demo.wms.services.UserService;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by toxa on 8/26/2016.
 */
@org.springframework.stereotype.Component
@ViewScope
public class UserForm extends AbstractForm<User> {


    @Autowired
    private I18N messages;

    @PropertyId("login")
    private MTextField login = new MTextField()
            .withRequired(true);
    @PropertyId("password")
    private MPasswordField password = new MPasswordField().withRequired(true);
    @PropertyId("role")
    private ComboBox role = new ComboBox();


    private final MButton save = new MButton(FontAwesome.SAVE)
            .withClickShortcut(ShortcutAction.KeyCode.ENTER)
            .withStyleName(ValoTheme.BUTTON_PRIMARY);

    private final MButton cancel = new MButton()
            .withClickShortcut(ShortcutAction.KeyCode.ESCAPE);


    public UserForm() {
        setEagerValidation(true);
        setSaveButton(save);
        setResetButton(cancel);
    }


    @PostConstruct
    public void init() {
        setCaptions();
    }

    private void setCaptions() {
        login.setCaption(messages.get("common.login"));
        password.setCaption(messages.get("common.password"));
        role.setCaption(messages.get("common.role"));
        save.setCaption(messages.get("common.save"));
        cancel.setCaption(messages.get("common.cancel"));
    }

    @Override
    protected Component createContent() {
        role.addItems(User.Role.values());
        role.setNullSelectionAllowed(false);
        role.setRequired(true);

        MFormLayout formLayout = new MFormLayout(
                this.login,
                this.password,
                this.role        ).withWidth("");
        MCssLayout actions = new MCssLayout(save, cancel).withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        MVerticalLayout layout = new MVerticalLayout(formLayout.withMargin(true), actions)
                .withMargin(true);
        layout.setComponentAlignment(actions, Alignment.BOTTOM_RIGHT);
        return layout;
    }

    public UserForm withSaveHandler(SavedHandler<User> savedHandler) {
        setSavedHandler(savedHandler);
        return this;
    }

    public UserForm withResetHandler(ResetHandler<User> resetHandler) {
        setResetHandler(resetHandler);
        return this;
    }

    public UserForm withTitle(String title) {
        setModalWindowTitle(title);
        return this;
    }

}
