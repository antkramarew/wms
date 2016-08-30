package com.demo.wms.ui.views.form.order;

import com.demo.wms.services.ProductService;
import com.demo.wms.util.vaadin.NonEmptyCollectionValidator;
import com.vaadin.data.fieldgroup.PropertyId;


public class FormLayout extends com.vaadin.ui.FormLayout {


    @PropertyId("lines")
    private OrderLineField lineItemsField;

    public FormLayout(ProductService productService) {
        lineItemsField = new OrderLineField(productService);
        init();

    }

    public void init() {
        setSpacing(true);
        addComponents(lineItemsField);
        lineItemsField.setRequired(true);
        lineItemsField.addValidator(new NonEmptyCollectionValidator("An order must contain products"));
    }
}
