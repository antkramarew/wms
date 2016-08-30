package com.demo.wms.ui.views.form;

import com.demo.wms.domain.Product;
import com.demo.wms.services.ProductService;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.MBeanFieldGroup;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class ProductForm extends AbstractForm<Product> {

    public static final String PRODUCT_FORM_FIELD_SKU = "product.form.field.sku";
    public static final String PRODUCT_FORM_FIELD_NAME = "product.form.field.name";
    public static final String PRODUCT_FORM_FIELD_PRICE = "product.form.field.price";
    public static final String PRODUCT_FORM_FIELD_TRADE_PRICE = "product.form.field.trade.price";
    public static final String PRODUCT_FORM_FIELD_WIDTH = "product.form.field.width";

    @Autowired
    private ProductService productService;
    @Autowired
    private I18N messages;

    @PropertyId("sku")
    private MTextField sku = new MTextField().withRequired(true);
    @PropertyId("name")
    private  MTextField name = new MTextField().withRequired(true);

    @PropertyId("width")
    private MTextField width = new MTextField().withRequired(true);
    @PropertyId("price")
    private  MTextField price = new MTextField().withRequired(true);
    @PropertyId("tradePrice")
    private  MTextField tradePrice = new MTextField();
/*
    private final MButton save = new MButton(FontAwesome.SAVE,"Save", e -> {
        productService.save(product);
        closePopup();
    }).withClickShortcut(ShortcutAction.KeyCode.ENTER)
            .withStyleName(ValoTheme.BUTTON_PRIMARY);

    private final MButton cancel = new MButton("Cancel", event -> {
        closePopup();
    });

    private final CssLayout actions = new MCssLayout(save, cancel).withStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);*/
    private MFormLayout formLayout;

    @Override
    protected Component createContent() {

        formLayout = new MFormLayout(this.sku,
                this.name,
                this.width,
                this.price,
                this.tradePrice).withWidth("");
        MVerticalLayout verticalLayout = new MVerticalLayout(formLayout, getToolbar()).withWidth("");
        return verticalLayout;
    }


    public interface ChangeHandler {

        void onChange();
    }


/*

    @Override
    public MBeanFieldGroup<Product> setEntity(Product entity) {
        //this.product = entity;
        return super.setEntity(entity);
    }
*/

    public void setChangeHandler(ChangeHandler h) {
        //save.addClickListener(e -> h.onChange());
    }

    @PostConstruct
    public void init() {

        sku.setCaption(messages.get(PRODUCT_FORM_FIELD_SKU));
        name = new MTextField(messages.get(PRODUCT_FORM_FIELD_NAME));
        width = new MTextField(messages.get(PRODUCT_FORM_FIELD_WIDTH));
        price = new MTextField(messages.get(PRODUCT_FORM_FIELD_PRICE));
        tradePrice = new MTextField(messages.get(PRODUCT_FORM_FIELD_TRADE_PRICE));
    }


}