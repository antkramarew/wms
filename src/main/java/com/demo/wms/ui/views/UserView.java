package com.demo.wms.ui.views;

import com.demo.wms.domain.User;
import com.demo.wms.services.UserService;
import com.demo.wms.ui.components.VerticalSpacedLayout;
import com.demo.wms.ui.views.form.UserForm;
import com.demo.wms.util.Caption;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.MBeanFieldGroup.MValidator;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by toxa on 8/26/2016.
 */
@SpringView(name = "user_view")
public class UserView extends VerticalSpacedLayout implements View {


    public static final String NOTIFICATION_LOGIN_ALREADY_EXISTS = "notification.login.already.exists";
    public static final String COMMON_CONFIRMATION_TEXT = "common.confirmation.text";

    @Autowired
    private UserService userService;
    @Autowired
    private I18N messages;

    private Map<Caption, String> captions;
    private Map<Caption, Validator> validators;


    private UserForm addUserForm;
    private UserForm editUserForm;


    private Grid grid;
    private BeanItemContainer<User> userBeanItemContainer;


    private AbstractForm.SavedHandler<User> editSaveHandler = user -> {
        userService.saveUser(user);
        updateContainer(user);
        editUserForm.closePopup();
    };

    private AbstractForm.SavedHandler<User> addSaveHandler = user -> {
        userService.saveUser(user);
        updateContainer(user);
        addUserForm.closePopup();
    };
    private Validator loginAlreadyExistsValidator = value -> {
        if(userService.isLoginExists((String) value)){
            throw new Validator.InvalidValueException(messages.get(NOTIFICATION_LOGIN_ALREADY_EXISTS));
        }
    };


    private void updateContainer(User entity) {
        if (userBeanItemContainer.containsId(entity)) {
            userBeanItemContainer.removeItem(entity);
        }
        userBeanItemContainer.addBean(entity);
        userBeanItemContainer.sort(new Object[]{"login"}, new boolean[]{true});
        grid.select(entity);
    }

    private AbstractForm.ResetHandler<User> addResetHandler = (entity -> {
        addUserForm.closePopup();
    });

    private AbstractForm.ResetHandler<User> editResetHandler = (entity -> {
        editUserForm.closePopup();
    });

    private final MButton add = new MButton(FontAwesome.PLUS,
            event -> {
                addUserForm.setEntity(new User());
                addUserForm.openInModalPopup();
                addUserForm.focusFirst();
            });

    private final ConfirmButton delete = new ConfirmButton(FontAwesome.TRASH_O, "",
            event -> {
                Long id = ((User) grid.getSelectedRow()).getId();
                userService.delete(id);
                userBeanItemContainer.removeItem(grid.getSelectedRow());
            });

    private final MButton edit = new MButton(FontAwesome.PENCIL_SQUARE_O,
            event -> {
                Long id = ((User) grid.getSelectedRow()).getId();
                editUserForm.setEntity(userService.findUser(id));
                editUserForm.openInModalPopup();
            });

    private final MCssLayout actions = new MCssLayout(add, edit, delete)
            .withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


    @PostConstruct
    public void init() {
        initForm();
        initGrid();
        delete.setConfirmationText(messages.get(COMMON_CONFIRMATION_TEXT));
        addComponents(actions, grid);
        applyButtonStates();

    }

    private void initCaptions() {
        captions = new HashMap<>();
        captions.put(Caption.CANCEL, messages.get(Caption.CANCEL.getCode()));
        captions.put(Caption.LOGIN, messages.get(Caption.LOGIN.getCode()));
        captions.put(Caption.SAVE, messages.get(Caption.SAVE.getCode()));
        captions.put(Caption.PASSWORD, messages.get(Caption.PASSWORD.getCode()));
        captions.put(Caption.ROLE, messages.get(Caption.ROLE.getCode()));

    }

    private void initForm() {
        initCaptions();
        initValidators();
        addUserForm = new UserForm()
                .withCaptions(captions)
                .withSaveHandler(addSaveHandler)
                .withResetHandler(addResetHandler)
                .withTitle(messages.get(Caption.TITLE_ADD.getCode()))
                .withValidators(validators);
        editUserForm = new UserForm()
                .withCaptions(captions)
                .withSaveHandler(editSaveHandler)
                .withResetHandler(editResetHandler)
                .withTitle(messages.get(Caption.TITLE_EDIT.getCode()));
    }

    private void initValidators() {
        validators = new HashMap<>();
        validators.put(Caption.LOGIN, loginAlreadyExistsValidator);
    }

    private void initGrid() {
        grid = new Grid();
        userBeanItemContainer = new BeanItemContainer<>(User.class, userService.getUsers());
        grid.setContainerDataSource(userBeanItemContainer);
        grid.setColumns("login", "role");
        grid.getColumn("login").setHeaderCaption(messages.get(Caption.LOGIN.getCode()));
        grid.getColumn("role").setHeaderCaption(messages.get(Caption.ROLE.getCode()));
        grid.addSelectionListener(event -> {
            applyButtonStates();
        });
    }

    private void applyButtonStates() {
        boolean isSelected = grid.getSelectedRow() != null;
        edit.setEnabled(isSelected);
        delete.setEnabled(isSelected);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


    }
}
