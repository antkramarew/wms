package com.demo.wms.ui.views.form;

import com.demo.wms.domain.User;
import com.demo.wms.services.UserService;
import com.demo.wms.util.Caption;
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
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by toxa on 8/26/2016.
 */
public class UserForm extends AbstractForm<User> {

    @PropertyId("login")
    private MTextField login = new MTextField()
            .withRequired(true);
    @PropertyId("password")
    private MPasswordField password = new MPasswordField()
            .withRequired(true);
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

    public UserForm withCaptions(Map<Caption,String> captions) {
        login.setCaption(captions.get(Caption.LOGIN));
        password.setCaption(captions.get(Caption.PASSWORD));
        role.setCaption(captions.get(Caption.ROLE));
        save.setCaption(captions.get(Caption.SAVE));
        cancel.setCaption(captions.get(Caption.CANCEL));
        return this;
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

    public UserForm withValidators(Map<Caption, Validator> validators) {
        login.addValidator(validators.get(Caption.LOGIN));
        return this;
    }

}
