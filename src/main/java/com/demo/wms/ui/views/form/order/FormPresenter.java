package com.demo.wms.ui.views.form.order;

import com.demo.wms.domain.Order;
import com.demo.wms.exeptions.OrderSubmitException;
import com.demo.wms.processor.OrderProcessor;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

public class FormPresenter {

    protected OrderView view;
    protected Order order;
    protected OrderProcessor orderProcessor;

    public FormPresenter(OrderView view, OrderProcessor orderProcessor) {
        this.view = view;
        this.orderProcessor = orderProcessor;
    }

    public void viewShown() {
        clearForm();
    }

    public void clearPressed(){
        clearForm();
    }

    public void savePressed() {
        try {
            view.commit();
            orderProcessor.submitOrder(order);
            view.showSuccess();
            clearForm();
        }
        // Note that our View is leaking its internal implementation by throwing a FieldGroup.CommitException.
        // I opted for a simple an short solution, but purists may want to avoid.
        catch (FieldGroup.CommitException e) {
            view.showFailure();
        } catch (OrderSubmitException ose) {
            view.showFailure();
        }

    }

    private void clearForm() {
        order = new Order();
        view.setOrder(order);
    }
}
