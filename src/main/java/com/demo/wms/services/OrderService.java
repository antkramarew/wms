package com.demo.wms.services;

import com.demo.wms.domain.Order;
import com.demo.wms.repository.OrderRepository;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toxa on 8/21/2016.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;


    public List<Order> findCanceledOrders() {
        return repository.findByStatus(Order.Status.CANCELED);
    }

    public List<Order> findCompletedOrders() {
        return repository.findByStatus(Order.Status.COMPLETED);
    }

    public void saveOrderWithStatus(Order order, Order.Status status) {
        order.setStatus(status);
        repository.save(order);
    }

    public Order findOrder(Long id) {
        return repository.findOne(id);
    }
}
