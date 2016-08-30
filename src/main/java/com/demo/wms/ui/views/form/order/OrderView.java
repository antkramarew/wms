package com.demo.wms.ui.views.form.order;


import com.demo.wms.domain.Order;
import com.demo.wms.processor.OrderProcessor;
import com.demo.wms.services.ProductService;
import com.demo.wms.ui.components.VerticalSpacedLayout;
import com.demo.wms.util.vaadin.FieldGroupUtil;
import com.demo.wms.util.vaadin.MyTheme;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringView(name="order_view")
public class OrderView extends VerticalSpacedLayout implements View {


    @Autowired
    private OrderProcessor orderProcessor;
    @Autowired
    private ProductService productService;

    private FormPresenter presenter;

    private FormLayout formLayout;

    private FieldGroup fieldGroup;
    private Button clear;
    private Button save;

    public OrderView() {

    }

    @PostConstruct
    public void init() {
        presenter = new FormPresenter(this, orderProcessor);
        formLayout = new FormLayout(productService);

        addCaption();
        addForm();
    }

    private void addCaption() {
        Label caption = new Label("Add new order");
        caption.addStyleName(MyTheme.LABEL_H4);
        addComponent(caption);
    }

    private void addForm() {
        addComponents(formLayout, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        clear = new Button("Clear", click -> presenter.clearPressed());
        save = new Button("Add Order", click -> presenter.savePressed());
        buttonsLayout.addComponents(clear, save);

        buttonsLayout.setSpacing(true);
        buttonsLayout.setWidth("100%");

        clear.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addStyleName(MyTheme.BUTTON_PRIMARY);

        buttonsLayout.setExpandRatio(clear, 1);
        buttonsLayout.setComponentAlignment(clear, Alignment.TOP_RIGHT);


        return buttonsLayout;
    }

    public void setOrder(Order order) {
        fieldGroup = new FieldGroup(new BeanItem<>(order));
        fieldGroup.bindMemberFields(formLayout);
        FieldGroupUtil.improveUX(fieldGroup, save, clear);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        presenter.viewShown();
    }

    public void showSuccess() {
        Notification.show("Saved successfully");
    }

    public void showFailure() {
        Notification.show("You are doing it wrong.", Notification.Type.ERROR_MESSAGE);
    }

    public void commit() throws FieldGroup.CommitException {
        fieldGroup.commit();
    }

}
