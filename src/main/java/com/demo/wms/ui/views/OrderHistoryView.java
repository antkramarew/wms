package com.demo.wms.ui.views;

import com.demo.wms.domain.Order;
import com.demo.wms.repository.OrderRepository;
import com.demo.wms.ui.components.VerticalSpacedLayout;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.label.MLabel;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Created by toxa on 8/22/2016.
 */
@SpringView(name = "order_history_view")
public class OrderHistoryView extends VerticalSpacedLayout implements View {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private MessageSource messageSource;

    private MTable<Order> orderTable = new MTable<Order>()
            .withProperties("id", "manager", "status", "submittedDate");

    private MLabel label = new MLabel("Order history").withSize(MSize.FULL_WIDTH);

    @PostConstruct
    public void init() {

        orderTable.setContainerDataSource(new BeanItemContainer<Order>(Order.class, (Collection<? extends Order>) repository.findAll()));
        orderTable.setSizeFull();
        addComponents(label, orderTable);

        setTableHeaders();
    }

    private void setTableHeaders() {
        /*
        orderTable.setColumnHeaders(
                messageSource.getMessage("order.history.view.table.header.id", null, null),
                messageSource.getMessage("order.history.view.table.header.manager", null, null),
                messageSource.getMessage("order.history.view.table.header.status", null, null),
                messageSource.getMessage("order.history.view.table.header.date", null, null)
        );*/
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
