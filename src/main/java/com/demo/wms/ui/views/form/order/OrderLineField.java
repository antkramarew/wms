package com.demo.wms.ui.views.form.order;

import com.demo.wms.domain.OrderLine;
import com.demo.wms.domain.Product;
import com.demo.wms.services.ProductService;
import com.demo.wms.util.vaadin.FieldGroupUtil;
import com.demo.wms.util.vaadin.MyTheme;
import com.google.common.collect.Lists;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.util.List;



public class OrderLineField extends CustomField<List> {

    private ProductService productService;

    private MTable<OrderLine> orderLineTable = new MTable<>(OrderLine.class)
            .withProperties("product", "quantity","item")
            .withColumnHeaders("Product", "Quantity", "Item")
            .withWidth("100%");

    private BeanItemContainer<OrderLine> container;
    private boolean spacing = true;

    public OrderLineField(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected Component initContent() {
        setWidth("100%");

        VerticalLayout rootLayout = new VerticalLayout();
        LineItemForm lineItemForm = new LineItemForm();
        rootLayout.addComponents(lineItemForm, orderLineTable);
        rootLayout.setWidth("100%");
        orderLineTable.setHeight(400, Unit.PIXELS);
        rootLayout.setSpacing(spacing);

        container = new BeanItemContainer<>(OrderLine.class, Lists.newArrayList());
        container.removeContainerProperty("id");
        orderLineTable.setContainerDataSource(container);
        return rootLayout;
    }


    @Override
    protected void setInternalValue(List newValue) {
        // We override set internal value to get the internal value of the field to show in the Grid.
        if (orderLineTable != null) {
            container = new BeanItemContainer<>(OrderLine.class, (List<OrderLine>) newValue);
            container.removeContainerProperty("id");
            orderLineTable.setContainerDataSource(container);
        }
        super.setInternalValue(newValue);
    }














    private class LineItemForm extends HorizontalLayout {

        @PropertyId("item")
        private ComboBox itemSelect = new ComboBox();
        MHorizontalLayout itemSelectWrapper = new MHorizontalLayout(itemSelect);
        /*
                .withReadOnly(true)
                .asComboBoxType(ComboBoxConfig.build())
                .withCaption("Items")
                .withNullSelectionAllowed(false)
                .setCaptionGenerator(option -> option.getQuantity() + "m " + (option.isDefect() ? "brak" : ""));
        */

        @PropertyId("product")
        private ComboBox productSelect;
                /*
                .withWidth("100%")
                .withNullSelectionAllowed(false)
                .withCaption("Products")
                .setCaptionGenerator(option -> option.getSku() + " " + option.getName() + " " + option.getWidth() + "m")
                .asComboBoxType(ComboBoxConfig.build()
                        .withFilteringMode(FilteringMode.CONTAINS)
                        .withPageLength(10)
                        .withTextInputAllowed(true))
                .addMValueChangeListener(event -> {
                    if(null != event.getValue()) {
                        itemSelect.clear();
                        itemSelect.setEnabled(true);
                        Long productId = event.getValue().getId();
                        productService.findProductChunks(productId).forEach(itemSelect::addOption);
                    }
                });
*/

        @PropertyId("quantity")
        private MTextField quantity = new MTextField("Quantity")
                .withWidth(80, Unit.PIXELS);

        private BeanFieldGroup<OrderLine> fieldGroup = new BeanFieldGroup<>(OrderLine.class);;

        private MButton addButton = new MButton("Add", click -> add() );

        public LineItemForm() {
            setSpacing(spacing);
            setWidth("100%");

            initLayout();
            initFieldGroup();
        }


        private void initFieldGroup() {
            fieldGroup.bindMemberFields(this);
            FieldGroupUtil.improveUX(fieldGroup, addButton, null);
            resetDataSource();
        }

        private void resetDataSource() {
            fieldGroup.setItemDataSource(new BeanItem<>(new OrderLine()));
        }

        private void initLayout() {
            initComboBox();
            addComponents(productSelect,itemSelectWrapper, quantity, addButton);
            quantity.setRequired(true);
            addButton.addStyleName(MyTheme.BUTTON_PRIMARY);
            setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
            setExpandRatio(productSelect, 1);
        }

        private void initComboBox() {
            itemSelect.setRequired(true);
            itemSelect.setReadOnly(true);
            itemSelect.setCaption("Items");
            productSelect = new ComboBox("Products", productService.findAll());
            productSelect.setRequired(true);
            productSelect.setFilteringMode(FilteringMode.CONTAINS);
            productSelect.setImmediate(true);
            productSelect.addValueChangeListener(productSelectedListener);
            productSelect.setWidth("100%");

        }

        ValueChangeListener productSelectedListener = (ValueChangeListener) event -> {
            if(event.getProperty().getValue() != null) {
                productService.findProductChunks(((Product) event.getProperty().getValue()).getId()).forEach(itemSelect::addItem);
            } else {
                itemSelect.removeAllItems();
            }

        };


        private void add(){
            if(Float.valueOf(quantity.getValue()) <=0 ){
                Notification.show("Quantity must be greater than 0");
                quantity.focus();
                return;
            }
            try {
                fieldGroup.commit();
                addOrderLineToOrder(fieldGroup.getItemDataSource().getBean());
                resetDataSource();
            } catch (FieldGroup.CommitException e) {
                Notification.show("Check your input");
            }
        }

    }

    private void addOrderLineToOrder(OrderLine lineItem) {
        List<OrderLine> currentLineItems = Lists.newArrayList(getValue());
        currentLineItems.add(lineItem);

        // Call set value to ensure that all listeners get triggered
        setValue(currentLineItems);
    }

    @Override
    public Class<? extends List> getType() {
        return List.class;
    }

}
