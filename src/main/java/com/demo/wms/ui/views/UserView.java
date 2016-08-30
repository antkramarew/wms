package com.demo.wms.ui.views;

import com.demo.wms.domain.User;
import com.demo.wms.exeptions.LoginAlreadyExistsException;
import com.demo.wms.services.UserService;
import com.demo.wms.ui.components.VerticalSpacedLayout;
import com.demo.wms.ui.views.form.UserForm;
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
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MCssLayout;

import javax.annotation.PostConstruct;

/**
 * Created by toxa on 8/26/2016.
 */
@SpringView(name="user_view")
public class UserView extends VerticalSpacedLayout implements View {


    public static final String NOTIFICATION_LOGIN_ALREADY_EXISTS = "notification.login.already.exists";
    public static final String COMMON_LOGIN = "common.login";
    public static final String COMMON_ROLE = "common.role";
    public static final String COMMON_CONFIRMATION_TEXT = "common.confirmation.text";

    @Autowired
    private UserService userService;
    @Autowired
    private I18N messages;
    @Autowired
    private UserForm form;

    private Grid grid;
    private BeanItemContainer<User> userBeanItemContainer;

    private AbstractForm.SavedHandler<User> savedHandler = (entity ->
            {
                if (userService.isLoginExists(entity.getLogin())) {
                    Notification.show(messages.get(NOTIFICATION_LOGIN_ALREADY_EXISTS));
                    return;
                }

                userService.saveUser(entity);
                    updateContainer(entity);
                    grid.select(entity);
                    closePopup();
            });


    private void updateContainer(User entity) {
        if(userBeanItemContainer.containsId(entity)){
            userBeanItemContainer.removeItem(entity);
        }
        userBeanItemContainer.addBean(entity);
        userBeanItemContainer.sort(new Object[]{"login"}, new boolean[]{true});
    }

    private AbstractForm.ResetHandler<User> resetHandler = (entity -> {
        closePopup();
        });

    private final MButton add = new MButton(FontAwesome.PLUS,
            event -> {
                form.setModalWindowTitle(messages.get("common.add.title"));
                form.setEntity(new User());
                form.openInModalPopup();
                form.focusFirst();
            });

    private final ConfirmButton delete = new ConfirmButton(FontAwesome.TRASH_O,"",
            event -> {
                Long id = ((User) grid.getSelectedRow()).getId();
                userService.delete(id);
                userBeanItemContainer.removeItem(grid.getSelectedRow());
            });

    private final MButton edit = new MButton(FontAwesome.PENCIL_SQUARE_O,
            event -> {
                Long id = ((User) grid.getSelectedRow()).getId();
                form.setModalWindowTitle(messages.get("common.edit.title"));
                form.setEntity(userService.findUser(id));
                form.openInModalPopup();
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

    private void initForm() {
        form.setSavedHandler(savedHandler);
        form.setResetHandler(resetHandler);
    }

    private void initGrid() {
        grid = new Grid();
        userBeanItemContainer = new BeanItemContainer<>(User.class, userService.getUsers());
        grid.setContainerDataSource(userBeanItemContainer);
        grid.setColumns("login", "role");
        grid.getColumn("login").setHeaderCaption(messages.get(COMMON_LOGIN));
        grid.getColumn("role").setHeaderCaption(messages.get(COMMON_ROLE));
        grid.addSelectionListener(event -> {
            applyButtonStates();
        });
    }

    private void applyButtonStates() {
        boolean isSelected = grid.getSelectedRow() != null;
        edit.setEnabled(isSelected);
        delete.setEnabled(isSelected);
    }

    private void closePopup() {
        form.closePopup();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


    }
}
